# Documents API

Base URL: `/api/v1/documents`

All endpoints require JWT Bearer token authentication.

---

## Endpoints

### 1. List Documents

Get a paginated list of all documents.

**Endpoint:** `GET /api/v1/documents`

**Authentication:** Required (Bearer Token)

**Query Parameters:**
| Parameter | Type   | Required | Default | Description                              |
|-----------|--------|----------|---------|------------------------------------------|
| page      | int    | No       | 1       | Page number                              |
| perPage   | int    | No       | 5       | Items per page                           |
| filter    | string | No       | -       | Search filter (filename, originalName)   |
| sort      | string | No       | -       | Sort field (audit.createdAt, filename)   |
| expands   | string | No       | -       | Related entities to load                 |

**Request Example:**
```http
GET /api/v1/documents?page=1&perPage=5&filter=invoice&sort=audit.createdAt HTTP/1.1
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
      "filename": "invoice_20240101_abc123.pdf",
      "originalName": "invoice.pdf",
      "filePath": "documents/2024/01/invoice_20240101_abc123.pdf",
      "fileSize": 1024567,
      "mimeType": "application/pdf",
      "uploadedBy": 1,
      "userId": 1,
      "createdAt": "2024-01-01T10:00:00Z",
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

### 2. Get Document by ID

Get a single document by its ID.

**Endpoint:** `GET /api/v1/documents/{id}`

**Authentication:** Required (Bearer Token)

**Path Parameters:**
| Parameter | Type | Required | Description  |
|-----------|------|----------|--------------|
| id        | long | Yes      | Document ID  |

**Query Parameters:**
| Parameter | Type   | Required | Description              |
|-----------|--------|----------|--------------------------|
| expands   | string | No       | Related entities to load |

**Request Example:**
```http
GET /api/v1/documents/1 HTTP/1.1
Authorization: Bearer <access_token>
```

**Response Example:**
```json
{
  "status": 200,
  "message": "OK",
  "data": {
    "id": 1,
    "filename": "invoice_20240101_abc123.pdf",
    "originalName": "invoice.pdf",
    "filePath": "documents/2024/01/invoice_20240101_abc123.pdf",
    "fileSize": 1024567,
    "mimeType": "application/pdf",
    "uploadedBy": 1,
    "userId": 1,
    "createdAt": "2024-01-01T10:00:00Z",
    "createdBy": 1,
    "updatedAt": null,
    "updatedBy": null,
    "deletedAt": null,
    "deletedBy": null
  }
}
```

---

### 3. Upload Document (Single)

Upload a single document. The document will be processed with OCR to extract data.

**Endpoint:** `POST /api/v1/documents`

**Authentication:** Required (Bearer Token)

**Content-Type:** `multipart/form-data`

**Form Parameters:**
| Parameter | Type | Required | Description     |
|-----------|------|----------|-----------------|
| file      | file | Yes      | Document file   |

**Request Example:**
```http
POST /api/v1/documents HTTP/1.1
Authorization: Bearer <access_token>
Content-Type: multipart/form-data

--boundary
Content-Disposition: form-data; name="file"; filename="invoice.pdf"
Content-Type: application/pdf

<binary file content>
--boundary--
```

**Response Example:**
```json
{
  "status": 200,
  "message": "OK",
  "data": [
    {
      "id": 1,
      "documentId": 1,
      "atasNama": "John Doe",
      "nominal": "100000000",
      "jangkaWaktu": "12 bulan",
      "periode": "Januari 2024",
      "rate": "5.5%",
      "alokasi": "Deposito",
      "namaRekeningTujuanPencairan": "John Doe",
      "nomorRekeningTujuanPencairan": "1234567890",
      "nomorRekeningPengirim": "0987654321",
      "nomorRekeningPlacement": "1122334455",
      "createdAt": "2024-01-01T10:00:00Z",
      "createdBy": 1,
      "updatedAt": null,
      "updatedBy": null,
      "deletedAt": null,
      "deletedBy": null
    }
  ]
}
```

---

### 4. Upload Documents (Multiple)

Upload multiple documents at once.

**Endpoint:** `POST /api/v1/documents/multiple`

**Authentication:** Required (Bearer Token)

**Content-Type:** `multipart/form-data`

**Form Parameters:**
| Parameter | Type       | Required | Description        |
|-----------|------------|----------|--------------------|
| files     | file[]     | Yes      | Array of files   |

**Request Example:**
```http
POST /api/v1/documents/multiple HTTP/1.1
Authorization: Bearer <access_token>
Content-Type: multipart/form-data

