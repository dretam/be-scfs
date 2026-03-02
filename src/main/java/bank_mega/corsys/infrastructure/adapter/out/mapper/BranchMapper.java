package bank_mega.corsys.infrastructure.adapter.out.mapper;

import bank_mega.corsys.domain.exception.DomainRuleViolationException;
import bank_mega.corsys.domain.model.branch.Branch;
import bank_mega.corsys.domain.model.branch.BranchAbbreviation;
import bank_mega.corsys.domain.model.branch.BranchArea;
import bank_mega.corsys.domain.model.branch.BranchCategory;
import bank_mega.corsys.domain.model.branch.BranchCode;
import bank_mega.corsys.domain.model.branch.BranchDirektorat;
import bank_mega.corsys.domain.model.branch.BranchId;
import bank_mega.corsys.domain.model.branch.BranchName;
import bank_mega.corsys.domain.model.branch.BranchRegional;
import bank_mega.corsys.infrastructure.adapter.out.jpa.entity.BranchJpaEntity;
import jakarta.validation.constraints.NotNull;

public class BranchMapper {

    public static Branch toDomain(@NotNull BranchJpaEntity jpaEntity) {
        if (jpaEntity == null) throw new DomainRuleViolationException("JPA Entity is null");
        return new Branch(
                new BranchId(jpaEntity.getId()),
                jpaEntity.getIdBranch() != null ? new BranchCode(jpaEntity.getIdBranch()) : null,
                jpaEntity.getBranchName() != null ? new BranchName(jpaEntity.getBranchName()) : null,
                jpaEntity.getFlagDel(),
                jpaEntity.getCategory() != null ? new BranchCategory(jpaEntity.getCategory()) : null,
                jpaEntity.getRegional() != null ? new BranchRegional(jpaEntity.getRegional()) : null,
                jpaEntity.getAddress(),
                jpaEntity.getArea() != null ? new BranchArea(jpaEntity.getArea()) : null,
                jpaEntity.getDirektorat() != null ? new BranchDirektorat(jpaEntity.getDirektorat()) : null,
                jpaEntity.getModId(),
                jpaEntity.getTelepon(),
                jpaEntity.getFaximile(),
                jpaEntity.getSingkatan() != null ? new BranchAbbreviation(jpaEntity.getSingkatan()) : null,
                null
        );
    }

    public static BranchJpaEntity toJpaEntity(Branch domainEntity) {
        BranchJpaEntity jpaEntity = new BranchJpaEntity();
        if (domainEntity.getId() != null) {
            jpaEntity.setId(domainEntity.getId().value());
        }
        if (domainEntity.getIdBranch() != null) {
            jpaEntity.setIdBranch(domainEntity.getIdBranch().value());
        }
        if (domainEntity.getBranchName() != null) {
            jpaEntity.setBranchName(domainEntity.getBranchName().value());
        }
        jpaEntity.setFlagDel(domainEntity.getFlagDel());
        if (domainEntity.getCategory() != null) {
            jpaEntity.setCategory(domainEntity.getCategory().value());
        }
        if (domainEntity.getRegional() != null) {
            jpaEntity.setRegional(domainEntity.getRegional().value());
        }
        jpaEntity.setAddress(domainEntity.getAddress());
        if (domainEntity.getArea() != null) {
            jpaEntity.setArea(domainEntity.getArea().value());
        }
        if (domainEntity.getDirektorat() != null) {
            jpaEntity.setDirektorat(domainEntity.getDirektorat().value());
        }
        jpaEntity.setModId(domainEntity.getModId());
        jpaEntity.setTelepon(domainEntity.getTelepon());
        jpaEntity.setFaximile(domainEntity.getFaximile());
        if (domainEntity.getSingkatan() != null) {
            jpaEntity.setSingkatan(domainEntity.getSingkatan().value());
        }
        return jpaEntity;
    }

}
