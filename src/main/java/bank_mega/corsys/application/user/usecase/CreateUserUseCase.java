package bank_mega.corsys.application.user.usecase;

import bank_mega.corsys.application.assembler.UserAssembler;
import bank_mega.corsys.application.common.annotation.UseCase;
import bank_mega.corsys.application.user.command.CreateUserCommand;
import bank_mega.corsys.application.user.dto.UserResponse;
import bank_mega.corsys.application.user.dto.UserUploadResponse;
import bank_mega.corsys.domain.exception.CompanyNotFoundException;
import bank_mega.corsys.domain.exception.PermissionNotFoundException;
import bank_mega.corsys.domain.exception.RoleNotFoundException;
import bank_mega.corsys.domain.exception.UserAlreadyExistsException;
import bank_mega.corsys.domain.model.common.AuditTrail;
import bank_mega.corsys.domain.model.company.Company;
import bank_mega.corsys.domain.model.company.CompanyCif;
import bank_mega.corsys.domain.model.company.CompanyId;
import bank_mega.corsys.domain.model.permission.Permission;
import bank_mega.corsys.domain.model.permission.PermissionId;
import bank_mega.corsys.domain.model.role.Role;
import bank_mega.corsys.domain.model.role.RoleCode;
import bank_mega.corsys.domain.model.user.*;
import bank_mega.corsys.domain.model.userpermission.Effect;
import bank_mega.corsys.domain.model.userpermission.UserPermission;
import bank_mega.corsys.domain.repository.*;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@UseCase
@RequiredArgsConstructor
public class CreateUserUseCase {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final CompanyRepository companyRepository;
    private final UserPermissionRepository userPermissionRepository;
    private final PermissionRepository permissionRepository;
    private final UserAssembler userAssembler;

    @Transactional
    public UserResponse execute(CreateUserCommand command, User authPrincipal) {

        UserName userName = new UserName(command.username());

        User existingUser = userRepository.findFirstByName(userName).orElse(null);

        if (existingUser != null) {
            if (existingUser.getAudit().deletedAt() == null) {
                throw new UserAlreadyExistsException(existingUser.getName().value());
            } else {
                return restoreUser(existingUser, command, authPrincipal);
            }
        }
        return createNewUser(null, command, authPrincipal);
    }

    public UserUploadResponse uploadUsers(MultipartFile file, User authPrincipal) {

        List<User> successList = new ArrayList<>();
        List<String> errors = new ArrayList<>();

        try (Workbook workbook = new XSSFWorkbook(file.getInputStream())) {

            Sheet sheet = workbook.getSheetAt(0);

            for (int i = 1; i <= sheet.getLastRowNum(); i++) {

                Row row = sheet.getRow(i);
                if (row == null) continue;

                try {
                    String username = getCell(row, 0);
                    String password = getCell(row, 1);
                    String fullName = getCell(row, 2);
                    String email = getCell(row, 3);
                    String roleCode = getCell(row, 4);
                    String companyCif = getCell(row, 5);

                    // --- VALIDATION ---
                    validateRow(username, password, fullName, email, roleCode, companyCif, i);

                    Role role = roleRepository.findFirstByIdAndAuditDeletedAtIsNull(new RoleCode(roleCode))
                            .orElseThrow(() -> new RuntimeException("Invalid role"));

                    Company company = companyRepository.findFirstByCif(new CompanyCif(companyCif))
                            .orElseThrow(() -> new RuntimeException("Invalid company"));

                    User resUser = new User(
                        null,
                        new UserName(username.trim()),
                        new UserFullName(fullName.trim()),
                        new UserEmail(email.toLowerCase().trim()),
                        new UserPassword(userRepository.hashPassword(password)),
                        new UserIsActive(true),
                        new UserPhotoPath(null),
                        company,
                        role,
                        AuditTrail.create(authPrincipal.getId().value())
                    );

                    successList.add(resUser);
                } catch (Exception e) {
                    errors.add("Row " + (i + 1) + ": " + e.getMessage());
                }
            }

            // --- BATCH INSERT ---
            int batchSize = 50;
            for (int i = 0; i < successList.size(); i += batchSize) {
                userRepository.saveAll(
                        successList.subList(i, Math.min(i + batchSize, successList.size()))
                );
            }

            return new UserUploadResponse(
                    sheet.getLastRowNum(),
                    successList.size(),
                    errors.size(),
                    errors
            );
        } catch (Exception e) {
            throw new RuntimeException("Failed to process Excel");
        }
    }