--boundary
Content-Disposition: form-data; name="files"; filename="invoice1.pdf"
Content-Type: application/pdf

<binary file content 1>
--boundary
Content-Disposition: form-data; name="files"; filename="invoice2.pdf"
Content-Type: application/pdf

<binary file content 2>
--boundary--
```

**Response Example:**
```json
{
  "status": 200,
  "message": "OK",
  "data": [
    {
      "id": 1,
      "filename": "invoice1_20240101_abc.pdf",
      "originalName": "invoice1.pdf",
      "filePath": "documents/2024/01/invoice1_20240101_abc.pdf",
      "fileSize": 1024567,
      "mimeType": "application/pdf",
      "uploadedBy": 1,
      "userId": 1,
      "createdAt": "2024-01-01T10:00:00Z",
      "createdBy": 1,
      "updatedAt": null,
      "updatedBy": null,
      "deletedAt": null,
      "deletedBy": null
    },
    {
      "id": 2,
      "filename": "invoice2_20240101_def.pdf",
      "originalName": "invoice2.pdf",
      "filePath": "documents/2024/01/invoice2_20240101_def.pdf",
      "fileSize": 2048901,
      "mimeType": "application/pdf",
      "uploadedBy": 1,
      "userId": 1,
      "createdAt": "2024-01-01T10:01:00Z",
      "createdBy": 1,
      "updatedAt": null,
      "updatedBy": null,
      "deletedAt": null,
      "deletedBy": null
    }
  ]
}
```

---

### 5. Update Document

Update an existing document by uploading a new file.

**Endpoint:** `PUT /api/v1/documents/{id}`

**Authentication:** Required (Bearer Token)

**Content-Type:** `multipart/form-data`

**Path Parameters:**
| Parameter | Type | Required | Description  |
|-----------|------|----------|--------------|
| id        | long | Yes      | Document ID  |

**Form Parameters:**
| Parameter | Type | Required | Description     |
|-----------|------|----------|-----------------|
| file      | file | Yes      | New document file |

**Request Example:**
```http
PUT /api/v1/documents/1 HTTP/1.1
Authorization: Bearer <access_token>
Content-Type: multipart/form-data

--boundary
Content-Disposition: form-data; name="file"; filename="updated_invoice.pdf"
Content-Type: application/pdf

