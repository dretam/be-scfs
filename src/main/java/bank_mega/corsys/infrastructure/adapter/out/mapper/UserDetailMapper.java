package bank_mega.corsys.infrastructure.adapter.out.mapper;

import bank_mega.corsys.domain.exception.DomainRuleViolationException;
import bank_mega.corsys.domain.model.branch.Branch;
import bank_mega.corsys.domain.model.internaluser.InternalUserArea;
import bank_mega.corsys.domain.model.internaluser.InternalUserDirektorat;
import bank_mega.corsys.domain.model.internaluser.InternalUserEmail;
import bank_mega.corsys.domain.model.internaluser.InternalUserJabatan;
import bank_mega.corsys.domain.model.user.UserId;
import bank_mega.corsys.domain.model.userdetail.UserDetail;
import bank_mega.corsys.domain.model.userdetail.UserDetailId;
import bank_mega.corsys.infrastructure.adapter.out.jpa.entity.BranchJpaEntity;
import bank_mega.corsys.infrastructure.adapter.out.jpa.entity.UserDetailJpaEntity;
import bank_mega.corsys.infrastructure.adapter.out.jpa.entity.UserJpaEntity;
import jakarta.validation.constraints.NotNull;

import java.util.Set;

public class UserDetailMapper {

    public static UserDetail toDomain(@NotNull UserDetailJpaEntity jpaEntity, Set<String> expands) {
        if (jpaEntity == null) throw new DomainRuleViolationException("JPA Entity is null");

        Branch usersCabang = null;
        Branch usersBranch = null;

        if (expands.contains("usersCabang") && jpaEntity.getUsersCabang() != null) {
            usersCabang = BranchMapper.toDomain(jpaEntity.getUsersCabang());
        }

        if (expands.contains("usersBranch") && jpaEntity.getUsersBranch() != null) {
            usersBranch = BranchMapper.toDomain(jpaEntity.getUsersBranch());
        }

        return new UserDetail(
                jpaEntity.getId() != null ? new UserDetailId(jpaEntity.getId()) : null,
                new UserId(jpaEntity.getUserId().getId()),
                jpaEntity.getNama(),
                jpaEntity.getJabatan() != null ? new InternalUserJabatan(jpaEntity.getJabatan()) : null,
                jpaEntity.getEmail() != null ? new InternalUserEmail(jpaEntity.getEmail()) : null,
                jpaEntity.getArea() != null ? new InternalUserArea(jpaEntity.getArea()) : null,
                jpaEntity.getJobTitle(),
                jpaEntity.getDirektorat() != null ? new InternalUserDirektorat(jpaEntity.getDirektorat()) : null,
                jpaEntity.getSex(),
                jpaEntity.getMobile(),
                jpaEntity.getTglLahir(),
                usersCabang,
                usersBranch,
                AuditTrailEmbeddableMapper.toDomain(jpaEntity.getAudit())
        );
    }

    public static UserDetail toDomain(@NotNull UserDetailJpaEntity jpaEntity) {
        return toDomain(jpaEntity, Set.of());
    }

    public static UserDetailJpaEntity toJpaEntity(UserDetail userDetail) {
        UserDetailJpaEntity jpaEntity = new UserDetailJpaEntity();
        if (userDetail.getId() != null) {
            jpaEntity.setId(userDetail.getId().value());
        }
        if (userDetail.getUserId() != null) {
            UserJpaEntity userJpa = new UserJpaEntity();
            userJpa.setId(userDetail.getUserId().value());
            jpaEntity.setUserId(userJpa);
        }
        jpaEntity.setNama(userDetail.getNama());
        if (userDetail.getJabatan() != null) {
            jpaEntity.setJabatan(userDetail.getJabatan().value());
        }
        if (userDetail.getEmail() != null) {
            jpaEntity.setEmail(userDetail.getEmail().value());
        }
        if (userDetail.getArea() != null) {
            jpaEntity.setArea(userDetail.getArea().value());
        }
        jpaEntity.setJobTitle(userDetail.getJobTitle());
        if (userDetail.getDirektorat() != null) {
            jpaEntity.setDirektorat(userDetail.getDirektorat().value());
        }
        jpaEntity.setSex(userDetail.getSex());
        jpaEntity.setMobile(userDetail.getMobile());
        jpaEntity.setTglLahir(userDetail.getTglLahir());
        jpaEntity.setAudit(AuditTrailEmbeddableMapper.toJpa(userDetail.getAudit()));

        if (userDetail.getUsersCabang() != null) {
            BranchJpaEntity branchJpa = new BranchJpaEntity();
            branchJpa.setId(userDetail.getUsersCabang().getId().value());
            jpaEntity.setUsersCabang(branchJpa);
        }

        if (userDetail.getUsersBranch() != null) {
            BranchJpaEntity branchJpa = new BranchJpaEntity();
            branchJpa.setId(userDetail.getUsersBranch().getId().value());
            jpaEntity.setUsersBranch(branchJpa);
        }

        return jpaEntity;
    }

}
