# Roles API

Base URL: `/api/v1/roles`

All endpoints require JWT Bearer token authentication.

---

## Endpoints

### 1. List Roles

Get a paginated list of all roles.

**Endpoint:** `GET /api/v1/roles`

**Authentication:** Required (Bearer Token)

**Query Parameters:**
| Parameter | Type   | Required | Default | Description                    |
|-----------|--------|----------|---------|--------------------------------|
| page      | int    | No       | 1       | Page number                    |
| perPage   | int    | No       | 5       | Items per page                 |
| filter    | string | No       | -       | Search filter (name, icon)     |
| sort      | string | No       | -       | Sort field (audit.createdAt, name) |

**Request Example:**
```http
GET /api/v1/roles?page=1&perPage=5&filter=admin&sort=audit.createdAt HTTP/1.1
Authorization: Bearer <access_token>
```

**Response Example:**
```json
{
  "status": 200,
  "message": "OK",
  "data": [
    {
      "id": 1,
      "name": "ADMIN",
      "icon": "ph:shield",
      "description": "Administrator role with full access",
      "createdAt": "2024-01-01T00:00:00Z",
      "createdBy": 1,
      "updatedAt": null,
      "updatedBy": null,
      "deletedAt": null,
      "deletedBy": null
    },
    {
      "id": 2,
      "name": "USER",
      "icon": "ph:user",
      "description": "Regular user role",
      "createdAt": "2024-01-01T00:00:00Z",
      "createdBy": 1,
      "updatedAt": null,
      "updatedBy": null,
      "deletedAt": null,
      "deletedBy": null
    }
  ],
  "pagination": {
    "currentPage": 1,
    "totalPage": 1,
    "perPage": 5,
    "total": 2,
    "count": 2,
    "hasNext": false,
    "hasPrevious": false,
    "hasContent": true
  }
}
```

---

### 2. Get Role by ID

Get a single role by its ID.

**Endpoint:** `GET /api/v1/roles/{id}`

**Authentication:** Required (Bearer Token)

**Path Parameters:**
| Parameter | Type | Required | Description |
|-----------|------|----------|-------------|
| id        | long | Yes      | Role ID     |

**Request Example:**
```http
GET /api/v1/roles/1 HTTP/1.1
Authorization: Bearer <access_token>
```

**Response Example:**
```json
{
  "status": 200,
  "message": "OK",
  "data": {
    "id": 1,
    "name": "ADMIN",
    "icon": "ph:shield",
    "description": "Administrator role with full access",
    "createdAt": "2024-01-01T00:00:00Z",
    "createdBy": 1,
    "updatedAt": null,
    "updatedBy": null,
    "deletedAt": null,
    "deletedBy": null
  }
}
```

---

### 3. Create Role

Create a new role.

**Endpoint:** `POST /api/v1/roles`

**Authentication:** Required (Bearer Token)

**Request Body:**
```json
{
  "name": "MANAGER",
  "icon": "ph:briefcase",
  "description": "Manager role with limited access"
}
```

**Response Example:**
```json
{
  "status": 200,
  "message": "OK",
  "data": {
    "id": 3,
    "name": "MANAGER",
    "icon": "ph:briefcase",
    "description": "Manager role with limited access",
    "createdAt": "2024-01-02T00:00:00Z",
    "createdBy": 1,
    "updatedAt": null,
    "updatedBy": null,
    "deletedAt": null,
    "deletedBy": null
  }
}
```

---

### 4. Update Role

Update an existing role.

**Endpoint:** `PUT /api/v1/roles`

**Authentication:** Required (Bearer Token)

**Request Body:**
```json
{
  "id": 3,
  "name": "SENIOR_MANAGER",
  "icon": "ph:star",
  "description": "Senior manager role with extended access"
}
```

**Response Example:**
```json
{
  "status": 200,
  "message": "OK",
  "data": {
    "id": 3,
    "name": "SENIOR_MANAGER",
    "icon": "ph:star",
    "description": "Senior manager role with extended access",
    "createdAt": "2024-01-02T00:00:00Z",
    "createdBy": 1,
    "updatedAt": "2024-01-03T00:00:00Z",
    "updatedBy": 1,
    "deletedAt": null,
    "deletedBy": null
  }
}
```

---

### 5. Soft Delete Role

Soft delete a role (marks as deleted but keeps in database).

**Endpoint:** `DELETE /api/v1/roles`

**Authentication:** Required (Bearer Token)

**Request Body:**
```json
{
  "id": 3
}
```

**Response Example:**
```json
{
  "status": 200,
  "message": "OK",
  "data": {
    "id": 3,
    "name": "SENIOR_MANAGER",
    "icon": "ph:star",
    "description": "Senior manager role with extended access",
    "createdAt": "2024-01-02T00:00:00Z",
    "createdBy": 1,
    "updatedAt": "2024-01-03T00:00:00Z",
    "updatedBy": 1,
    "deletedAt": "2024-01-03T00:00:00Z",
    "deletedBy": 1
  }
}
```

---

### 6. Hard Delete Role

Permanently delete a role from the database.

**Endpoint:** `DELETE /api/v1/roles/{id}/destroy`

**Authentication:** Required (Bearer Token)

**Path Parameters:**
| Parameter | Type | Required | Description |
|-----------|------|----------|-------------|
| id        | long | Yes      | Role ID     |

**Request Example:**
```http
DELETE /api/v1/roles/3/destroy HTTP/1.1
Authorization: Bearer <access_token>
```

**Response Example:**
```json
{
  "status": 200,
  "message": "OK",
  "id": 3
}
```

---

## RoleResponse Schema

| Field       | Type    | Description                    |
|-------------|---------|--------------------------------|
| id          | long    | Role unique identifier         |
| name        | string  | Role name                      |
| icon        | string  | Icon identifier (e.g., ph:shield) |
| description | string  | Role description               |
| createdAt   | datetime| Creation timestamp             |
| createdBy   | long    | User ID who created            |
| updatedAt   | datetime| Last update timestamp          |
| updatedBy   | long    | User ID who last updated       |
| deletedAt   | datetime| Soft delete timestamp          |
| deletedBy   | long    | User ID who soft deleted       |

---

## Error Responses

### 400 Bad Request
```json
{
  "status": 400,
  "message": "Bad Request",
  "errors": ["name is required"]
}
```

### 404 Not Found
```json
{
  "status": 404,
  "message": "Not Found",
  "errors": ["Role not found with id: 999"]
}
```
