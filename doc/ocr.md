# OCR Data API Documentation

## Overview

The OCR Data API provides endpoints to manage OCR (Optical Character Recognition) data extracted from uploaded documents. This API allows you to retrieve, update, approve, reject, and delete OCR data records.

**Base URL:** `/api/v1/ocr-data`

**Authentication:** All endpoints require JWT Bearer token authentication.

---

## Table of Contents

1. [Get List of OCR Data](#get-list-of-ocr-data)
2. [Get OCR Data by ID](#get-ocr-data-by-id)
3. [Update OCR Data](#update-ocr-data)
4. [Bulk Approve OCR Data](#bulk-approve-ocr-data)
5. [Bulk Reject OCR Data](#bulk-reject-ocr-data)
6. [Delete OCR Data](#delete-ocr-data)
7. [Error Responses](#error-responses)

---

## Get List of OCR Data

Retrieve a paginated list of OCR data records with optional filtering and sorting.

### Endpoint

```
GET /api/v1/ocr-data
```

### Query Parameters

| Parameter | Type   | Required | Default | Description                              |
|-----------|--------|----------|---------|------------------------------------------|
| page      | int    | No       | 1       | Page number (1-indexed)                  |
| perPage   | int    | No       | 5       | Number of items per page                 |
| filter    | string | No       | -       | Filter query string                      |
| sort      | string | No       | -       | Sort field (prefix with `-` for DESC)    |
| expands   | string | No       | -       | Comma-separated list of relations to expand |

### Sortable Fields

- `id`
- `atasNama`
- `nominal`
- `status`
- `audit.createdAt`

### Example Request

```bash
curl -X GET "http://localhost:8080/api/v1/ocr-data?page=1&perPage=10&sort=-audit.createdAt&expands=document" \
  -H "Authorization: Bearer <your-token>"
```

### Success Response (200 OK)

```json
{
  "status": 200,
  "message": "OK",
  "data": [
    {
      "id": 1,
      "document": {
        "id": 1,
        "filename": "ocr_result_123.json",
        "originalName": "document.pdf",
        "filePath": "/uploads/2024/01/ocr_result_123.json",
        "fileSize": 2048,
        "mimeType": "application/json",
        "uploadedBy": 1,
        "userId": 1,
        "createdAt": "2024-01-15T10:30:00Z",
        "createdBy": 1,
        "updatedAt": null,
        "updatedBy": null,
        "deletedAt": null,
        "deletedBy": null
      },
      "atasNama": "John Doe",
      "nominal": "100000000",
      "jangkaWaktu": "12",
      "periode": "Bulanan",
      "rate": "5.5",
      "alokasi": "Investasi",
      "namaRekeningTujuanPencairan": "John Doe",
      "nomorRekeningTujuanPencairan": "1234567890",
      "nomorRekeningPengirim": "0987654321",
      "nomorRekeningPlacement": "1122334455",
      "status": "PENDING",
      "createdAt": "2024-01-15T10:30:00Z",
      "createdBy": 1,
      "updatedAt": null,
      "updatedBy": null,
      "deletedAt": null,
      "deletedBy": null
    }
  ],
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

---

## Get OCR Data by ID

Retrieve a single OCR data record by its ID.

### Endpoint

```
GET /api/v1/ocr-data/{id}
```

### Path Parameters

| Parameter | Type | Required | Description        |
|-----------|------|----------|--------------------|
| id        | Long | Yes      | OCR Data ID        |

### Query Parameters

| Parameter | Type   | Required | Description                              |
|-----------|--------|----------|------------------------------------------|
| expands   | string | No       | Comma-separated list of relations to expand |

### Example Request

```bash
curl -X GET "http://localhost:8080/api/v1/ocr-data/1?expands=document" \
  -H "Authorization: Bearer <your-token>"
```

### Success Response (200 OK)

```json
{
  "status": 200,
  "message": "OK",
  "data": {
    "id": 1,
    "document": {
      "id": 1,
      "filename": "ocr_result_123.json",
      "originalName": "document.pdf",
      "filePath": "/uploads/2024/01/ocr_result_123.json",
      "fileSize": 2048,
      "mimeType": "application/json",
      "uploadedBy": 1,
      "userId": 1,
      "createdAt": "2024-01-15T10:30:00Z",
      "createdBy": 1,
      "updatedAt": null,
      "updatedBy": null,
      "deletedAt": null,
      "deletedBy": null
    },
    "atasNama": "John Doe",
    "nominal": "100000000",
    "jangkaWaktu": "12",
    "periode": "Bulanan",
    "rate": "5.5",
    "alokasi": "Investasi",
    "namaRekeningTujuanPencairan": "John Doe",
    "nomorRekeningTujuanPencairan": "1234567890",
    "nomorRekeningPengirim": "0987654321",
    "nomorRekeningPlacement": "1122334455",
    "status": "PENDING",
    "createdAt": "2024-01-15T10:30:00Z",
    "createdBy": 1,
    "updatedAt": null,
    "updatedBy": null,
    "deletedAt": null,
    "deletedBy": null
  }
}
```

---

## Update OCR Data

Update an existing OCR data record.

### Endpoint

```
PUT /api/v1/ocr-data
```

### Request Body

```json
{
  "id": 1,
  "atasNama": "Jane Doe",
  "nominal": "150000000",
  "jangkaWaktu": "24",
  "periode": "Bulanan",
  "rate": "6.0",
  "alokasi": "Deposito",
  "namaRekeningTujuanPencairan": "Jane Doe",
  "nomorRekeningTujuanPencairan": "9876543210",
  "nomorRekeningPengirim": "1234567890",
  "nomorRekeningPlacement": "5544332211"
}
```

| Field                              | Type   | Required | Description                           |
|------------------------------------|--------|----------|---------------------------------------|
| id                                 | Long   | Yes      | OCR Data ID (must exist)              |
| atasNama                           | String | No       | Account holder name                   |
| nominal                            | String | No       | Nominal amount                        |
| jangkaWaktu                        | String | No       | Duration/term                         |
| periode                            | String | No       | Period                                |
| rate                               | String | No       | Interest rate                         |
| alokasi                            | String | No       | Allocation                            |
| namaRekeningTujuanPencairan        | String | No       | Target account name for disbursement  |
| nomorRekeningTujuanPencairan       | String | No       | Target account number for disbursement|
| nomorRekeningPengirim              | String | No       | Sender account number                 |
| nomorRekeningPlacement             | String | No       | Placement account number              |

### Example Request

```bash
curl -X PUT "http://localhost:8080/api/v1/ocr-data" \
  -H "Authorization: Bearer <your-token>" \
  -H "Content-Type: application/json" \
  -d '{
    "id": 1,
    "atasNama": "Jane Doe",
    "nominal": "150000000"
  }'
```

### Success Response (200 OK)

```json
{
  "status": 200,
  "message": "OK",
  "data": {
    "id": 1,
    "document": {...},
    "atasNama": "Jane Doe",
    "nominal": "150000000",
    "status": "PENDING",
    "createdAt": "2024-01-15T10:30:00Z",
    "createdBy": 1,
    "updatedAt": "2024-01-15T11:00:00Z",
    "updatedBy": 1,
    "deletedAt": null,
    "deletedBy": null
  }
}
```

---

## Bulk Approve OCR Data

Approve multiple OCR data records at once. Changes the status to `APPROVED`.

### Endpoint

```
POST /api/v1/ocr-data/approve
```

### Request Body

```json
{
  "ids": [1, 2, 3, 4, 5]
}
```

| Parameter | Type       | Required | Description                          |
|-----------|------------|----------|--------------------------------------|
| ids       | List<Long> | Yes      | List of OCR Data IDs to approve (cannot be empty) |

### Example Request

```bash
curl -X POST "http://localhost:8080/api/v1/ocr-data/approve" \
  -H "Authorization: Bearer <your-token>" \
  -H "Content-Type: application/json" \
  -d '{
    "ids": [1, 2, 3]
  }'
```

### Success Response (200 OK)

```json
{
  "status": 200,
  "message": "OK",
  "data": [
    {
      "id": 1,
      "document": {...},
      "atasNama": "John Doe",
      "nominal": "100000000",
      "status": "APPROVED",
      "createdAt": "2024-01-15T10:30:00Z",
      "createdBy": 1,
      "updatedAt": "2024-01-15T12:00:00Z",
      "updatedBy": 1,
      "deletedAt": null,
      "deletedBy": null
    },
    {
      "id": 2,
      "document": {...},
      "atasNama": "Jane Smith",
      "nominal": "200000000",
      "status": "APPROVED",
      "createdAt": "2024-01-15T10:35:00Z",
      "createdBy": 1,
      "updatedAt": "2024-01-15T12:00:00Z",
      "updatedBy": 1,
      "deletedAt": null,
      "deletedBy": null
    },
    {
      "id": 3,
      "document": {...},
      "atasNama": "Bob Wilson",
      "nominal": "150000000",
      "status": "APPROVED",
      "createdAt": "2024-01-15T10:40:00Z",
      "createdBy": 1,
      "updatedAt": "2024-01-15T12:00:00Z",
      "updatedBy": 1,
      "deletedAt": null,
      "deletedBy": null
    }
  ]
}
```

---

## Bulk Reject OCR Data

Reject multiple OCR data records at once. Changes the status to `REJECTED`.

### Endpoint

```
POST /api/v1/ocr-data/reject
```

### Request Body

```json
{
  "ids": [1, 2, 3, 4, 5]
}
```

| Parameter | Type       | Required | Description                          |
|-----------|------------|----------|--------------------------------------|
| ids       | List<Long> | Yes      | List of OCR Data IDs to reject (cannot be empty) |

### Example Request

```bash
curl -X POST "http://localhost:8080/api/v1/ocr-data/reject" \
  -H "Authorization: Bearer <your-token>" \
  -H "Content-Type: application/json" \
  -d '{
    "ids": [1, 2, 3]
  }'
```

### Success Response (200 OK)

```json
{
  "status": 200,
  "message": "OK",
  "data": [
    {
      "id": 1,
      "document": {...},
      "atasNama": "John Doe",
      "nominal": "100000000",
      "status": "REJECTED",
      "createdAt": "2024-01-15T10:30:00Z",
      "createdBy": 1,
      "updatedAt": "2024-01-15T12:00:00Z",
      "updatedBy": 1,
      "deletedAt": null,
      "deletedBy": null
    },
    {
      "id": 2,
      "document": {...},
      "atasNama": "Jane Smith",
      "nominal": "200000000",
      "status": "REJECTED",
      "createdAt": "2024-01-15T10:35:00Z",
      "createdBy": 1,
      "updatedAt": "2024-01-15T12:00:00Z",
      "updatedBy": 1,
      "deletedAt": null,
      "deletedBy": null
    },
    {
      "id": 3,
      "document": {...},
      "atasNama": "Bob Wilson",
      "nominal": "150000000",
      "status": "REJECTED",
      "createdAt": "2024-01-15T10:40:00Z",
      "createdBy": 1,
      "updatedAt": "2024-01-15T12:00:00Z",
      "updatedBy": 1,
      "deletedAt": null,
      "deletedBy": null
    }
  ]
}
```

---

## Delete OCR Data

Permanently delete an OCR data record by its ID.

### Endpoint

```
DELETE /api/v1/ocr-data/{id}/destroy
```

### Path Parameters

| Parameter | Type | Required | Description        |
|-----------|------|----------|--------------------|
| id        | Long | Yes      | OCR Data ID        |

### Example Request

```bash
curl -X DELETE "http://localhost:8080/api/v1/ocr-data/1/destroy" \
  -H "Authorization: Bearer <your-token>"
```

### Success Response (200 OK)

```json
{
  "status": 200,
  "message": "OK",
  "id": 1
}
```

---

## Error Responses

### 400 Bad Request

Returned when the request contains invalid data or missing required fields.

```json
{
  "status": 400,
  "message": "Bad Request",
  "errors": [
    {
      "field": "ids",
      "message": "ids cannot be empty"
    }
  ]
}
```

**Common Causes:**
- Empty `ids` array in approve/reject requests
- Missing required `id` in update request
- Invalid data format

---

### 401 Unauthorized

Returned when the authentication token is missing or invalid.

```json
{
  "status": 401,
  "message": "Unauthorized",
  "error": "Invalid or missing authentication token"
}
```

**Common Causes:**
- Missing `Authorization` header
- Expired JWT token
- Invalid token format

---

### 403 Forbidden

Returned when the authenticated user doesn't have permission to perform the action.

```json
{
  "status": 403,
  "message": "Forbidden",
  "error": "Access denied"
}
```

**Common Causes:**
- Insufficient user role/permissions
- Resource belongs to another user

---

### 404 Not Found

Returned when the requested OCR data record doesn't exist.

```json
{
  "status": 404,
  "message": "Not Found",
  "error": "OCR Data not found with id: 999"
}
```

**Common Causes:**
- Invalid OCR Data ID
- Record has been soft-deleted or permanently deleted

---

### 429 Too Many Requests

Returned when the rate limit is exceeded (default: 50 requests/second).

```json
{
  "status": 429,
  "message": "Too Many Requests",
  "error": "Rate limit exceeded. Please try again later."
}
```

**Common Causes:**
- Exceeding 50 requests per second
- Bulk operations with very large ID lists

---

### 500 Internal Server Error

Returned when an unexpected error occurs on the server.

```json
{
  "status": 500,
  "message": "Internal Server Error",
  "error": "An unexpected error occurred"
}
```

**Common Causes:**
- Database connection issues
- Unexpected exceptions in business logic

---

## OCR Status Values

| Status     | Description                                    |
|------------|------------------------------------------------|
| PENDING    | OCR data is awaiting review/approval           |
| APPROVED   | OCR data has been approved for processing      |
| REJECTED   | OCR data has been rejected and needs correction|

---

## Best Practices

1. **Bulk Operations**: Use bulk approve/reject endpoints for processing multiple records efficiently instead of making individual API calls.

2. **Pagination**: Always use pagination when retrieving lists to avoid performance issues with large datasets.

3. **Error Handling**: Implement proper error handling for all HTTP status codes, especially 401, 403, 404, and 429.

4. **Rate Limiting**: Be mindful of the rate limit (50 req/s). For bulk operations, consider batching large ID lists.

5. **Soft Delete**: The API uses soft delete by default. Deleted records can be filtered out using `audit.deletedAt` field.

6. **Audit Trail**: All changes are tracked with `createdBy`, `updatedBy`, `createdAt`, and `updatedAt` fields for audit purposes.
