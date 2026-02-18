# Documents API

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

### List Documents
```
GET /api/v1/documents
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
      "filename": "document.pdf",
      "originalName": "My Document.pdf",
      "filePath": "documents/2023/10/document.pdf",
      "fileSize": 102400,
      "mimeType": "application/pdf",
      "uploadedBy": 1,
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

### Get Document by ID
```
GET /api/v1/documents/{id}
```

#### Path Parameters
- `id`: Document ID

#### Query Parameters
- `expands`: Expand relations

#### Response
```json
{
  "status": 200,
  "message": "OK",
  "data": {
    "id": 1,
    "filename": "document.pdf",
    "originalName": "My Document.pdf",
    "filePath": "documents/2023/10/document.pdf",
    "fileSize": 102400,
    "mimeType": "application/pdf",
    "uploadedBy": 1,
    "createdAt": "2023-10-15T10:30:00Z",
    "createdBy": 1,
    "updatedAt": "2023-10-15T11:45:00Z",
    "updatedBy": 1,
    "deletedAt": null,
    "deletedBy": null
  }
}
```

### Create Document
```
POST /api/v1/documents
```

#### Request Body
```json
{
  "filename": "new-document.pdf",
  "originalName": "New Document.pdf",
  "filePath": "documents/2023/10/new-document.pdf",
  "fileSize": 204800,
  "mimeType": "application/pdf",
  "uploadedBy": 1
}
```

#### Response
```json
{
  "status": 200,
  "message": "OK",
  "data": {
    "id": 2,
    "filename": "new-document.pdf",
    "originalName": "New Document.pdf",
    "filePath": "documents/2023/10/new-document.pdf",
    "fileSize": 204800,
    "mimeType": "application/pdf",
    "uploadedBy": 1,
    "createdAt": "2023-10-16T09:15:00Z",
    "createdBy": 1,
    "updatedAt": null,
    "updatedBy": null,
    "deletedAt": null,
    "deletedBy": null
  }
}
```

### Update Document
```
PUT /api/v1/documents
```

#### Request Body
```json
{
  "id": 1,
  "filename": "updated-document.pdf",
  "originalName": "Updated Document.pdf",
  "filePath": "documents/2023/10/updated-document.pdf",
  "fileSize": 307200,
  "mimeType": "application/pdf",
  "uploadedBy": 1
}
```

#### Response
```json
{
  "status": 200,
  "message": "OK",
  "data": {
    "id": 1,
    "filename": "updated-document.pdf",
    "originalName": "Updated Document.pdf",
    "filePath": "documents/2023/10/updated-document.pdf",
    "fileSize": 307200,
    "mimeType": "application/pdf",
    "uploadedBy": 1,
    "createdAt": "2023-10-15T10:30:00Z",
    "createdBy": 1,
    "updatedAt": "2023-10-16T10:20:00Z",
    "updatedBy": 1,
    "deletedAt": null,
    "deletedBy": null
  }
}
```

### Soft Delete Document
```
DELETE /api/v1/documents
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
    "filename": "document.pdf",
    "originalName": "My Document.pdf",
    "filePath": "documents/2023/10/document.pdf",
    "fileSize": 102400,
    "mimeType": "application/pdf",
    "uploadedBy": 1,
    "createdAt": "2023-10-15T10:30:00Z",
    "createdBy": 1,
    "updatedAt": "2023-10-16T10:20:00Z",
    "updatedBy": 1,
    "deletedAt": "2023-10-16T10:25:00Z",
    "deletedBy": 1
  }
}
```

### Hard Delete Document
```
DELETE /api/v1/documents/{id}/destroy
```

#### Path Parameters
- `id`: Document ID

#### Response
```json
{
  "status": 200,
  "message": "OK",
  "id": 1
}
```

### Upload Document
```
POST /api/v1/documents/upload
```

#### Request Parameters
- `file`: File to upload (multipart/form-data)
- `uploadedBy`: ID of user uploading (optional, defaults to authenticated user)

#### Response
```json
{
  "status": 200,
  "message": "OK",
  "data": {
    "id": 3,
    "filename": "uploaded-file.pdf",
    "originalName": "My Uploaded File.pdf",
    "filePath": "documents/2023/10/uploaded-file.pdf",
    "fileSize": 153600,
    "mimeType": "application/pdf",
    "success": true,
    "message": "File uploaded successfully"
  }
}
```