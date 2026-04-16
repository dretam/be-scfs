package bank_mega.corsys.domain.repository;

import bank_mega.corsys.domain.model.community.Community;
import bank_mega.corsys.domain.model.community.CommunityId;
import bank_mega.corsys.domain.model.community.CommunityName;
import lombok.NonNull;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface CommunityRepository {
    Community save(Community community);

    void delete(Community community);

    long count();

    Page<@NonNull Community> findAllPageable(int page, int size, String sort, String filter);

    List<@NonNull Community> findAllByAuditDeletedAtIsNull();

    Optional<Community> findFirstById(CommunityId communityId);

    Optional<Community> findFirstByIdAndAuditDeletedAtIsNull(CommunityId communityId);

    Optional<Community> findFirstByName(CommunityName communityName);

    Optional<Community> findFirstByNameAndAuditDeletedAtIsNull(CommunityName communityName);
}
