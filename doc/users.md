# Users API

Base URL: `/api/v1/users`

All endpoints require JWT Bearer token authentication.

---

## Endpoints

### 1. List Users

Get a paginated list of all users.

**Endpoint:** `GET /api/v1/users`

**Authentication:** Required (Bearer Token)

**Query Parameters:**
| Parameter | Type   | Required | Default | Description                          |
|-----------|--------|----------|---------|--------------------------------------|
| page      | int    | No       | 1       | Page number                          |
| perPage   | int    | No       | 5       | Items per page                       |
| filter    | string | No       | -       | Search filter (name, email)          |
| sort      | string | No       | -       | Sort field (audit.createdAt, name)   |
| expands   | string | No       | -       | Related entities to load (e.g., role) |

**Request Example:**
```http
GET /api/v1/users?page=1&perPage=5&filter=john&sort=audit.createdAt&expands=role HTTP/1.1
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
      "name": "John Doe",
      "email": "john@example.com",
      "role": {
        "id": 1,
        "name": "ADMIN",
        "icon": "ph:shield",
        "description": "Administrator role",
        "createdAt": "2024-01-01T00:00:00Z",
        "createdBy": 1,
        "updatedAt": null,
        "updatedBy": null,
        "deletedAt": null,
        "deletedBy": null
      },
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
    "total": 1,
    "count": 1,
    "hasNext": false,
    "hasPrevious": false,
    "hasContent": true
  }
}
```

---

### 2. Get User by ID

Get a single user by their ID.

**Endpoint:** `GET /api/v1/users/{id}`

**Authentication:** Required (Bearer Token)

**Path Parameters:**
| Parameter | Type | Required | Description |
|-----------|------|----------|-------------|
| id        | long | Yes      | User ID     |

**Query Parameters:**
| Parameter | Type   | Required | Description              |
|-----------|--------|----------|--------------------------|
| expands   | string | No       | Related entities to load |

**Request Example:**
```http
GET /api/v1/users/1?expands=role HTTP/1.1
Authorization: Bearer <access_token>
```

**Response Example:**
```json
{
  "status": 200,
  "message": "OK",
  "data": {
    "id": 1,
    "name": "John Doe",
    "email": "john@example.com",
    "role": {
      "id": 1,
      "name": "ADMIN",
      "icon": "ph:shield",
      "description": "Administrator role",
      "createdAt": "2024-01-01T00:00:00Z",
      "createdBy": 1,
      "updatedAt": null,
      "updatedBy": null,
      "deletedAt": null,
      "deletedBy": null
    },
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

### 3. Create User

Create a new user.

**Endpoint:** `POST /api/v1/users`

**Authentication:** Required (Bearer Token)

**Request Body:**
```json
{
  "name": "Jane Smith",
  "email": "jane@example.com",
  "password": "securePassword123",
  "roleId": 2
}
```

**Response Example:**
```json
{
  "status": 200,
  "message": "OK",
  "data": {
    "id": 2,
    "name": "Jane Smith",
    "email": "jane@example.com",
    "role": {
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
    },
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

### 4. Update User

Update an existing user.

**Endpoint:** `PUT /api/v1/users`

**Authentication:** Required (Bearer Token)

**Request Body:**
```json
{
  "id": 2,
  "name": "Jane Williams",
  "email": "jane.williams@example.com",
  "existingPassword": "securePassword123",
  "password": "newSecurePassword456",
  "roleId": 1
}
```

**Response Example:**
```json
{
  "status": 200,
  "message": "OK",
  "data": {
    "id": 2,
    "name": "Jane Williams",
    "email": "jane.williams@example.com",
    "role": {
      "id": 1,
      "name": "ADMIN",
      "icon": "ph:shield",
      "description": "Administrator role",
      "createdAt": "2024-01-01T00:00:00Z",
      "createdBy": 1,
      "updatedAt": null,
      "updatedBy": null,
      "deletedAt": null,
      "deletedBy": null
    },
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

### 5. Soft Delete User

Soft delete a user (marks as deleted but keeps in database).

**Endpoint:** `DELETE /api/v1/users`

**Authentication:** Required (Bearer Token)

**Request Body:**
```json
{
  "id": 2
}
```

**Response Example:**
```json
{
  "status": 200,
  "message": "OK",
  "data": {
    "id": 2,
    "name": "Jane Williams",
    "email": "jane.williams@example.com",
    "role": {
      "id": 1,
      "name": "ADMIN",
      "icon": "ph:shield",
      "description": "Administrator role",
      "createdAt": "2024-01-01T00:00:00Z",
      "createdBy": 1,
      "updatedAt": null,
      "updatedBy": null,
      "deletedAt": null,
      "deletedBy": null
    },
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

### 6. Hard Delete User

Permanently delete a user from the database.

**Endpoint:** `DELETE /api/v1/users/{id}/destroy`

**Authentication:** Required (Bearer Token)

**Path Parameters:**
| Parameter | Type | Required | Description |
|-----------|------|----------|-------------|
| id        | long | Yes      | User ID     |

**Request Example:**
```http
DELETE /api/v1/users/2/destroy HTTP/1.1
Authorization: Bearer <access_token>
```

**Response Example:**
```json
{
  "status": 200,
  "message": "OK",
  "id": 2
}
```

---

## UserResponse Schema

| Field       | Type         | Description                    |
|-------------|--------------|--------------------------------|
| id          | long         | User unique identifier         |
| name        | string       | User's full name               |
| email       | string       | User's email address           |
| role        | RoleResponse | User's role information        |
| createdAt   | datetime     | Creation timestamp             |
| createdBy   | long         | User ID who created            |
| updatedAt   | datetime     | Last update timestamp          |
| updatedBy   | long         | User ID who last updated       |
| deletedAt   | datetime     | Soft delete timestamp          |
| deletedBy   | long         | User ID who soft deleted       |

---

## Validation Rules

| Field    | Rules                                      |
|----------|--------------------------------------------|
| name     | Required, must be unique                   |
| email    | Required, valid email format, must be unique |
| password | Required                                   |
| roleId   | Required, must exist                       |

---

## Error Responses

### 400 Bad Request
```json
{
  "status": 400,
  "message": "Bad Request",
  "errors": [
    "name is required",
    "email must be a valid email address"
  ]
}
```

### 404 Not Found
```json
{
  "status": 404,
  "message": "Not Found",
  "errors": ["User not found with id: 999"]
}
```

### 409 Conflict
```json
{
  "status": 409,
  "message": "Conflict",
  "errors": ["Email already in use"]
}
```
