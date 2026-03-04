RBAC (Role-Based Access Control) Implementation Plan                                                                                        │  
│                                                                                                                                               │  
│   Overview                                                                                                                                    │  
│   This plan outlines the implementation of a comprehensive RBAC system with Role, User, Permission, and Menu management for the Backend       │  
│   Dashboard TMG application, following the existing hexagonal architecture patterns.                                                          │  
│                                                                                                                                               │  
│   ---                                                                                                                                         │  
│                                                                                                                                               │  
│   1. Database Schema Design                                                                                                                   │  
│                                                                                                                                               │  
│   New Tables to Create                                                                                                                        │  
│                                                                                                                                               │  
│   permissions Table                                                                                                                           │  
│    ... first 6 lines hidden ...                                                                                                               │  
│     7     created_at  DATETIME DEFAULT CURRENT_TIMESTAMP,                                                                                     │
│     8     updated_by  BIGINT,                                                                                                                 │  
│     9     updated_at  DATETIME ON UPDATE CURRENT_TIMESTAMP,                                                                                   │  
│    10     deleted_by  BIGINT,                                                                                                                 │  
│    11     deleted_at  DATETIME                                                                                                                │  
│    12 );                                                                                                                                      │  
│                                                                                                                                               │  
│   menus Table                                                                                                                                 │  
│    ... first 11 lines hidden ...                                                                                                              │  
│    12     updated_by  BIGINT,                                                                                                                 │  
│    13     updated_at  DATETIME ON UPDATE CURRENT_TIMESTAMP,                                                                                   │  
│    14     deleted_by  BIGINT,                                                                                                                 │  
│    15     deleted_at  DATETIME,                                                                                                               │  
│    16     FOREIGN KEY (parent_id) REFERENCES menus(id)                                                                                        │  
│    17 );                                                                                                                                      │  
│                                                                                                                                               │  
│   role_permissions Table (Many-to-Many)                                                                                                       │  
│    ... first 3 lines hidden ...                                                                                                               │  
│    4     created_by    BIGINT NOT NULL DEFAULT 0,                                                                                             │  
│    5     created_at    DATETIME DEFAULT CURRENT_TIMESTAMP,                                                                                    │  
│    6     PRIMARY KEY (role_id, permission_id),                                                                                                │  
│    7     FOREIGN KEY (role_id) REFERENCES roles(id),                                                                                          │  
│    8     FOREIGN KEY (permission_id) REFERENCES permissions(id)                                                                               │  
│    9 );                                                                                                                                       │  
│                                                                                                                                               │  
│   role_menus Table (Many-to-Many)                                                                                                             │  
│    ... first 3 lines hidden ...                                                                                                               │  
│    4     created_by BIGINT NOT NULL DEFAULT 0,                                                                                                │  
│    5     created_at DATETIME DEFAULT CURRENT_TIMESTAMP,                                                                                       │  
│    6     PRIMARY KEY (role_id, menu_id),                                                                                                      │  
│    7     FOREIGN KEY (role_id) REFERENCES roles(id),                                                                                          │  
│    8     FOREIGN KEY (menu_id) REFERENCES menus(id)                                                                                           │  
│    9 );                                                                                                                                       │  
│                                                                                                                                               │  
│   Migration Files                                                                                                                             │  
│    - MySQL: src/main/resources/db/migration/mysql/V1_1_10__Create_RBAC_Tables.sql                                                             │  
│    - PostgreSQL: src/main/resources/db/migration/postgresql/V1_1_10__Create_RBAC_Tables.sql                                                   │  
│                                                                                                                                               │  
│   ---                                                                                                                                         │  
│                                                                                                                                               │  
│   2. Domain Layer Implementation                                                                                                              │  
│                                                                                                                                               │  
│   2.1 Permission Domain Models                                                                                                                │  
│   Location: src/main/java/bank_mega/corsys/domain/model/permission/                                                                           │  
│                                                                                                                                               │  
│    - Permission.java - Main entity with methods: changeName(), changeCode(), changeDescription()                                              │  
│    - PermissionId.java - Value object for ID                                                                                                  │  
│    - PermissionCode.java - Value object for code                                                                                              │  
│    - PermissionName.java - Value object for name                                                                                              │  
│                                                                                                                                               │  
│   2.2 Menu Domain Models                                                                                                                      │  
│   Location: src/main/java/bank_mega/corsys/domain/model/menu/                                                                                 │  
│                                                                                                                                               │  
│    - Menu.java - Main entity with methods: changeName(), changePath(), changeIcon(), changeParent()                                           │  
│    - MenuId.java - Value object for ID                                                                                                        │  
│    - MenuCode.java - Value object for code                                                                                                    │  
│    - MenuName.java - Value object for name                                                                                                    │  
│                                                                                                                                               │  
│   2.3 Update Role Entity                                                                                                                      │  
│   Location: src/main/java/bank_mega/corsys/domain/model/role/Role.java                                                                        │  
│                                                                                                                                               │  
│   Add:                                                                                                                                        │  
│    - Set<Permission> permissions - Collection of permissions                                                                                  │  
│    - Set<Menu> menus - Collection of menus                                                                                                    │  
│    - Methods: addPermission(), removePermission(), addMenu(), removeMenu()                                                                    │  
│                                                                                                                                               │  
│   2.4 Repository Interfaces                                                                                                                   │  
│   Location: src/main/java/bank_mega/corsys/domain/repository/                                                                                 │  
│                                                                                                                                               │  
│    - PermissionRepository.java - CRUD operations + findFirstByCode(), findAllByRoleId()                                                       │  
│    - MenuRepository.java - CRUD operations + findAllByRoleId(), findAllActiveByParentId()                                                     │  
│                                                                                                                                               │  
│   ---                                                                                                                                         │  
│                                                                                                                                               │  
│   3. Application Layer Implementation                                                                                                         │  
│                                                                                                                                               │  
│   3.1 Permission Use Cases                                                                                                                    │  
│   Location: src/main/java/bank_mega/corsys/application/permission/                                                                            │  
│                                                                                                                                               │  
│    ... first 8 lines hidden ...                                                                                                               │  
│     9     ├── CreatePermissionUseCase.java                                                                                                    │  
│    10     ├── UpdatePermissionUseCase.java                                                                                                    │  
│    11     ├── RetrievePermissionUseCase.java                                                                                                  │  
│    12     ├── PagePermissionUseCase.java                                                                                                      │  
│    13     ├── SoftDeletePermissionUseCase.java                                                                                                │  
│    14     └── DeletePermissionUseCase.java                                                                                                    │  
│                                                                                                                                               │  
│   3.2 Menu Use Cases                                                                                                                          │  
│   Location: src/main/java/bank_mega/corsys/application/menu/                                                                                  │  
│                                                                                                                                               │  
│    ... first 9 lines hidden ...                                                                                                               │  
│    10     ├── UpdateMenuUseCase.java                                                                                                          │  
│    11     ├── RetrieveMenuUseCase.java                                                                                                        │  
│    12     ├── PageMenuUseCase.java                                                                                                            │  
│    13     ├── SoftDeleteMenuUseCase.java                                                                                                      │  
│    14     ├── DeleteMenuUseCase.java                                                                                                          │  
│    15     └── GetMenusByRoleUseCase.java                                                                                                      │  
│                                                                                                                                               │  
│   3.3 Update Role Use Cases                                                                                                                   │  
│   Update existing Role use cases to support permission/menu assignment:                                                                       │  
│    - CreateRoleUseCase.java - Add permissions and menus to role creation                                                                      │  
│    - UpdateRoleUseCase.java - Add permissions and menus to role update                                                                        │  
│                                                                                                                                               │  
│   3.4 Update User Use Cases                                                                                                                   │  
│   Update CreateUserCommand.java and UpdateUserCommand.java to support role assignment with permissions                                        │  
│                                                                                                                                               │  
│   3.5 Assemblers                                                                                                                              │  
│    - PermissionAssembler.java - Convert between domain and DTO                                                                                │  
│    - MenuAssembler.java - Convert between domain and DTO                                                                                      │  
│    - Update RoleAssembler.java - Include permissions and menus in response                                                                    │  
│                                                                                                                                               │  
│   ---                                                                                                                                         │  
│                                                                                                                                               │  
│   4. Infrastructure Layer Implementation                                                                                                      │  
│                                                                                                                                               │  
│   4.1 JPA Entities                                                                                                                            │  
│   Location: src/main/java/bank_mega/corsys/infrastructure/adapter/out/jpa/entity/                                                             │  
│                                                                                                                                               │  
│    - PermissionJpaEntity.java - JPA entity with audit fields                                                                                  │  
│    - MenuJpaEntity.java - JPA entity with self-referencing parent relationship                                                                │  
│    - Update RoleJpaEntity.java - Add @ManyToMany relationships for permissions and menus                                                      │  
│                                                                                                                                               │  
│   4.2 Spring Data Repositories                                                                                                                │  
│   Location: src/main/java/bank_mega/corsys/infrastructure/adapter/out/jpa/repository/                                                         │  
│                                                                                                                                               │  
│    - SpringDataPermissionJpaRepository.java                                                                                                   │  
│    - SpringDataMenuJpaRepository.java                                                                                                         │  
│                                                                                                                                               │  
│   4.3 Mappers                                                                                                                                 │  
│   Location: src/main/java/bank_mega/corsys/infrastructure/adapter/out/mapper/                                                                 │  
│                                                                                                                                               │  
│    - PermissionMapper.java - Convert between JPA entity and domain                                                                            │  
│    - MenuMapper.java - Convert between JPA entity and domain                                                                                  │  
│    - Update RoleMapper.java - Handle permission/menu mapping                                                                                  │  
│                                                                                                                                               │  
│   4.4 Repository Implementations                                                                                                              │  
│   Location: src/main/java/bank_mega/corsys/infrastructure/adapter/out/repo/                                                                   │  
│                                                                                                                                               │  
│    - PermissionRepositoryImpl.java - Implementation of PermissionRepository                                                                   │  
│    - MenuRepositoryImpl.java - Implementation of MenuRepository                                                                               │  
│                                                                                                                                               │  
│   4.5 Predicates                                                                                                                              │  
│   Location: src/main/java/bank_mega/corsys/infrastructure/adapter/out/predicate/                                                              │  
│                                                                                                                                               │  
│    - PermissionPredicate.java - Dynamic query filtering                                                                                       │  
│    - MenuPredicate.java - Dynamic query filtering                                                                                             │  
│                                                                                                                                               │  
│   4.6 API Controllers                                                                                                                         │  
│   Location: src/main/java/bank_mega/corsys/infrastructure/adapter/in/api/v1/                                                                  │  
│                                                                                                                                               │  
│    - PermissionApi.java - REST endpoints for permissions                                                                                      │  
│    - MenuApi.java - REST endpoints for menus                                                                                                  │  
│    - Update RoleApi.java - Add endpoints for role-permission and role-menu management                                                         │  
│                                                                                                                                               │  
│   4.7 Validation                                                                                                                              │  
│   Location: src/main/java/bank_mega/corsys/infrastructure/adapter/in/validation/                                                              │  
│                                                                                                                                               │  
│    - permission/ - PermissionIdExist, PermissionCodeAvailable                                                                                 │  
│    - menu/ - MenuIdExist, MenuCodeAvailable, MenuParentIdExist                                                                                │  
│                                                                                                                                               │  
│   ---                                                                                                                                         │  
│                                                                                                                                               │  
│   5. Security & Authorization                                                                                                                 │  
│                                                                                                                                               │  
│   5.1 Permission-Based Authorization                                                                                                          │  
│   Location: src/main/java/bank_mega/corsys/infrastructure/config/security/                                                                    │  
│                                                                                                                                               │  
│    - PermissionEvaluator.java - Evaluate if user has permission                                                                               │  
│    - HasPermissionAnnotation.java - Custom annotation @HasPermission("USER_CREATE")                                                           │  
│    - HasPermissionAspect.java - AOP aspect for permission checking                                                                            │  
│                                                                                                                                               │  
│   5.2 Update Security Configuration                                                                                                           │  
│   Update SecurityConfiguration.java to include permission-based access control                                                                │  
│                                                                                                                                               │  
│   5.3 Update UserTokenAuthentication                                                                                                          │  
│   Include permissions in authentication token for efficient access checking                                                                   │  
│                                                                                                                                               │  
│   ---                                                                                                                                         │  
│                                                                                                                                               │  
│   6. Data Seeder                                                                                                                              │  
│                                                                                                                                               │  
│   6.1 RBAC Seeder                                                                                                                             │  
│   Location: src/main/java/bank_mega/corsys/infrastructure/config/seeder/RbacSeeder.java                                                       │  
│                                                                                                                                               │  
│   Seed initial data:                                                                                                                          │  
│    - Permissions: USER_CREATE, USER_READ, USER_UPDATE, USER_DELETE, ROLE_CREATE, ROLE_READ, ROLE_UPDATE, ROLE_DELETE, PERMISSION_CREATE,      │  
│      PERMISSION_READ, PERMISSION_UPDATE, PERMISSION_DELETE, MENU_CREATE, MENU_READ, MENU_UPDATE, MENU_DELETE                                  │  
│    - Menus: Dashboard, User Management, Role Management, Permission Management, Menu Management                                               │  
│    - Role-Permission assignments:                                                                                                             │  
│      - ROLE_SU: All permissions                                                                                                               │  
│      - ROLE_ADMIN: USER_*, ROLE_*, PERMISSION_READ, MENU_*                                                                                    │  
│      - ROLE_VIEW: USER_READ, ROLE_READ, PERMISSION_READ, MENU_READ                                                                            │  
│                                                                                                                                               │  
│   ---                                                                                                                                         │  
│                                                                                                                                               │  
│   7. API Endpoints Design                                                                                                                     │  
│                                                                                                                                               │  
│   Permissions                                                                                                                                 │  
│    1 GET    /api/v1/permissions              - List all permissions (paginated)                                                               │  
│    2 GET    /api/v1/permissions/{id}         - Get permission by ID                                                                           │  
│    3 POST   /api/v1/permissions              - Create permission                                                                              │  
│    4 PUT    /api/v1/permissions              - Update permission                                                                              │  
│    5 DELETE /api/v1/permissions              - Soft delete permission                                                                         │  
│    6 DELETE /api/v1/permissions/{id}/destroy - Hard delete permission                                                                         │  
│                                                                                                                                               │  
│   Menus                                                                                                                                       │  
│    ... first 2 lines hidden ...                                                                                                               │  
│    3 GET    /api/v1/menus/tree               - Get menu tree structure                                                                        │  
│    4 GET    /api/v1/menus/role/{roleId}      - Get menus by role                                                                              │  
│    5 POST   /api/v1/menus                    - Create menu                                                                                    │  
│    6 PUT    /api/v1/menus                    - Update menu                                                                                    │  
│    7 DELETE /api/v1/menus                    - Soft delete menu                                                                               │  
│    8 DELETE /api/v1/menus/{id}/destroy       - Hard delete menu                                                                               │  
│                                                                                                                                               │  
│   Roles (Updated)                                                                                                                             │  
│    1 POST   /api/v1/roles/{id}/permissions   - Assign permissions to role                                                                     │  
│    2 DELETE /api/v1/roles/{id}/permissions   - Remove permissions from role                                                                   │  
│    3 POST   /api/v1/roles/{id}/menus         - Assign menus to role                                                                           │  
│    4 DELETE /api/v1/roles/{id}/menus         - Remove menus from role                                                                         │  
│                                                                                                                                               │  
│   ---                                                                                                                                         │  
│                                                                                                                                               │  
│   8. File Structure Summary                                                                                                                   │  
│                                                                                                                                               │  
│    ... first 54 lines hidden ...                                                                                                              │  
│    55     └── config/                                                                                                                         │  
│    56         ├── security/                                                                                                                   │  
│    57         │   ├── PermissionEvaluator.java      # NEW                                                                                     │  
│    58         │   └── HasPermissionAnnotation.java  # NEW                                                                                     │  
│    59         └── seeder/                                                                                                                     │  
│    60             └── RbacSeeder.java               # NEW                                                                                     │  
│                                                                                                                                               │  
│   ---                                                                                                                                         │  
│                                                                                                                                               │  
│   9. Implementation Phases                                                                                                                    │  
│                                                                                                                                               │  
│   Phase 1: Core Domain & Database (Priority: HIGH)                                                                                            │  
│    1. Create database migration scripts                                                                                                       │  
│    2. Implement Permission domain models                                                                                                      │  
│    3. Implement Menu domain models                                                                                                            │  
│    4. Update Role domain model                                                                                                                │  
│                                                                                                                                               │  
│   Phase 2: Repository Layer (Priority: HIGH)                                                                                                  │  
│    1. Create JPA entities                                                                                                                     │  
│    2. Create Spring Data repositories                                                                                                         │  
│    3. Implement repository interfaces                                                                                                         │  
│    4. Create mappers and predicates                                                                                                           │  
│                                                                                                                                               │  
│   Phase 3: Application Layer (Priority: HIGH)                                                                                                 │  
│    1. Implement Permission use cases                                                                                                          │  
│    2. Implement Menu use cases                                                                                                                │  
│    3. Update Role use cases                                                                                                                   │  
│    4. Create assemblers                                                                                                                       │  
│                                                                                                                                               │  
│   Phase 4: API Layer (Priority: HIGH)                                                                                                         │  
│    1. Create Permission API controller                                                                                                        │  
│    2. Create Menu API controller                                                                                                              │  
│    3. Update Role API controller                                                                                                              │  
│    4. Add validation annotations                                                                                                              │  
│                                                                                                                                               │  
│   Phase 5: Security & Authorization (Priority: MEDIUM)                                                                                        │  
│    1. Implement permission evaluator                                                                                                          │  
│    2. Create @HasPermission annotation                                                                                                        │  
│    3. Update security configuration                                                                                                           │  
│    4. Update authentication token                                                                                                             │  
│                                                                                                                                               │  
│   Phase 6: Data Seeding & Testing (Priority: MEDIUM)                                                                                          │  
│    1. Create RBAC seeder                                                                                                                      │  
│    2. Seed initial permissions                                                                                                                │  
│    3. Seed initial menus                                                                                                                      │  
│    4. Seed role-permission mappings                                                                                                           │  
│                                                                                                                                               │  
│   ---                                                                                                                                         │  
│                                                                                                                                               │  
│   10. Key Design Decisions                                                                                                                    │  
│                                                                                                                                               │  
│    1. Many-to-Many Relationships: Role-Permission and Role-Menurelationships use join tables for flexibility                                  │  
│    2. Soft Delete: All entities follow existing audit pattern with soft delete                                                                │  
│    3. Hierarchical Menus: Self-referencing parent relationship for nested menus                                                               │  
│    4. Permission Codes: Use uppercase snake_case (e.g., `USER_CREATE`) for consistency                                                        │  
│    5. Menu Codes: Use uppercase with prefix (e.g., MENU_USER_MANAGEMENT)                                                                      │  
│    6. Authorization: Combine role-based (existing) with permission-based (new) authorization                                                  │  
│    7. Hexagonal Architecture: Follow existing patterns for consistency                                                                        │  
│                                                                                                                                               │  
│   ---                                                                                                                                         │  
│                                                                                                                                               │  
│   11. Testing Strategy                                                                                                                        │  
│                                                                                                                                               │  
│    1. Unit Tests: Domain models, use cases, validators                                                                                        │  
│    2. Integration Tests: Repository implementations, API endpoints                                                                            │  
│    3. Security Tests: Permission authorization, role assignments                                                                              │  
│                                                                                                                                               │  
│   ---                                                                                                                                         │  
│                                                                                                                                               │  
│   12. Documentation Updates                                                                                                                   │  
│                                                                                                                                               │  
│   Update documentation.md with:                                                                                                               │  
│    - RBAC architecture overview                                                                                                               │  
│    - Permission code conventions                                                                                                              │  
│    - Menu structure guidelines                                                                                                                │  
│    - API endpoint documentation                                                                                                               │  
│    - Security configuration guide                                                                                                             │  
│                                        