# Auth API

Base URL: `/api/v1/auth`

All endpoints except login and refresh require JWT Bearer token authentication.

---

## Endpoints

### 1. Get Current Session

Get the currently authenticated user's information.

**Endpoint:** `GET /api/v1/auth/session`

**Authentication:** Required (Bearer Token)

**Query Parameters:**
| Parameter | Type   | Required | Description              |
|-----------|--------|----------|--------------------------|
| expands   | string | No       | Related entities to load |

**Request Example:**
```http
GET /api/v1/auth/session HTTP/1.1
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

### 2. Login

Authenticate user and receive JWT tokens.

**Endpoint:** `POST /api/v1/auth/login`

**Authentication:** Not Required

**Request Body:**
```json
{
  "username": "admin",
  "password": "password123",
  "rememberMe": false
}
```

**Response Example:**
```json
{
  "status": 200,
  "message": "OK",
  "refreshToken": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "accessToken": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
}
```

---

### 3. Refresh Token

Refresh the access token using a valid refresh token.

**Endpoint:** `POST /api/v1/auth/refresh`

**Authentication:** Not Required

**Request Body:**
```json
{
  "refreshToken": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
}
```

**Response Example:**
```json
{
  "status": 200,
  "message": "OK",
  "refreshToken": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "accessToken": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
}
```

---

## Common Response Fields

| Field    | Type    | Description                    |
|----------|---------|--------------------------------|
| status   | integer | HTTP status code               |
| message  | string  | Response message               |
| data     | object  | Response data (varies)         |

## Error Responses

### 401 Unauthorized
```json
{
  "status": 401,
  "message": "Unauthorized",
  "errors": ["Invalid credentials"]
}
```

### 403 Forbidden
```json
{
  "status": 403,
  "message": "Forbidden",
  "errors": ["Access denied"]
}
```
