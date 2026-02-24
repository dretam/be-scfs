# OCR Data API

Base URL: `/api/v1/ocr-data`

All endpoints require JWT Bearer token authentication.

> **Note:** OCR data is automatically extracted when uploading documents. This API provides read and management access to the extracted OCR data.

---

## Endpoints

### 1. List OCR Data

Get a paginated list of all OCR data.

**Endpoint:** `GET /api/v1/ocr-data`

**Authentication:** Required (Bearer Token)

**Query Parameters:**
| Parameter | Type   | Required | Default | Description                                      |
|-----------|--------|----------|---------|--------------------------------------------------|
| page      | int    | No       | 1       | Page number                                      |
| perPage   | int    | No       | 5       | Items per page                                   |
| filter    | string | No       | -       | Search filter (atasNama, nominal, periode, alokasi) |
| sort      | string | No       | -       | Sort field (audit.createdAt, atasNama, nominal, periode) |
| expands   | string | No       | -       | Related entities to load                         |

**Request Example:**
```http
GET /api/v1/ocr-data?page=1&perPage=5&filter=john&sort=audit.createdAt HTTP/1.1
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
    },
    {
      "id": 2,
      "documentId": 2,
      "atasNama": "Jane Smith",
      "nominal": "250000000",
      "jangkaWaktu": "24 bulan",
      "periode": "Februari 2024",
      "rate": "6.0%",
      "alokasi": "Investasi",
      "namaRekeningTujuanPencairan": "Jane Smith",
      "nomorRekeningTujuanPencairan": "5555666677",
      "nomorRekeningPengirim": "4444333322",
      "nomorRekeningPlacement": "9988776655",
      "createdAt": "2024-01-02T11:00:00Z",
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

### 2. Get OCR Data by ID

Get a single OCR data record by its ID.

**Endpoint:** `GET /api/v1/ocr-data/{id}`

**Authentication:** Required (Bearer Token)

**Path Parameters:**
| Parameter | Type | Required | Description     |
|-----------|------|----------|-----------------|
| id        | long | Yes      | OCR Data ID     |

**Query Parameters:**
| Parameter | Type   | Required | Description              |
|-----------|--------|----------|--------------------------|
| expands   | string | No       | Related entities to load |

**Request Example:**
```http
GET /api/v1/ocr-data/1 HTTP/1.1
Authorization: Bearer <access_token>
```

**Response Example:**
```json
{
  "status": 200,
  "message": "OK",
  "data": {
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
}
```

---

### 3. Update OCR Data

Update an existing OCR data record (e.g., to correct OCR extraction errors).

**Endpoint:** `PUT /api/v1/ocr-data`

**Authentication:** Required (Bearer Token)

**Request Body:**
```json
{
  "id": 1,
  "atasNama": "John D. Doe",
  "nominal": "150000000",
  "jangkaWaktu": "18 bulan",
  "periode": "Januari 2024 - Juni 2025",
  "rate": "5.75%",
  "alokasi": "Deposito Berjangka",
  "namaRekeningTujuanPencairan": "John Doe",
  "nomorRekeningTujuanPencairan": "1234567890",
  "nomorRekeningPengirim": "0987654321",
  "nomorRekeningPlacement": "1122334455"
}
```

**Response Example:**
```json
{
  "status": 200,
  "message": "OK",
  "data": {
    "id": 1,
    "documentId": 1,
    "atasNama": "John D. Doe",
    "nominal": "150000000",
    "jangkaWaktu": "18 bulan",
    "periode": "Januari 2024 - Juni 2025",
    "rate": "5.75%",
    "alokasi": "Deposito Berjangka",
    "namaRekeningTujuanPencairan": "John Doe",
    "nomorRekeningTujuanPencairan": "1234567890",
    "nomorRekeningPengirim": "0987654321",
    "nomorRekeningPlacement": "1122334455",
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

### 4. Hard Delete OCR Data

Permanently delete an OCR data record from the database.

**Endpoint:** `DELETE /api/v1/ocr-data/{id}/destroy`

**Authentication:** Required (Bearer Token)

**Path Parameters:**
| Parameter | Type | Required | Description     |
|-----------|------|----------|-----------------|
| id        | long | Yes      | OCR Data ID     |

**Request Example:**
```http
DELETE /api/v1/ocr-data/1/destroy HTTP/1.1
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

## OCRResponse Schema

| Field                              | Type    | Description                                      |
|------------------------------------|---------|--------------------------------------------------|
| id                                 | long    | OCR data unique identifier                       |
| documentId                         | long    | Associated document ID (one-to-one relationship) |
| atasNama                           | string  | Account holder name                              |
| nominal                            | string  | Nominal amount                                   |
| jangkaWaktu                        | string  | Duration/term (e.g., "12 bulan")                 |
| periode                            | string  | Period (e.g., "Januari 2024")                    |
| rate                               | string  | Interest rate (e.g., "5.5%")                     |
| alokasi                            | string  | Allocation purpose                               |
| namaRekeningTujuanPencairan        | string  | Target account name for disbursement             |
| nomorRekeningTujuanPencairan       | string  | Target account number for disbursement           |
| nomorRekeningPengirim              | string  | Sender account number                            |
| nomorRekeningPlacement             | string  | Placement account number                         |
| createdAt                          | datetime| Creation timestamp                               |
| createdBy                          | long    | User ID who created                              |
| updatedAt                          | datetime| Last update timestamp                            |
| updatedBy                          | long    | User ID who last updated                         |
| deletedAt                          | datetime| Soft delete timestamp                            |
| deletedBy                          | long    | User ID who soft deleted                         |

---

## Field Descriptions

| Field                              | Description                                           | Example                    |
|------------------------------------|-------------------------------------------------------|----------------------------|
| atasNama                           | Name of the account holder                          | "John Doe"                 |
| nominal                            | Amount in IDR (Indonesian Rupiah)                    | "100000000"                |
| jangkaWaktu                        | Investment duration                                  | "12 bulan"                 |
| periode                            | Investment period                                    | "Januari 2024"             |
| rate                               | Interest rate per annum                              | "5.5%"                     |
| alokasi                            | Fund allocation purpose                              | "Deposito"                 |
| namaRekeningTujuanPencairan        | Name on the target disbursement account             | "John Doe"                 |
| nomorRekeningTujuanPencairan       | Target disbursement account number                   | "1234567890"               |
| nomorRekeningPengirim              | Sender's account number                              | "0987654321"               |
| nomorRekeningPlacement             | Placement account number                             | "1122334455"               |

---

## Relationship with Documents

OCR data has a **one-to-one relationship** with documents:
- Each document can have one or more OCR data records
- Each OCR data record belongs to exactly one document (via `documentId`)
- When a document is uploaded, OCR processing automatically creates associated OCR data records

---

## Error Responses

### 400 Bad Request
```json
{
  "status": 400,
  "message": "Bad Request",
  "errors": ["id is required"]
}
```

### 404 Not Found
```json
{
  "status": 404,
  "message": "Not Found",
  "errors": ["OCR Data not found with id: 999"]
}
```

### 422 Unprocessable Entity
```json
{
  "status": 422,
  "message": "Unprocessable Entity",
  "errors": ["Invalid OCR data format"]
}
```
