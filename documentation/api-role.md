# Roles API

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

## Endpoints

### List Roles
```
GET /api/v1/roles
```

#### Query Parameters
- `page`: Page number (default: 1)
- `perPage`: Items per page (default: 5)
- `filter`: Filter criteria
- `sort`: Sort specification

#### Response
```json
{
  "status": 200,
  "message": "OK",
  "data": [
    {
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

### Get Role by ID
```
GET /api/v1/roles/{id}
```

#### Path Parameters
- `id`: Role ID

#### Response
```json
{
  "status": 200,
  "message": "OK",
  "data": {
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
  }
}
```

### Create Role
```
POST /api/v1/roles
```

#### Request Body
```json
{
  "name": "Editor",
  "icon": "editor-icon",
  "description": "Content Editor"
}
```

#### Response
```json
{
  "status": 200,
  "message": "OK",
  "data": {
    "id": 2,
    "name": "Editor",
    "icon": "editor-icon",
    "description": "Content Editor",
    "createdAt": "2023-10-16T09:15:00Z",
    "createdBy": 1,
    "updatedAt": null,
    "updatedBy": null,
    "deletedAt": null,
    "deletedBy": null
  }
}
```

### Update Role
```
PUT /api/v1/roles
```

#### Request Body
```json
{
  "id": 1,
  "name": "Super Admin",
  "icon": "super-admin-icon",
  "description": "Super Administrator"
}
```

#### Response
```json
{
  "status": 200,
  "message": "OK",
  "data": {
    "id": 1,
    "name": "Super Admin",
    "icon": "super-admin-icon",
    "description": "Super Administrator",
    "createdAt": "2023-10-15T10:30:00Z",
    "createdBy": 1,
    "updatedAt": "2023-10-16T10:20:00Z",
    "updatedBy": 1,
    "deletedAt": null,
    "deletedBy": null
  }
}
```

### Soft Delete Role
```
DELETE /api/v1/roles
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
    "name": "Super Admin",
    "icon": "super-admin-icon",
    "description": "Super Administrator",
    "createdAt": "2023-10-15T10:30:00Z",
    "createdBy": 1,
    "updatedAt": "2023-10-16T10:20:00Z",
    "updatedBy": 1,
    "deletedAt": "2023-10-16T10:25:00Z",
    "deletedBy": 1
  }
}
```

### Hard Delete Role
```
DELETE /api/v1/roles/{id}/destroy
```

#### Path Parameters
- `id`: Role ID

#### Response
```json
{
  "status": 200,
  "message": "OK",
  "id": 1
}
```