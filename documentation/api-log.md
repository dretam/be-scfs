# Access Logs API

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

### List Access Logs
```
GET /api/v1/logs
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
      "userId": 1,
      "username": "john.doe",
      "action": "login",
      "resource": "auth",
      "method": "POST",
      "endpoint": "/api/v1/auth/login",
      "ipAddress": "192.168.1.100",
      "userAgent": "Mozilla/5.0...",
      "responseCode": 200,
      "requestBody": "{\"email\":\"user@example.com\"}",
      "responseBody": "{\"status\":200,\"message\":\"OK\",\"data\":{\"token\":\"...\"}}",
      "createdAt": "2023-10-15T10:30:00Z",
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

### Get Access Log by ID
```
GET /api/v1/logs/{id}
```

#### Path Parameters
- `id`: Access Log ID

#### Response
```json
{
  "status": 200,
  "message": "OK",
  "data": {
    "id": 1,
    "userId": 1,
    "username": "john.doe",
    "action": "login",
    "resource": "auth",
    "method": "POST",
    "endpoint": "/api/v1/auth/login",
    "ipAddress": "192.168.1.100",
    "userAgent": "Mozilla/5.0...",
    "responseCode": 200,
    "requestBody": "{\"email\":\"user@example.com\"}",
    "responseBody": "{\"status\":200,\"message\":\"OK\",\"data\":{\"token\":\"...\"}}",
    "createdAt": "2023-10-15T10:30:00Z",
    "createdBy": 1,
    "updatedAt": null,
    "updatedBy": null,
    "deletedAt": null,
    "deletedBy": null
  }
}
```

### Create Access Log
```
POST /api/v1/logs
```

#### Request Body
```json
{
  "userId": 1,
  "username": "jane.doe",
  "action": "view",
  "resource": "documents",
  "method": "GET",
  "endpoint": "/api/v1/documents/1",
  "ipAddress": "192.168.1.101",
  "userAgent": "Mozilla/5.0...",
  "responseCode": 200,
  "requestBody": "",
  "responseBody": "{\"status\":200,\"message\":\"OK\",\"data\":{...}}"
}
```

#### Response
```json
{
  "status": 200,
  "message": "OK",
  "data": {
    "id": 2,
    "userId": 1,
    "username": "jane.doe",
    "action": "view",
    "resource": "documents",
    "method": "GET",
    "endpoint": "/api/v1/documents/1",
    "ipAddress": "192.168.1.101",
    "userAgent": "Mozilla/5.0...",
    "responseCode": 200,
    "requestBody": "",
    "responseBody": "{\"status\":200,\"message\":\"OK\",\"data\":{...}}",
    "createdAt": "2023-10-16T09:15:00Z",
    "createdBy": 1,
    "updatedAt": null,
    "updatedBy": null,
    "deletedAt": null,
    "deletedBy": null
  }
}
```

### Update Access Log
```
PUT /api/v1/logs
```

#### Request Body
```json
{
  "id": 1,
  "userId": 1,
  "username": "john.doe",
  "action": "login",
  "resource": "auth",
  "method": "POST",
  "endpoint": "/api/v1/auth/login",
  "ipAddress": "192.168.1.100",
  "userAgent": "Mozilla/5.0 (updated)",
  "responseCode": 200,
  "requestBody": "{\"email\":\"user@example.com\"}",
  "responseBody": "{\"status\":200,\"message\":\"OK\",\"data\":{\"token\":\"...\"}}"
}
```

#### Response
```json
{
  "status": 200,
  "message": "OK",
  "data": {
    "id": 1,
    "userId": 1,
    "username": "john.doe",
    "action": "login",
    "resource": "auth",
    "method": "POST",
    "endpoint": "/api/v1/auth/login",
    "ipAddress": "192.168.1.100",
    "userAgent": "Mozilla/5.0 (updated)",
    "responseCode": 200,
    "requestBody": "{\"email\":\"user@example.com\"}",
    "responseBody": "{\"status\":200,\"message\":\"OK\",\"data\":{\"token\":\"...\"}}",
    "createdAt": "2023-10-15T10:30:00Z",
    "createdBy": 1,
    "updatedAt": "2023-10-16T10:20:00Z",
    "updatedBy": 1,
    "deletedAt": null,
    "deletedBy": null
  }
}
```

### Soft Delete Access Log
```
DELETE /api/v1/logs
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
    "userId": 1,
    "username": "john.doe",
    "action": "login",
    "resource": "auth",
    "method": "POST",
    "endpoint": "/api/v1/auth/login",
    "ipAddress": "192.168.1.100",
    "userAgent": "Mozilla/5.0 (updated)",
    "responseCode": 200,
    "requestBody": "{\"email\":\"user@example.com\"}",
    "responseBody": "{\"status\":200,\"message\":\"OK\",\"data\":{\"token\":\"...\"}}",
    "createdAt": "2023-10-15T10:30:00Z",
    "createdBy": 1,
    "updatedAt": "2023-10-16T10:20:00Z",
    "updatedBy": 1,
    "deletedAt": "2023-10-16T10:25:00Z",
    "deletedBy": 1
  }
}
```

### Hard Delete Access Log
```
DELETE /api/v1/logs/{id}/destroy
```

#### Path Parameters
- `id`: Access Log ID

#### Response
```json
{
  "status": 200,
  "message": "OK",
  "id": 1
}
```