<binary file content>
--boundary--
```

**Response Example:**
```json
{
  "status": 200,
  "message": "OK",
  "data": {
    "id": 1,
    "filename": "updated_invoice_20240102_xyz.pdf",
    "originalName": "updated_invoice.pdf",
    "filePath": "documents/2024/01/updated_invoice_20240102_xyz.pdf",
    "fileSize": 1536789,
    "mimeType": "application/pdf",
    "uploadedBy": 1,
    "userId": 1,
    "createdAt": "2024-01-01T10:00:00Z",
    "createdBy": 1,
    "updatedAt": "2024-01-02T15:00:00Z",
    "updatedBy": 1,
    "deletedAt": null,
    "deletedBy": null
  }
}
```

---

### 6. Soft Delete Document

Soft delete a document (marks as deleted but keeps in database).

**Endpoint:** `DELETE /api/v1/documents`

**Authentication:** Required (Bearer Token)

**Request Body:**
```json
{
  "id": 1
}
```

**Response Example:**
```json
{
  "status": 200,
  "message": "OK",
  "data": {
    "id": 1,
    "filename": "invoice_20240101_abc123.pdf",
    "originalName": "invoice.pdf",
    "filePath": "documents/2024/01/invoice_20240101_abc123.pdf",
    "fileSize": 1024567,
    "mimeType": "application/pdf",
    "uploadedBy": 1,
    "userId": 1,
    "createdAt": "2024-01-01T10:00:00Z",
    "createdBy": 1,
    "updatedAt": "2024-01-02T00:00:00Z",
    "updatedBy": 1,
    "deletedAt": "2024-01-02T00:00:00Z",
    "deletedBy": 1
  }
}
```

---

### 7. Hard Delete Document

Permanently delete a document from the database.

**Endpoint:** `DELETE /api/v1/documents/{id}/destroy`

**Authentication:** Required (Bearer Token)

**Path Parameters:**
| Parameter | Type | Required | Description  |
|-----------|------|----------|--------------|
| id        | long | Yes      | Document ID  |

**Request Example:**
```http
DELETE /api/v1/documents/1/destroy HTTP/1.1
Authorization: Bearer <access_token>
```

**Response Example:**
```json
{
  "status": 200,
  "message": "OK",
  "id": 1
}
```

---

## DocumentResponse Schema

| Field          | Type    | Description                           |
|----------------|---------|---------------------------------------|
| id             | long    | Document unique identifier            |
| filename       | string  | Stored filename (unique)              |
| originalName   | string  | Original uploaded filename            |
| filePath       | string  | Full path in storage                  |
| fileSize       | long    | File size in bytes                    |
| mimeType       | string  | MIME type of the file                 |
| uploadedBy     | long    | User ID who uploaded                  |
| userId         | long    | Associated user ID                    |
| createdAt      | datetime| Creation timestamp                    |
| createdBy      | long    | User ID who created                   |
| updatedAt      | datetime| Last update timestamp                 |
| updatedBy      | long    | User ID who last updated              |
| deletedAt      | datetime| Soft delete timestamp                 |
| deletedBy      | long    | User ID who soft deleted              |

---

## OCRResponse Schema

When uploading a document, OCR processing extracts the following data:

| Field                              | Type    | Description                           |
|------------------------------------|---------|---------------------------------------|
| id                                 | long    | OCR data unique identifier            |
| documentId                         | long    | Associated document ID                |
| atasNama                           | string  | Account holder name                   |
| nominal                            | string  | Nominal amount                        |
| jangkaWaktu                        | string  | Duration/term                         |
| periode                            | string  | Period                                |
| rate                               | string  | Interest rate                         |
| alokasi                            | string  | Allocation                            |
| namaRekeningTujuanPencairan        | string  | Target account name for disbursement  |
| nomorRekeningTujuanPencairan       | string  | Target account number for disbursement|
| nomorRekeningPengirim              | string  | Sender account number                 |
| nomorRekeningPlacement             | string  | Placement account number              |
| createdAt                          | datetime| Creation timestamp                    |
| createdBy                          | long    | User ID who created                   |
| updatedAt                          | datetime| Last update timestamp                 |
| updatedBy                          | long    | User ID who last updated              |
| deletedAt                          | datetime| Soft delete timestamp                 |
| deletedBy                          | long    | User ID who soft deleted              |

---

## Supported File Types

| Type       | Extensions          | Max Size |
|------------|---------------------|----------|
| PDF        | .pdf                | 10 MB    |
| Images     | .jpg, .jpeg, .png   | 5 MB     |

---

## Error Responses

### 400 Bad Request
```json
{
  "status": 400,
  "message": "Bad Request",
  "errors": ["File is required"]
}
```

### 404 Not Found
```json
{
  "status": 404,
  "message": "Not Found",
  "errors": ["Document not found with id: 999"]
}
```

### 409 Conflict
```json
{
  "status": 409,
  "message": "Conflict",
  "errors": ["Document with filename 'invoice.pdf' already exists"]
}
```

### 413 Payload Too Large
```json
{
  "status": 413,
  "message": "Payload Too Large",
  "errors": ["File size exceeds maximum limit of 10MB"]
}
```
