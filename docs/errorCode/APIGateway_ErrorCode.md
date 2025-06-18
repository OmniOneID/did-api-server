---
puppeteer:
pdf:
format: A4
displayHeaderFooter: true
landscape: false
scale: 0.8
margin:
top: 1.2cm
right: 1cm
bottom: 1cm
left: 1cm
image:
quality: 100
fullPage: false
---


# API Gateway Server Error

- Date: 2025-05-30
- Version: v2.0.0

| Version | Date       | Changes                      |
|---------|------------|------------------------------|
| v2.0.0  | 2025-05-30 | Added ZKP-related error codes |
| v1.0.0  | 2024-09-03 | Initial version              |

<div style="page-break-after: always;"></div>

# Table of Contents
- [Model](#model)
  - [Error Response](#error-response)
- [Error Code](#error-code)
  - [1. Encoding Errors (001xx)](#1-encoding-errors-001xx)
  - [2. Document Retrieval Errors (002xx)](#2-document-retrieval-errors-002xx)
  - [3. DID Related Errors (003xx)](#3-did-related-errors-003xx)
  - [4. VC Related Errors (004xx)](#4-vc-related-errors-004xx)
  - [5. ZKP Related Errors (005xx)](#5-zkp-related-errors-005xx)

# Model

## Error Response

### Description
```
Error struct for API Gateway. It has code and message pair.
Code starts with SSRVAGW.
```

### Declaration
```java
public class ErrorResponse {
    private final String code;
    private final String description;
}
```

### Property

| Name        | Type   | Description         | **M/O** | **Note** |
|-------------|--------|---------------------|---------|----------|
| code        | String | Error code          | M       |          |
| message     | String | Error description   | M       |          |
| httpStatus  | int    | HTTP status code    | M       |          |

# Error Code

## 1. Encoding Errors (001xx)

| Error Code       | Error Message              | Description | Action Required                        | HTTP Status |
|------------------|----------------------------|-------------|----------------------------------------|-------------|
| SSRVAGW00100     | Failed to encode data.     | -           | Check encoding process and input data. | 500         |

## 2. Document Retrieval Errors (002xx)

| Error Code       | Error Message                                 | Description | Action Required                                | HTTP Status |
|------------------|-----------------------------------------------|-------------|------------------------------------------------|-------------|
| SSRVAGW00200     | Failed to retrieve DID document.              | -           | Verify DID document retrieval process.         | 500         |
| SSRVAGW00201     | Failed to retrieve VC meta.                   | -           | Check VC meta retrieval process.               | 500         |
| SSRVAGW00202     | Failed to retrieve ZKP Credential Schema.     | -           | Ensure ZKP Schema retrieval is implemented.    | 500         |
| SSRVAGW00203     | Failed to retrieve ZKP Credential Definition. | -           | Check ZKP Credential Definition setup.         | 500         |

## 3. DID Related Errors (003xx)

| Error Code       | Error Message                              | Description | Action Required                      | HTTP Status |
|------------------|--------------------------------------------|-------------|--------------------------------------|-------------|
| SSRVAGW00300     | Failed to find DID: DID value is invalid.  | -           | Verify the DID value and format.     | 400         |
| SSRVAGW00301     | Failed to process DID: DID is invalid.     | -           | Check DID processing and validation. | 400         |

## 4. VC Related Errors (004xx)

| Error Code       | Error Message                                | Description | Action Required                            | HTTP Status |
|------------------|----------------------------------------------|-------------|--------------------------------------------|-------------|
| SSRVAGW00400     | Failed to process VC: VC ID is invalid.      | -           | Verify VC ID format and validity.          | 400         |
| SSRVAGW00401     | Failed to find VC: VC META data not found.   | -           | Check VC META data existence and retrieval.| 400         |

## 5. ZKP Related Errors (005xx)

| Error Code       | Error Message                                | Description | Action Required                             | HTTP Status |
|------------------|----------------------------------------------|-------------|---------------------------------------------|-------------|
| SSRVAGW00500     | Failed to find ZKP Credential Schema.        | -           | Confirm ZKP Credential Schema exists.       | 400         |
| SSRVAGW00501     | Failed to find ZKP Credential Definition.    | -           | Ensure ZKP Credential Definition is present.| 400         |
