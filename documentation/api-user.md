# Users API

## Templates

### Audit Trail Fields
- `createdAt`: ISO 8601 timestamp of creation
- `createdBy`: ID of user who created the record
- `updatedAt`: ISO 8601 timestamp of last update (nullable)
- `updatedBy`: ID of user who last updated the record (nullable)
- `deletedAt`: ISO 8601 timestamp of deletion (nullable)
- `deletedBy`: ID of user who deleted the record (nullable)

### Standard Response Format
- `status`: HTTP status code
- `message`: Human-readable status message
- `data`: Response payload object/array

### Pagination Response Format
- `status`: HTTP status code
- `message`: Human-readable status message
- `data`: Array of response objects
- `pagination`: Object containing pagination metadata
  - `currentPage`: Current page number
  - `totalPage`: Total number of pages
  - `perPage`: Number of items per page
  - `total`: Total number of items
  - `count`: Number of items in current page
  - `hasNext`: Boolean indicating if next page exists
  - `hasPrevious`: Boolean indicating if previous page exists
  - `hasContent`: Boolean indicating if page has content

### Role Object
- `id`: Role ID
- `name`: Role name
- `icon`: Role icon (nullable)
- `description`: Role description (nullable)
- `createdAt`: Creation timestamp
- `createdBy`: Creator ID
- `updatedAt`: Last update timestamp (nullable)
- `updatedBy`: Last updater ID (nullable)
- `deletedAt`: Deletion timestamp (nullable)
- `deletedBy`: Deleter ID (nullable)

## Endpoints

### List Users
```
GET /api/v1/users
```

#### Query Parameters
- `page`: Page number (default: 1)
- `perPage`: Items per page (default: 5)
- `filter`: Filter criteria
- `sort`: Sort specification
- `expands`: Expand relations

#### Response
```json
{
  "status": 200,
  "message": "OK",
  "data": [
    {
      "id": 1,
      "name": "John Doe",
      "email": "john.doe@example.com",
      "role": {
        "id": 1,
        "name": "Administrator",
        "icon": "admin-icon",
        "description": "System Administrator",
        "createdAt": "2023-10-15T10:30:00Z",
        "createdBy": 1,
        "updatedAt": "2023-10-15T11:45:00Z",
        "updatedBy": 1,
        "deletedAt": null,
        "deletedBy": null
      },
      "createdAt": "2023-10-15T10:30:00Z",
      "createdBy": 1,
      "updatedAt": "2023-10-15T11:45:00Z",
      "updatedBy": 1,
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

### Get User by ID
```
GET /api/v1/users/{id}
```

#### Path Parameters
- `id`: User ID

#### Query Parameters
- `expands`: Expand relations

#### Response
```json
{
  "status": 200,
  "message": "OK",
  "data": {
    "id": 1,
    "name": "John Doe",
    "email": "john.doe@example.com",
    "role": {
      "id": 1,
      "name": "Administrator",
      "icon": "admin-icon",
      "description": "System Administrator",
      "createdAt": "2023-10-15T10:30:00Z",
      "createdBy": 1,
      "updatedAt": "2023-10-15T11:45:00Z",
      "updatedBy": 1,
      "deletedAt": null,
      "deletedBy": null
    },
    "createdAt": "2023-10-15T10:30:00Z",
    "createdBy": 1,
    "updatedAt": "2023-10-15T11:45:00Z",
    "updatedBy": 1,
    "deletedAt": null,
    "deletedBy": null
  }
}
```

### Create User
```
POST /api/v1/users
```

#### Request Body
```json
{
  "name": "Jane Smith",
  "email": "jane.smith@example.com",
  "password": "securePassword123",
  "roleId": 2
}
```

#### Response
```json
{
  "status": 200,
  "message": "OK",
  "data": {
    "id": 2,
    "name": "Jane Smith",
    "email": "jane.smith@example.com",
    "role": {
      "id": 2,
      "name": "User",
      "icon": "user-icon",
      "description": "Regular User",
      "createdAt": "2023-10-15T10:30:00Z",
      "createdBy": 1,
      "updatedAt": null,
      "updatedBy": null,
      "deletedAt": null,
      "deletedBy": null
    },
    "createdAt": "2023-10-16T09:15:00Z",
    "createdBy": 1,
    "updatedAt": null,
    "updatedBy": null,
    "deletedAt": null,
    "deletedBy": null
  }
}
```

### Update User
```
PUT /api/v1/users
```

#### Request Body
```json
{
  "id": 1,
  "name": "John Smith",
  "email": "john.smith@example.com",
  "roleId": 2
}
```

#### Response
```json
{
  "status": 200,
  "message": "OK",
  "data": {
    "id": 1,
    "name": "John Smith",
    "email": "john.smith@example.com",
    "role": {
      "id": 2,
      "name": "User",
      "icon": "user-icon",
      "description": "Regular User",
      "createdAt": "2023-10-15T10:30:00Z",
      "createdBy": 1,
      "updatedAt": null,
      "updatedBy": null,
      "deletedAt": null,
      "deletedBy": null
    },
    "createdAt": "2023-10-15T10:30:00Z",
    "createdBy": 1,
    "updatedAt": "2023-10-16T10:20:00Z",
    "updatedBy": 1,
    "deletedAt": null,
    "deletedBy": null
  }
}
```

### Soft Delete User
```
DELETE /api/v1/users
```

#### Request Body
```json
{
  "id": 1
}
```

#### Response
```json
{
  "status": 200,
  "message": "OK",
  "data": {
    "id": 1,
    "name": "John Smith",
    "email": "john.smith@example.com",
    "role": {
      "id": 2,
      "name": "User",
      "icon": "user-icon",
      "description": "Regular User",
      "createdAt": "2023-10-15T10:30:00Z",
      "createdBy": 1,
      "updatedAt": null,
      "updatedBy": null,
      "deletedAt": null,
      "deletedBy": null
    },
    "createdAt": "2023-10-15T10:30:00Z",
    "createdBy": 1,
    "updatedAt": "2023-10-16T10:20:00Z",
    "updatedBy": 1,
    "deletedAt": "2023-10-16T10:25:00Z",
    "deletedBy": 1
  }
}
```

### Hard Delete User
```
DELETE /api/v1/users/{id}/destroy
```

#### Path Parameters
- `id`: User ID

#### Response
```json
{
  "status": 200,
  "message": "OK",
  "id": 1
}
```