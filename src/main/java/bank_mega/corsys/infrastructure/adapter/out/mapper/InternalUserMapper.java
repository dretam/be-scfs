package bank_mega.corsys.infrastructure.adapter.out.mapper;

import bank_mega.corsys.domain.exception.DomainRuleViolationException;
import bank_mega.corsys.domain.model.branch.Branch;
import bank_mega.corsys.domain.model.internaluser.InternalUser;
import bank_mega.corsys.domain.model.internaluser.InternalUserArea;
import bank_mega.corsys.domain.model.internaluser.InternalUserCount;
import bank_mega.corsys.domain.model.internaluser.InternalUserDirektorat;
import bank_mega.corsys.domain.model.internaluser.InternalUserEmail;
import bank_mega.corsys.domain.model.internaluser.InternalEmployeeType;
import bank_mega.corsys.domain.model.internaluser.InternalUserJabatan;
import bank_mega.corsys.domain.model.internaluser.InternalUserName;
import bank_mega.corsys.domain.model.internaluser.InternalUserStatus;
import bank_mega.corsys.infrastructure.adapter.out.jpa.entity.BranchJpaEntity;
import bank_mega.corsys.infrastructure.adapter.out.jpa.entity.InternalUserJpaEntity;
import jakarta.validation.constraints.NotNull;

import java.util.Set;

public class InternalUserMapper {

    public static InternalUser toDomain(@NotNull InternalUserJpaEntity jpaEntity, Set<String> expands) {
        if (jpaEntity == null) throw new DomainRuleViolationException("JPA Entity is null");
        
        Branch usersCabang = null;
        Branch usersBranch = null;
        
        if (expands.contains("usersCabang") && jpaEntity.getUsersCabang() != null) {
            usersCabang = BranchMapper.toDomain(jpaEntity.getUsersCabang());
        }
        
        if (expands.contains("usersBranch") && jpaEntity.getUsersBranch() != null) {
            usersBranch = BranchMapper.toDomain(jpaEntity.getUsersBranch());
        }
        
        return new InternalUser(
                new InternalUserName(jpaEntity.getUserName()),
                jpaEntity.getNama(),
                jpaEntity.getJoinDate(),
                jpaEntity.getJabatan() != null ? new InternalUserJabatan(jpaEntity.getJabatan()) : null,
                jpaEntity.getApproval1(),
                jpaEntity.getApproval2(),
                jpaEntity.getLastLogin(),
                jpaEntity.getUpdatePass(),
                jpaEntity.getStatus() != null ? new InternalUserStatus(jpaEntity.getStatus()) : null,
                jpaEntity.getCount() != null ? new InternalUserCount(jpaEntity.getCount()) : null,
                jpaEntity.getEmail() != null ? new InternalUserEmail(jpaEntity.getEmail()) : null,
                jpaEntity.getArea() != null ? new InternalUserArea(jpaEntity.getArea()) : null,
                jpaEntity.getJobTitle(),
                jpaEntity.getDirektorat() != null ? new InternalUserDirektorat(jpaEntity.getDirektorat()) : null,
                jpaEntity.getSex(),
                jpaEntity.getEmployee() != null ? new InternalEmployeeType(jpaEntity.getEmployee()) : null,
                jpaEntity.getMobile(),
                jpaEntity.getPassword(),
                jpaEntity.getExtOffice(),
                jpaEntity.getTglLahir(),
                jpaEntity.getPangkat(),
                jpaEntity.getSessionId(),
                usersCabang,
                usersBranch,
                null
        );
    }

    public static InternalUser toDomain(@NotNull InternalUserJpaEntity jpaEntity) {
        return toDomain(jpaEntity, Set.of());
    }

    public static InternalUserJpaEntity toJpaEntity(InternalUser domainEntity) {
        InternalUserJpaEntity jpaEntity = new InternalUserJpaEntity();
        jpaEntity.setUserName(domainEntity.getUserName().value());
        jpaEntity.setNama(domainEntity.getNama());
        jpaEntity.setJoinDate(domainEntity.getJoinDate());
        if (domainEntity.getJabatan() != null) {
            jpaEntity.setJabatan(domainEntity.getJabatan().value());
        }
        jpaEntity.setApproval1(domainEntity.getApproval1());
        jpaEntity.setApproval2(domainEntity.getApproval2());
        jpaEntity.setLastLogin(domainEntity.getLastLogin());
        jpaEntity.setUpdatePass(domainEntity.getUpdatePass());
        if (domainEntity.getStatus() != null) {
            jpaEntity.setStatus(domainEntity.getStatus().value());
        }
        if (domainEntity.getCount() != null) {
            jpaEntity.setCount(domainEntity.getCount().value());
        }
        if (domainEntity.getEmail() != null) {
            jpaEntity.setEmail(domainEntity.getEmail().value());
        }
        if (domainEntity.getArea() != null) {
            jpaEntity.setArea(domainEntity.getArea().value());
        }
        jpaEntity.setJobTitle(domainEntity.getJobTitle());
        if (domainEntity.getDirektorat() != null) {
            jpaEntity.setDirektorat(domainEntity.getDirektorat().value());
        }
        jpaEntity.setSex(domainEntity.getSex());
        if (domainEntity.getEmployee() != null) {
            jpaEntity.setEmployee(domainEntity.getEmployee().value());
        }
        jpaEntity.setMobile(domainEntity.getMobile());
        jpaEntity.setPassword(domainEntity.getPassword());
        jpaEntity.setExtOffice(domainEntity.getExtOffice());
        jpaEntity.setTglLahir(domainEntity.getTglLahir());
        jpaEntity.setPangkat(domainEntity.getPangkat());
        jpaEntity.setSessionId(domainEntity.getSessionId());
        
        if (domainEntity.getUsersCabang() != null) {
            BranchJpaEntity branchJpa = new BranchJpaEntity();
            branchJpa.setId(domainEntity.getUsersCabang().getId().value());
            jpaEntity.setUsersCabang(branchJpa);
        }
        
        if (domainEntity.getUsersBranch() != null) {
            BranchJpaEntity branchJpa = new BranchJpaEntity();
            branchJpa.setId(domainEntity.getUsersBranch().getId().value());
            jpaEntity.setUsersBranch(branchJpa);
        }
        
        return jpaEntity;
    }

}
