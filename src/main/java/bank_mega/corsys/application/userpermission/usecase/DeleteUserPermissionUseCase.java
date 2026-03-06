package bank_mega.corsys.application.userpermission.usecase;

import bank_mega.corsys.application.common.annotation.UseCase;
import bank_mega.corsys.application.userpermission.command.DeleteUserPermissionCommand;
import bank_mega.corsys.domain.exception.DomainRuleViolationException;
import bank_mega.corsys.domain.model.user.UserId;
import bank_mega.corsys.domain.model.userpermission.UserPermission;
import bank_mega.corsys.domain.model.userpermission.UserPermissionId;
import bank_mega.corsys.domain.model.user.User;
import bank_mega.corsys.domain.repository.UserPermissionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@UseCase
@RequiredArgsConstructor
public class DeleteUserPermissionUseCase {

    private final UserPermissionRepository userPermissionRepository;

    @Transactional
    public void execute(DeleteUserPermissionCommand command, User authPrincipal) {
        UserPermissionId id = new UserPermissionId(command.userId(), command.permissionId());
        
        UserPermission userPermission = userPermissionRepository.findFirstById(id)
                .orElseThrow(() -> new DomainRuleViolationException(
                        "User permission override not found for user " + command.userId() + 
                        " and permission " + command.permissionId()));

        // Delete the user permission override
        userPermissionRepository.delete(userPermission);
    }

}
