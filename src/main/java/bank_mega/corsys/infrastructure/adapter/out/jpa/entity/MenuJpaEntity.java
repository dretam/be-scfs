package bank_mega.corsys.infrastructure.adapter.out.jpa.entity;

import bank_mega.corsys.infrastructure.adapter.out.jpa.entity.embeddable.AuditTrailEmbeddable;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "menus")
public class MenuJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String code;

    private String path;

    private String icon;

    @Column(name = "parent_id")
    private Long parentId;

    @Column(name = "sort_order", nullable = false)
    private Integer sortOrder;

    @Embedded
    private AuditTrailEmbeddable audit;

    @OneToMany(mappedBy = "menu", cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<PermissionJpaEntity> permissions = new HashSet<>();

}