    private String getCell(Row row, int index) {
        Cell cell = row.getCell(index);

        if (cell == null) return null;

        return switch (cell.getCellType()) {
            case STRING -> cell.getStringCellValue().trim();
            case NUMERIC -> String.valueOf((long) cell.getNumericCellValue());
            default -> null;
        };
    }

    private void validateRow(
            String username,
            String password,
            String fullName,
            String email,
            String role,
            String company,
            int row
    ) {
        if (fullName == null || fullName.length() < 3)
            throw new RuntimeException("Full name min 3");

        if (username == null || username.length() < 8)
            throw new RuntimeException("Invalid username");

        if (password == null || password.length() < 8)
            throw new RuntimeException("Password min 8");

        if (email == null || !email.contains("@"))
            throw new RuntimeException("Invalid email");

        if (role == null)
            throw new RuntimeException("Role required");

        if (company == null)
            throw new RuntimeException("Company required");

        if (userRepository.findFirstByEmail(new UserEmail(email)).isPresent())
            throw new RuntimeException("Email already exists");
    }

    @Transactional
    public List<UserResponse> excelExecute(List<CreateUserCommand> commandList, User authPrincipal) {
        List<UserResponse> userResponses = new LinkedList<>();
        for (CreateUserCommand command : commandList) {
            UserName userName = new UserName(command.username());

            User existingUser = userRepository.findFirstByName(userName).orElse(null);

            if (existingUser != null) {
                if (existingUser.getAudit().deletedAt() == null) {
                    throw new UserAlreadyExistsException(existingUser.getName().value());
                } else {
                    UserResponse userResponse = restoreUser(existingUser, command, authPrincipal);
                    userResponses.add(userResponse);
                }
            } else {
                UserResponse userResponse = createNewUser(null, command, authPrincipal);
                userResponses.add(userResponse);
            }
        }
        return userResponses;
    }

