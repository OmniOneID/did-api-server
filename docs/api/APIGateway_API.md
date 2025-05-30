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

API Gateway API
==

- Date: 2025-05-30
- Version: v2.0.0
  
Table of Contents
---
<!-- TOC tocDepth:2..3 chapterDepth:2..6 -->

- [1. Overview](#1-overview)
- [2. Terminology](#2-terminology)
- [3. API List](#3-api-list)
    - [3.1. Sequential API](#31-sequential-api)
    - [3.2. Single Call API](#32-single-call-api)
- [4. Single Call API](#4-single-call-api)
    - [4.1. Get DID Document](#41-get-did-document)
    - [4.2. Get VC Metadata](#42-get-vc-metadata)
    - [4.3. Get ZKP Credential Schema](#43-get-zkp-credential-schema)
    - [4.4. Get ZKP Credential Definition](#44-get-zkp-credential-definition)

<!-- /TOC -->


## 1. Overview

This document defines the APIs provided by the API Gateway Service.

<div style="page-break-after: always; margin-top: 50px;"></div>

## 2. Terminology
- Protocol
  - A set of `Sequential APIs` that must be called in a predetermined order to perform a specific function. The API call sequence must be strictly followed, as incorrect ordering may lead to unexpected results.
- Sequential API
  - A series of APIs that are called in a predetermined order to perform a specific function (protocol). Each API must be called sequentially, and incorrect ordering may prevent proper operation.
  - However, some protocols may have APIs with the same calling sequence, in which case one API can be selected and called.
- Single Call API
  - An API that can be called independently regardless of order, like typical REST APIs.
- Standard API
  - APIs clearly defined in the API documentation that must be provided consistently across all implementations. Standard APIs ensure interoperability between systems and must operate according to predefined specifications.
- Non-Standard API
  - APIs that can be defined or customized differently according to the needs of each implementation. The non-standard APIs provided in this document are just one example and may be implemented differently for each implementation. In this case, separate documentation for each implementation is required.
  - For example, DID Document retrieval functionality may be implemented differently depending on the system, and non-standard APIs like `get-diddoc` can be redefined as needed by each implementation.

<div style="page-break-after: always; margin-top: 50px;"></div>

## 3. API List

### 3.1. Sequential API
The API Gateway Service currently has no protocols defined for performing specific functions, and therefore does not provide sequential APIs.

<div style="page-break-after: always; margin-top: 40px;"></div>

### 3.2. Single Call API

| API                 | URL                        | Description                        | Standard API |
| ------------------- | -------------------------- | ---------------------------------- | ----------- |
| `get-diddoc`        | /api/v1/diddoc             | DID Document retrieval             | N           |
| `get-vcmeta`        | /api/v1/vcmeta             | VC metadata retrieval              | N           |
| `get-zkp-credschema` | /api/v1/zkp-cred-schema    | ZKP Credential Schema retrieval    | N           |
| `get-zkp-creddef`   | /api/v1/zkp-cred-def       | ZKP Credential Definition retrieval| N           |

<div style="page-break-after: always; margin-top: 50px;"></div>

## 4. Single Call API

Single Call APIs are independent APIs that perform a specific function.
Therefore, they are not Sequential APIs (aka protocols) which are groups of APIs that must be called in order, so no protocol number is assigned.
The list of Single Call APIs provided by the API Gateway Service is shown in the table below.

| API                 | URL                        | Description                        | Standard API |
| ------------------- | -------------------------- | ---------------------------------- | ----------- |
| `get-diddoc`        | /api/v1/diddoc             | DID Document retrieval             | N           |
| `get-vcmeta`        | /api/v1/vcmeta             | VC metadata retrieval              | N           |
| `get-zkp-credschema` | /api/v1/zkp-cred-schema    | ZKP Credential Schema retrieval    | N           |
| `get-zkp-creddef`   | /api/v1/zkp-cred-def       | ZKP Credential Definition retrieval| N           |

■ Authorization

Protocols include APIs that 'verify the caller's call permission' (authorization).
The Single Call APIs in the above list do not define authorization,
but the following approaches are being considered for future addition.

- Option 1) Issue a token that can be used for a certain period after verifying the `AttestedAppInfo` information signed by the authorized app provider
    - Attach TAS-issued token to header when calling single API
    - Separate token management API required
- Option 2) Authorized app provider issues token to authorized app and TAS requests token verification from authorized app provider
    - Attach authorized app provider-issued token to header when calling single API
    - Authorized app provider needs to implement functionality to issue and verify tokens

### 4.1. Get DID Document

Retrieve DID Document.

| Item          | Description      | Remarks |
| ------------- | ---------------- | ------- |
| Method        | `GET`           |         |
| Path          | `/api/v1/diddoc` |         |
| Authorization | -                |         |

#### 4.1.1. Request

**■ HTTP Headers**

| Header           | Value                            | Remarks |
| ---------------- | -------------------------------- | ------- |
| + `Content-Type` | `application/json;charset=utf-8` |         |     

**■ Path Parameters**

N/A

**■ Query Parameters**

| name    | Description | Remarks |
| ------- | ----------- | ------- |
| + `did` | `did`       |         |

**■ HTTP Body**

N/A

<div style="page-break-after: always; margin-top: 30px;"></div>

#### 4.1.2. Response

**■ Process**
1. Retrieve DID Document by did

**■ Status 200 - Success**

```c#
def object _GetDidDoc: "Get DID Document response"
{
    @spread(DidDoc)  // Refer to data specification
}
```

**■ Status 400 - Client error**

| Code         | Description              |
| ------------ | ------------------------ |
| SSRVAGW00300 | DID does not exist. |
| SSRVAGW00301 | Invalid DID. |

**■ Status 500 - Server error**

| Code         | Description                   |
| ------------ | ----------------------------- |
| SSRVAGW00200 | Failed to retrieve DID.      |


<div style="page-break-after: always; margin-top: 30px;"></div>

#### 4.1.3. Example

**■ Request**

```shell
curl -v -X GET "http://${Host}:${Port}/api/v1/diddoc?did=did%3Aomn%3Atas'"
```

**■ Response**

```http
HTTP/1.1 200 OK
Content-Type: application/json;charset=utf-8

{    
  "didDoc": "meyJAY29udGV4dCI6WyJodHRwczovL3d3dy53My5vcmcvbnMv..."// encodeData
}
```

<div style="page-break-after: always; margin-top: 40px;"></div>

### 4.2. Get VC Metadata

Retrieve VC Metadata.

| Item          | Description      | Remarks |
| ------------- | ---------------- | ------- |
| Method        | `GET`            |         |
| Path          | `/api/v1/vcmeta` |         |
| Authorization | -                |         |

#### 4.2.1. Request

**■ HTTP Headers**

| Header           | Value                            | Remarks |
| ---------------- | -------------------------------- | ------- |
| + `Content-Type` | `application/json;charset=utf-8` |         |     

**■ Path Parameters**

N/A

**■ Query Parameters**

| name     | Description | Remarks |
| -------- | ----------- | ------- |
| + `vcId` | `vcId`      |         |

**■ HTTP Body**

N/A

<div style="page-break-after: always; margin-top: 30px;"></div>

#### 4.2.2. Response

**■ Process**
1. Retrieve VC Metadata by vcId

**■ Status 200 - Success**

```c#
def object _GetVcMEta: "Get VC Metadata response"
{
    @spread(VcMeta)  // Refer to data specification
}
```

**■ Status 400 - Client error**

| Code         | Description             |
| ------------ | ----------------------- |
| SSRVAGW00401 | VC does not exist. |
| SSRVAGW00400 | Invalid VC. |

**■ Status 500 - Server error**

| Code         | Description                   |
| ------------ | ----------------------------- |
| SSRVAGW00201 | Failed to retrieve VC meta.  |

<div style="page-break-after: always; margin-top: 30px;"></div>

#### 4.2.3. Example

**■ Request**

```shell
curl -v -X GET "http://${Host}:${Port}/api/v1/vc-meta?vcId=c184fb29-e6e1-4144-bae0-ccc44a3770df"
```

**■ Response**

```http
HTTP/1.1 200 OK
Content-Type: application/json;charset=utf-8

{
  "vcId": "c184fb29-e6e1-4144-bae0-ccc44a3770df",
  "vcMeta": "meyJjcmVkZW50aWFsU2NoZW1hIjp7ImlkIjoiaHR0cDovLzE..."//encodeData
}
```

<div style="page-break-after: always; margin-top: 40px;"></div>

### 4.3. Get ZKP Credential Schema

Retrieve ZKP Credential Schema.

| Item          | Description                  | Remarks |
| ------------- | ---------------------------- | ------- |
| Method        | `GET`                        |         |
| Path          | `/api/v1/zkp-cred-schema`    |         |
| Authorization | -                            |         |

#### 4.3.1. Request

**■ HTTP Headers**

| Header           | Value                            | Remarks |
| ---------------- | -------------------------------- | ------- |
| + `Content-Type` | `application/json;charset=utf-8` |         |     

**■ Path Parameters**

N/A

**■ Query Parameters**

| name  | Description                        | Remarks |
| ----- | ---------------------------------- | ------- |
| + `id` | ZKP Credential Schema identifier  |         |

**■ HTTP Body**

N/A

<div style="page-break-after: always; margin-top: 30px;"></div>

#### 4.3.2. Response

**■ Process**
1. Retrieve ZKP Credential Schema by ZKP Credential Schema id

**■ Status 200 - Success**

```json
{
  "credSchema": "string"  // Multibase encoded ZKP Credential Schema data
}
```

**■ Status 400 - Client error**

| Code         | Description                             |
| ------------ | --------------------------------------- |
| SSRVAGW00500 | Failed to find ZKP Credential Schema   |

**■ Status 500 - Server error**

| Code         | Description                                 |
| ------------ | ------------------------------------------- |
| SSRVAGW00202 | Failed to retrieve ZKP Credential Schema   |

<div style="page-break-after: always; margin-top: 30px;"></div>

#### 4.3.3. Example

**■ Request**

```shell
curl -v -X GET "http://${Host}:${Port}/api/v1/zkp-cred-schema?id=schema-123"
```

**■ Response**

```http
HTTP/1.1 200 OK
Content-Type: application/json;charset=utf-8

{
  "credSchema": "meyJjcmVkZW50aWFsU2NoZW1hIjp7ImlkIjoiaHR0cDovLzE..."
}
```

<div style="page-break-after: always; margin-top: 40px;"></div>

### 4.4. Get ZKP Credential Definition

Retrieve ZKP Credential Definition.

| Item          | Description                     | Remarks |
| ------------- | ------------------------------- | ------- |
| Method        | `GET`                           |         |
| Path          | `/api/v1/zkp-cred-def`          |         |
| Authorization | -                               |         |

#### 4.4.1. Request

**■ HTTP Headers**

| Header           | Value                            | Remarks |
| ---------------- | -------------------------------- | ------- |
| + `Content-Type` | `application/json;charset=utf-8` |         |     

**■ Path Parameters**

N/A

**■ Query Parameters**

| name  | Description                           | Remarks |
| ----- | ------------------------------------- | ------- |
| + `id` | ZKP Credential Definition identifier |         |

**■ HTTP Body**

N/A

<div style="page-break-after: always; margin-top: 30px;"></div>

#### 4.4.2. Response

**■ Process**
1. Retrieve ZKP Credential Definition by ZKP credential definition id

**■ Status 200 - Success**

```json
{
  "credDef": "string"  // Multibase encoded ZKP Credential Definition data
}
```

**■ Status 400 - Client error**

| Code         | Description                                |
| ------------ | ------------------------------------------ |
| SSRVAGW00501 | Failed to find ZKP Credential Definition  |

**■ Status 500 - Server error**

| Code         | Description                                    |
| ------------ | ---------------------------------------------- |
| SSRVAGW00203 | Failed to retrieve ZKP Credential Definition  |

<div style="page-break-after: always; margin-top: 30px;"></div>

#### 4.4.3. Example

**■ Request**

```shell
curl -v -X GET "http://${Host}:${Port}/api/v1/zkp-cred-def?id=def-456"
```

**■ Response**

```http
HTTP/1.1 200 OK
Content-Type: application/json;charset=utf-8

{
  "credDef": "meyJjcmVkZW50aWFsRGVmaW5pdGlvbiI6eyJpZCI6Imh0dHA..."
}
```