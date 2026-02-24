# Backend Dashboard TMG - API Documentation

This folder contains comprehensive API documentation for the Backend Dashboard TMG application.

## Overview

The application is built using **Hexagonal Architecture** (Ports and Adapters) with Spring Boot 3.5.9 and Java 21.

**Base URL:** `/api/v1`

**Authentication:** JWT Bearer Token (except login/refresh endpoints)

---

## Table of Contents

| Entity | File | Description |
|--------|------|-------------|
| Authentication | [auth.md](./auth.md) | Login, logout, token refresh, session management |
| Roles | [roles.md](./roles.md) | Role management (CRUD operations) |
| Users | [users.md](./users.md) | User management (CRUD operations) |
| Documents | [documents.md](./documents.md) | Document upload, management, and OCR processing |
| OCR Data | [ocr.md](./ocr.md) | OCR data management (read, update, delete) |
| Access Logs | [logs.md](./logs.md) | Access log viewing and filtering |

---

## Common Response Formats

### Success Response (Single Item)
```json
{
  "status": 200,
  "message": "OK",
  "data": { ... }
}
```

### Success Response (List with Pagination)
```json
{
  "status": 200,
  "message": "OK",
  "data": [ ... ],
  "pagination": {
    "currentPage": 1,
    "totalPage": 5,
    "perPage": 10,
    "total": 50,
    "count": 10,
    "hasNext": true,
    "hasPrevious": false,
    "hasContent": true
  }
}
```

### Error Response
```json
{
  "status": 400,
  "message": "Bad Request",
  "errors": [
    "Error message 1",
    "Error message 2"
  ]
}
```

---

## Authentication

All endpoints (except `/api/v1/auth/login` and `/api/v1/auth/refresh`) require a valid JWT Bearer token.

**Header Format:**
```
Authorization: Bearer <your_access_token>
```

### Obtaining a Token

**Request:**
```http
POST /api/v1/auth/login
Content-Type: application/json

{
  "username": "admin",
  "password": "password123",
  "rememberMe": false
}
```

**Response:**
```json
{
  "status": 200,
  "message": "OK",
  "refreshToken": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "accessToken": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
}
```

---

## Common Query Parameters

| Parameter | Type   | Default | Description                    |
|-----------|--------|---------|--------------------------------|
| page      | int    | 1       | Page number for pagination     |
| perPage   | int    | 5       | Items per page                 |
| filter    | string | -       | Search filter                  |
| sort      | string | -       | Sort field                     |
| expands   | string | -       | Related entities to include    |

---

## HTTP Status Codes

| Code | Description | Meaning |
|------|-------------|---------|
| 200  | OK          | Request successful |
| 201  | Created     | Resource created successfully |
| 400  | Bad Request | Invalid request parameters |
| 401  | Unauthorized | Missing or invalid authentication |
| 403  | Forbidden   | Insufficient permissions |
| 404  | Not Found   | Resource not found |
| 409  | Conflict    | Resource already exists |
| 413  | Payload Too Large | File size exceeds limit |
| 422  | Unprocessable Entity | Invalid data format |
| 500  | Internal Server Error | Server error |

---

## Rate Limiting

All endpoints are protected by rate limiting:
- **Limit:** 50 requests per second
- **Header:** `X-RateLimit-Remaining` shows remaining requests

---

## API Endpoints Summary

### Auth (`/api/v1/auth`)
| Method | Endpoint | Description |
|--------|----------|-------------|
| GET    | `/session` | Get current user session |
| POST   | `/login` | Login and get tokens |
| POST   | `/refresh` | Refresh access token |

### Roles (`/api/v1/roles`)
| Method | Endpoint | Description |
|--------|----------|-------------|
| GET    | `/` | List roles (paginated) |
| GET    | `/{id}` | Get role by ID |
| POST   | `/` | Create role |
| PUT    | `/` | Update role |
| DELETE | `/` | Soft delete role |
| DELETE | `/{id}/destroy` | Hard delete role |

### Users (`/api/v1/users`)
| Method | Endpoint | Description |
|--------|----------|-------------|
| GET    | `/` | List users (paginated) |
| GET    | `/{id}` | Get user by ID |
| POST   | `/` | Create user |
| PUT    | `/` | Update user |
| DELETE | `/` | Soft delete user |
| DELETE | `/{id}/destroy` | Hard delete user |

### Documents (`/api/v1/documents`)
| Method | Endpoint | Description |
|--------|----------|-------------|
| GET    | `/` | List documents (paginated) |
| GET    | `/{id}` | Get document by ID |
| POST   | `/` | Upload single document |
| POST   | `/multiple` | Upload multiple documents |
| PUT    | `/{id}` | Update document |
| DELETE | `/` | Soft delete document |
| DELETE | `/{id}/destroy` | Hard delete document |

### OCR Data (`/api/v1/ocr-data`)
| Method | Endpoint | Description |
|--------|----------|-------------|
| GET    | `/` | List OCR data (paginated) |
| GET    | `/{id}` | Get OCR data by ID |
| PUT    | `/` | Update OCR data |
| DELETE | `/{id}/destroy` | Hard delete OCR data |

### Logs (`/api/v1/logs`)
| Method | Endpoint | Description |
|--------|----------|-------------|
| GET    | `/access` | List access logs (paginated) |
| GET    | `/access/{id}` | Get access log by ID |

---

## Support

For questions or issues, please contact the development team.