    public Boolean prepareDataUserForDownloadExcelTemplate(
            User authPrincipal,
            HttpServletResponse response
    ) {
        try (SXSSFWorkbook workbook = new SXSSFWorkbook(100);
             ServletOutputStream out = response.getOutputStream()) {

            // --- RESPONSE HEADER ---
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.setHeader("Content-Disposition", "attachment; filename=\"template-users.xlsx\"");

            Sheet sheet = workbook.createSheet("Data Users");
            SXSSFSheet sxssfSheet = (SXSSFSheet) sheet;

            for (int i = 0; i < 6; i++) {
                sxssfSheet.trackColumnForAutoSizing(i);
            }

            // --- HEADER ---
            String[] columns = {
                    "Username", "Password", "Full Name",
                    "Email", "Role Code", "Company CIF"
            };

            createHeader(workbook, sheet, columns);

            sheet.createFreezePane(0, 1);

            DataValidationHelper helper = sheet.getDataValidationHelper();

            // --- DROPDOWNS ---
            String[] roles = roleRepository.findAllByAuditDeletedAtIsNull()
                    .stream()
                    .map(r -> r.getCode().value())
                    .toArray(String[]::new);

            String[] companies = companyRepository.findAllByAuditDeletedAtIsNull()
                    .stream()
                    .map(c -> c.getCompanyCif().value())
                    .toArray(String[]::new);

            addDropdown(helper, sheet, roles, 4);     // Role column
            addDropdown(helper, sheet, companies, 5); // Company column

            // --- VALIDATIONS ---
            addRequiredValidation(helper, sheet, "A2", 0, "Username is required");
            addRequiredValidation(helper, sheet, "B2", 1, "Password is required");
            addRequiredValidation(helper, sheet, "C2", 2, "Full name is required");

            addEmailValidation(helper, sheet);

            // --- WRITE ---
            workbook.write(out);
            workbook.dispose();

            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private void createHeader(SXSSFWorkbook workbook, Sheet sheet, String[] columns) {

        Font font = workbook.createFont();
        font.setBold(true);
        font.setColor(IndexedColors.WHITE.getIndex());

        CellStyle style = workbook.createCellStyle();
        style.setFont(font);
        style.setFillForegroundColor(IndexedColors.DARK_BLUE.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style.setAlignment(HorizontalAlignment.CENTER);

        Row header = sheet.createRow(0);

        for (int i = 0; i < columns.length; i++) {
            Cell cell = header.createCell(i);
            cell.setCellValue(columns[i]);
            cell.setCellStyle(style);
            sheet.autoSizeColumn(i);
        }
    }

    private void addDropdown(DataValidationHelper helper, Sheet sheet, String[] data, int column) {

        CellRangeAddressList range = new CellRangeAddressList(1, 100, column, column);

        DataValidationConstraint constraint =
                helper.createExplicitListConstraint(data);

        DataValidation validation = helper.createValidation(constraint, range);
        validation.setSuppressDropDownArrow(false);
        validation.setShowErrorBox(true);

        sheet.addValidationData(validation);
    }

    private void addRequiredValidation(
            DataValidationHelper helper,
            Sheet sheet,
            String formulaCell,
            int column,
            String message
    ) {
        CellRangeAddressList range = new CellRangeAddressList(1, 100, column, column);

        DataValidationConstraint constraint =
                helper.createCustomConstraint("LEN(" + formulaCell + ")>0");

        DataValidation validation = helper.createValidation(constraint, range);
        validation.setShowErrorBox(true);
        validation.createErrorBox("Required", message);

        sheet.addValidationData(validation);
    }

    private void addEmailValidation(DataValidationHelper helper, Sheet sheet) {

        CellRangeAddressList range = new CellRangeAddressList(1, 100, 3, 3);

        DataValidationConstraint constraint =
                helper.createCustomConstraint("ISNUMBER(SEARCH(\"@\",D2))");

        DataValidation validation = helper.createValidation(constraint, range);
        validation.setShowErrorBox(true);
        validation.createErrorBox("Invalid Email", "Email must contain '@'");

        sheet.addValidationData(validation);
    }

    private UserResponse restoreUser(User existingUser, CreateUserCommand command, User authPrincipal) {
        Role role = roleRepository.findFirstByIdAndAuditDeletedAtIsNull(new RoleCode(command.roleId()))
                .orElseThrow(() -> new RoleNotFoundException(new RoleCode(command.roleId())));

        existingUser.changePassword(new UserPassword(userRepository.hashPassword(command.password())));
        existingUser.changeRole(role);
        existingUser.updateAudit(authPrincipal.getId().value()); // This will update updatedAt and updatedBy

        User saved = userRepository.save(existingUser);

        handlePermissionOverrides(saved, command);

        return userAssembler.toResponse(saved);
    }

    private UserResponse createNewUser(User user, CreateUserCommand command, User authPrincipal) {
        Role role = roleRepository.findFirstByIdAndAuditDeletedAtIsNull(new RoleCode(command.roleId()))
                .orElseThrow(() -> new RoleNotFoundException(new RoleCode(command.roleId())));

        Company company = companyRepository.findFirstByIdAndAuditDeletedAtIsNull(new CompanyId(command.companyId()))
                .orElseThrow(() -> new CompanyNotFoundException(new CompanyId(command.companyId())));

        User newUser = new User(
                null,
                new UserName(command.username()),
                new UserFullName(command.fullName()),
                new UserEmail(command.email()),
                new UserPassword(userRepository.hashPassword(command.password())),
                new UserIsActive(command.isActive()),
                new UserPhotoPath(command.photoPath()),
                company,
                role,
                AuditTrail.create(authPrincipal.getId().value())
        );

        User saved = userRepository.save(newUser);

        handlePermissionOverrides(saved, command);

        return userAssembler.toResponse(saved);
    }

    private void handlePermissionOverrides(User user, CreateUserCommand command) {
        if (command.permissionOverrides() != null && !command.permissionOverrides().isEmpty()) {
            // Delete existing permission overrides
            userPermissionRepository.deleteByUserId(user.getId());

            // Insert new permission overrides
            for (CreateUserCommand.PermissionOverride override : command.permissionOverrides()) {
                Permission permission = permissionRepository.findFirstById(PermissionId.of(override.permissionId()))
                        .orElseThrow(() -> new PermissionNotFoundException(PermissionId.of(override.permissionId())));

                UserPermission userPermission = new UserPermission(
                        null,
                        user,
                        permission,
                        Effect.valueOf(override.effect().name())
                );

                userPermissionRepository.save(userPermission);
            }
        } else {
            // If no overrides provided, delete any existing ones
            userPermissionRepository.deleteByUserId(user.getId());
        }
    }
}