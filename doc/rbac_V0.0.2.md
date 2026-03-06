# RBAC + Permission Override Design

## Overview

This document describes the RBAC implementation used in the backend
dashboard system.

The system combines:

-   **Role Based Access Control (RBAC)** for default permissions
-   **User Permission Overrides** for special cases

### Permission Resolution

    FINAL_PERMISSIONS =
    (role_permissions + user_allow_permissions) - user_deny_permissions

Example:

Scenario                       Result
  ------------------------------ ---------------------
Admin but cannot delete user   DENY USER_DELETE
Viewer but can export          ALLOW REPORT_EXPORT

------------------------------------------------------------------------

# 1. Database Schema

## menus

Hierarchical menu structure.

``` sql
CREATE TABLE menus (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    code VARCHAR(100) UNIQUE NOT NULL,
    name VARCHAR(150) NOT NULL,
    path VARCHAR(255),
    icon VARCHAR(100),
    parent_id BIGINT,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (parent_id) REFERENCES menus(id)
);
```

------------------------------------------------------------------------

## permissions

Permissions belong to a menu feature.

``` sql
CREATE TABLE permissions (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    code VARCHAR(100) UNIQUE NOT NULL,
    name VARCHAR(150) NOT NULL,
    description TEXT,
    menu_id BIGINT,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (menu_id) REFERENCES menus(id)
);
```

Example permissions:

    USER_CREATE
    USER_READ
    USER_UPDATE
    USER_DELETE
    ROLE_CREATE
    ROLE_READ
    ROLE_UPDATE
    ROLE_DELETE
    MENU_CREATE
    MENU_READ
    MENU_UPDATE
    MENU_DELETE

------------------------------------------------------------------------

## role_permissions

Default permissions attached to roles.

``` sql
CREATE TABLE role_permissions (
    role_id BIGINT NOT NULL,
    permission_id BIGINT NOT NULL,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (role_id, permission_id),
    FOREIGN KEY (role_id) REFERENCES roles(id),
    FOREIGN KEY (permission_id) REFERENCES permissions(id)
);
```

------------------------------------------------------------------------

## user_permissions (Override)

Allows overriding role permissions.

``` sql
CREATE TABLE user_permissions (
    user_id BIGINT NOT NULL,
    permission_id BIGINT NOT NULL,
    effect ENUM('ALLOW','DENY') NOT NULL,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (user_id, permission_id),
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (permission_id) REFERENCES permissions(id)
);
```

Effect meaning:

Effect   Description
  -------- ------------------------------
ALLOW    Adds permission to user
DENY     Removes permission from role

------------------------------------------------------------------------

# 2. Domain Model

## Role

    Role
     ├─ id
     ├─ name
     ├─ code
     └─ permissions (Set<Permission>)

## Permission

    Permission
     ├─ id
     ├─ code
     ├─ name
     ├─ description
     └─ menu

## Menu

    Menu
     ├─ id
     ├─ code
     ├─ name
     ├─ path
     ├─ icon
     └─ parent

## UserPermission

    UserPermission
     ├─ userId
     ├─ permissionId
     └─ effect (ALLOW / DENY)

------------------------------------------------------------------------

# 3. Simplified JPA Entities

## PermissionJpaEntity

``` java
@Entity
@Table(name = "permissions")
public class PermissionJpaEntity {

    @Id
    private Long id;

    private String code;

    private String name;

    @ManyToOne
    @JoinColumn(name = "menu_id")
    private MenuJpaEntity menu;
}
```

## MenuJpaEntity

``` java
@Entity
@Table(name = "menus")
public class MenuJpaEntity {

    @Id
    private Long id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "parent_id")
    private MenuJpaEntity parent;
}
```

## RoleJpaEntity

``` java
@Entity
@Table(name = "roles")
public class RoleJpaEntity {

    @Id
    private Long id;

    private String name;

    @ManyToMany
    @JoinTable(
        name = "role_permissions",
        joinColumns = @JoinColumn(name = "role_id"),
        inverseJoinColumns = @JoinColumn(name = "permission_id")
    )
    private Set<PermissionJpaEntity> permissions;
}
```

## UserPermissionJpaEntity

``` java
@Entity
@Table(name = "user_permissions")
public class UserPermissionJpaEntity {

    @EmbeddedId
    private UserPermissionId id;

    @Enumerated(EnumType.STRING)
    private Effect effect;

    @ManyToOne
    @MapsId("userId")
    private UserJpaEntity user;

    @ManyToOne
    @MapsId("permissionId")
    private PermissionJpaEntity permission;
}
```

------------------------------------------------------------------------

# 4. Permission Resolution Example

Example data:

Role permissions:

    USER_CREATE
    USER_READ
    USER_UPDATE
    USER_DELETE

User overrides:

    DENY USER_DELETE
    ALLOW REPORT_EXPORT

Final permissions:

    USER_CREATE
    USER_READ
    USER_UPDATE
    REPORT_EXPORT

------------------------------------------------------------------------

# 5. UI Flow

### Step 1 --- Admin selects role

    Role: ROLE_ADMIN

Default permissions automatically loaded.

### Step 2 --- Admin adjusts permissions

Example:

    ☑ USER_CREATE
    ☑ USER_READ
    ☑ USER_UPDATE
    ☐ USER_DELETE
    ☑ REPORT_EXPORT

### Step 3 --- Backend stores overrides

    USER_DELETE → DENY
    REPORT_EXPORT → ALLOW

------------------------------------------------------------------------

# 6. Final Architecture

    Menu
      └── Permissions

    Role
      └── Permissions

    User
      ├── Role
      └── Permission Overrides
