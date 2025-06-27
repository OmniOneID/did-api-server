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
| v2.0.0  | 2025-05-30 | ZKP 관련 에러 코드 추가 |
| v1.0.0  | 2024-09-03 | 최초 버전              |

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

### 설명
```
API Gateway용 에러 구조체. 코드와 메시지 쌍을 가지고 있습니다.
코드는 SSRVAGW로 시작합니다.
```

### 선언
```java
public class ErrorResponse {
    private final String code;
    private final String description;
}
```

### 속성

| Name        | Type   | Description         | **M/O** | **Note** |
|-------------|--------|---------------------|---------|----------|
| code        | String | 에러 코드          | M       |          |
| message     | String | 에러 설명   | M       |          |
| httpStatus  | int    | HTTP 상태 코드    | M       |          |

# Error Code

## 1. Encoding Errors (001xx)

| Error Code       | Error Message              | Description | Action Required                        | HTTP Status |
|------------------|----------------------------|-------------|----------------------------------------|-------------|
| SSRVAGW00100     | Failed to encode data.     | -           | 인코딩 프로세스와 입력 데이터를 확인하세요. | 500         |

## 2. Document Retrieval Errors (002xx)

| Error Code       | Error Message                                 | Description | Action Required                                | HTTP Status |
|------------------|-----------------------------------------------|-------------|------------------------------------------------|-------------|
| SSRVAGW00200     | Failed to retrieve DID document.              | -           | DID 문서 검색 프로세스를 확인하세요.         | 500         |
| SSRVAGW00201     | Failed to retrieve VC meta.                   | -           | VC 메타 검색 프로세스를 확인하세요.               | 500         |
| SSRVAGW00202     | Failed to retrieve ZKP Credential Schema.     | -           | ZKP 스키마 검색이 구현되었는지 확인하세요.    | 500         |
| SSRVAGW00203     | Failed to retrieve ZKP Credential Definition. | -           | ZKP Credential Definition 설정을 확인하세요.         | 500         |

## 3. DID Related Errors (003xx)

| Error Code       | Error Message                              | Description | Action Required                      | HTTP Status |
|------------------|--------------------------------------------|-------------|--------------------------------------|-------------|
| SSRVAGW00300     | Failed to find DID: DID value is invalid.  | -           | DID 값과 형식을 확인하세요.     | 400         |
| SSRVAGW00301     | Failed to process DID: DID is invalid.     | -           | DID 처리와 검증을 확인하세요. | 400         |

## 4. VC Related Errors (004xx)

| Error Code       | Error Message                                | Description | Action Required                            | HTTP Status |
|------------------|----------------------------------------------|-------------|--------------------------------------------|-------------|
| SSRVAGW00400     | Failed to process VC: VC ID is invalid.      | -           | VC ID 형식과 유효성을 확인하세요.          | 400         |
| SSRVAGW00401     | Failed to find VC: VC META data not found.   | -           | VC META 데이터 존재 여부와 검색을 확인하세요.| 400         |

## 5. ZKP Related Errors (005xx)

| Error Code       | Error Message                                | Description | Action Required                             | HTTP Status |
|------------------|----------------------------------------------|-------------|---------------------------------------------|-------------|
| SSRVAGW00500     | Failed to find ZKP Credential Schema.        | -           | ZKP Credential Schema가 존재하는지 확인하세요.       | 400         |
| SSRVAGW00501     | Failed to find ZKP Credential Definition.    | -           | ZKP Credential Definition이 있는지 확인하세요.| 400         |