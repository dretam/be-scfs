# Logs API (Access Logs)

Base URL: `/api/v1/logs`

All endpoints require JWT Bearer token authentication.

---

## Endpoints

### 1. List Access Logs

Get a paginated list of all access logs.

**Endpoint:** `GET /api/v1/logs/access`

**Authentication:** Required (Bearer Token)

**Query Parameters:**
| Parameter | Type   | Required | Default | Description                                    |
|-----------|--------|----------|---------|------------------------------------------------|
| page      | int    | No       | 1       | Page number                                    |
| perPage   | int    | No       | 5       | Items per page                                 |
| filter    | string | No       | -       | Search filter (uri, ipAddress, user_agent)     |
| sort      | string | No       | -       | Sort field (createdAt, statusCode, responseTimeMs) |
| expands   | string | No       | -       | Related entities to load (e.g., user)          |

**Request Example:**
```http
GET /api/v1/logs/access?page=1&perPage=5&filter=/api&sort=createdAt&expands=user HTTP/1.1
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
      "user": {
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
      },
      "ipAddress": "192.168.1.100",
      "userAgent": "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36",
      "uri": "/api/v1/users",
      "queryParams": "page=1&perPage=5",
      "requestBody": null,
      "statusCode": 200,
      "responseTimeMs": 45,
      "errorMessage": null,
      "httpMethod": "GET",
      "createdAt": "2024-01-01T10:00:00Z"
    },
    {
      "id": 2,
      "user": {
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
        "createdAt": "2024-01-01T00:00:00Z",
        "createdBy": 1,
        "updatedAt": null,
        "updatedBy": null,
        "deletedAt": null,
        "deletedBy": null
      },
      "ipAddress": "192.168.1.101",
      "userAgent": "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36",
      "uri": "/api/v1/documents",
      "queryParams": null,
      "requestBody": "{\"name\":\"test\",\"email\":\"test@example.com\"}",
      "statusCode": 201,
      "responseTimeMs": 120,
      "errorMessage": null,
      "httpMethod": "POST",
      "createdAt": "2024-01-01T10:05:00Z"
    }
  ],
  "pagination": {
    "currentPage": 1,
    "totalPage": 10,
    "perPage": 5,
    "total": 50,
    "count": 5,
    "hasNext": true,
    "hasPrevious": false,
    "hasContent": true
  }
}
```

---

### 2. Get Access Log by ID

Get a single access log by its ID.

**Endpoint:** `GET /api/v1/logs/access/{id}`

**Authentication:** Required (Bearer Token)

**Path Parameters:**
| Parameter | Type | Required | Description    |
|-----------|------|----------|----------------|
| id        | long | Yes      | Access Log ID  |

**Query Parameters:**
| Parameter | Type   | Required | Description              |
|-----------|--------|----------|--------------------------|
| expands   | string | No       | Related entities to load |

**Request Example:**
```http
GET /api/v1/logs/access/1?expands=user HTTP/1.1
Authorization: Bearer <access_token>
```

**Response Example:**
```json
{
  "status": 200,
  "message": "OK",
  "data": {
    "id": 1,
    "user": {
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
    },
    "ipAddress": "192.168.1.100",
    "userAgent": "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36",
    "uri": "/api/v1/users",
    "queryParams": "page=1&perPage=5",
    "requestBody": null,
    "statusCode": 200,
    "responseTimeMs": 45,
    "errorMessage": null,
    "httpMethod": "GET",
    "createdAt": "2024-01-01T10:00:00Z"
  }
}
```

---

## AccessLogResponse Schema

| Field          | Type               | Description                              |
|----------------|--------------------|------------------------------------------|
| id             | long               | Access log unique identifier             |
| user           | UserResponse       | User who made the request                |
| ipAddress      | string             | Client IP address                        |
| userAgent      | string             | Client user agent string                 |
| uri            | string             | Request URI                              |
| queryParams    | string             | Query parameters (if any)                |
| requestBody    | string             | Request body (for POST/PUT requests)     |
| statusCode     | integer            | HTTP response status code                |
| responseTimeMs | long               | Response time in milliseconds            |
| errorMessage   | string             | Error message (if request failed)        |
| httpMethod     | enum               | HTTP method (GET, POST, PUT, DELETE, etc.) |
| createdAt      | datetime           | Request timestamp                        |

---

## HTTP Methods Enum

| Value      | Description           |
|------------|-----------------------|
| GET        | GET request           |
| POST       | POST request          |
| PUT        | PUT request           |
| PATCH      | PATCH request         |
| DELETE     | DELETE request        |
| HEAD       | HEAD request          |
| CONNECT    | CONNECT request       |
| OPTIONS    | OPTIONS request       |
| TRACE      | TRACE request         |

---

## Filter Examples

### Filter by URI
```http
GET /api/v1/logs/access?filter=/api/v1/users HTTP/1.1
Authorization: Bearer <access_token>
```

### Filter by IP Address
```http
GET /api/v1/logs/access?filter=192.168.1 HTTP/1.1
Authorization: Bearer <access_token>
```

### Sort by Response Time (Descending)
```http
GET /api/v1/logs/access?sort=audit.createdAt&order=desc HTTP/1.1
Authorization: Bearer <access_token>
```

### Filter Error Responses Only
```http
GET /api/v1/logs/access?filter=500 HTTP/1.1
Authorization: Bearer <access_token>
```

---

## Use Cases

### 1. Audit Trail
Track all user actions in the system for compliance and security purposes.

### 2. Performance Monitoring
Identify slow endpoints by analyzing `responseTimeMs`.

### 3. Error Tracking
Find failed requests by filtering on `statusCode` >= 400 or `errorMessage` is not null.

### 4. User Activity Analysis
Track specific user activity by filtering on `user.id`.

### 5. Security Analysis
Identify suspicious activity by analyzing IP addresses and request patterns.

---

## Error Responses

### 404 Not Found
```json
{
  "status": 404,
  "message": "Not Found",
  "errors": ["Access Log not found with id: 999"]
}
```

### 403 Forbidden
```json
{
  "status": 403,
  "message": "Forbidden",
  "errors": ["Access denied to view logs"]
}
```
