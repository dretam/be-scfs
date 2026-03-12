package bank_mega.corsys.domain.repository;

import bank_mega.corsys.domain.model.menu.Menu;
import bank_mega.corsys.domain.model.menu.MenuCode;
import bank_mega.corsys.domain.model.menu.MenuId;
import bank_mega.corsys.domain.model.role.RoleId;
import lombok.NonNull;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface MenuRepository {

    Menu save(Menu menu);

    void delete(Menu menu);

    long count();

    Page<@NonNull Menu> findAllPageable(int page, int size, String sort, String filter, Set<String> expands);

    Optional<Menu> findFirstById(MenuId id);

    Optional<Menu> findFirstByIdAndAuditDeletedAtIsNull(MenuId id);

    Optional<Menu> findFirstByCode(MenuCode code);

    List<Menu> findAllByRoleId(RoleId roleId);

    List<Menu> findAllActiveByParentId(MenuId parentId);

}
