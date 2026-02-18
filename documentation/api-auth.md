# Authentication API

## Templates

### Standard Response Format
- `status`: HTTP status code
- `message`: Human-readable status message
- `data`: Response payload object/array

### Login Response Format
- `token`: JWT access token
- `refreshToken`: Refresh token for renewing access
- `expiresIn`: Token expiration time in seconds
- `user`: User information object

### User Information Object
- `id`: User ID
- `name`: User's full name
- `email`: User's email address
- `role`: User's role object

### Role Object
- `id`: Role ID
- `name`: Role name
- `icon`: Role icon (nullable)
- `description`: Role description (nullable)

## Endpoints

### Login
```
POST /api/v1/auth/login
```

#### Request Body
```json
{
  "email": "user@example.com",
  "password": "userPassword123"
}
```

#### Response
```json
{
  "status": 200,
  "message": "OK",
  "data": {
    "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
    "refreshToken": "def50200...",
    "expiresIn": 3600,
    "user": {
      "id": 1,
      "name": "John Doe",
      "email": "user@example.com",
      "role": {
        "id": 1,
        "name": "Administrator",
        "icon": "admin-icon",
        "description": "System Administrator"
      }
    }
  }
}
```

### Refresh Token
```
POST /api/v1/auth/refresh
```

#### Request Body
```json
{
  "refreshToken": "def50200..."
}
```

#### Response
```json
{
  "status": 200,
  "message": "OK",
  "data": {
    "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
    "refreshToken": "def50200...",
    "expiresIn": 3600
  }
}
```

### Logout
```
POST /api/v1/auth/logout
```

#### Headers
- `Authorization`: Bearer {token}

#### Response
```json
{
  "status": 200,
  "message": "OK",
  "data": null
}
```

### Change Password
```
PUT /api/v1/auth/change-password
```

#### Headers
- `Authorization`: Bearer {token}

#### Request Body
```json
{
  "currentPassword": "oldPassword123",
  "newPassword": "newPassword123"
}
```

#### Response
```json
{
  "status": 200,
  "message": "OK",
  "data": {
    "id": 1,
    "name": "John Doe",
    "email": "user@example.com",
    "role": {
      "id": 1,
      "name": "Administrator",
      "icon": "admin-icon",
      "description": "System Administrator"
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