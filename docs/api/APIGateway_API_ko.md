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

- 일자: 2025-05-30
- 버전: v2.0.0
  
목차
---
<!-- TOC tocDepth:2..3 chapterDepth:2..6 -->

- [1. 개요](#1-개요)
- [2. 용어 설명](#2-용어-설명)
- [3. API 목록](#3-api-목록)
    - [3.1. 순차 API](#31-순차-api)
    - [3.2. 단일호출 API](#32-단일호출-api)
- [4. 단일 호출 API](#4-단일-호출-api)
    - [4.1. Get DID Document](#41-get-did-document)
    - [4.2. Get VC Metadata](#42-get-vc-metadata)
    - [4.3. Get ZKP Credential Schema](#43-get-zkp-credential-schema)
    - [4.4. Get ZKP Credential Definition](#44-get-zkp-credential-definition)

<!-- /TOC -->


## 1. 개요

본 문서는 API Gateway Service가 제공하는 API를 정의한다.

<div style="page-break-after: always; margin-top: 50px;"></div>

## 2. 용어 설명
- 프로토콜 (Protocol)
  - 특정 기능을 수행하기 위해 정해진 순서에 따라 호출해야 하는 `순차 API`의 집합이다. API 호출 순서를 엄격히 따라야 하며, 순서가 잘못될 경우 예상하지 못한 결과가 발생할 수 있다.
- 순차 API (Sequential API)
  - 특정 기능(프로토콜)을 수행하기 위해 정해진 순서대로 호출하는 일련의 API를 말한다. 각 API는 순차적으로 호출되어야 하며, 순서가 잘못될 경우 제대로 동작하지 않을 수 있다.
  - 그러나 일부 프로토콜에서는 같은 호출 순서를 가진 API가 존재할 수 있으며, 이 경우 하나의 API를 선택하여 호출할 수 있다.
- 단일 호출 API (Single Call API)
  - 일반적인 REST API처럼 순서에 관계없이 독립적으로 호출 가능한 API를 의미한다.
- 표준 API (Standard API)
  - API 문서에서 명확하게 정의된 API로, 모든 구현체에서 일관된 방식으로 제공되어야 한다. 표준 API는 시스템 간 상호 운용성을 보장하며, 사전에 정의된 스펙에 따라 동작해야 한다.
- 비표준 API (Non-Standard API)
  - 구현체마다 필요에 따라 다르게 정의되거나 커스터마이징될 수 있는 API이다. 본 문서에서 제공하는 비표준 API는 한 가지 예시일 뿐이며, 각 구현체에 맞춰 다르게 구현될 수 있다. 이 경우, 구현체별 별도의 문서화가 필요하다.
  - 예를 들어, DID Document 조회 기능은 시스템에 따라 구현 방법이 달라질 수 있으며, `get-diddoc` API와 같은 비표준 API는 각 구현체에서 필요한 방식으로 재정의할 수 있다.

<div style="page-break-after: always; margin-top: 50px;"></div>

## 3. API 목록

### 3.1. 순차 API
API Gateway Service는 현재 특정 기능을 수행하기 위한 프로토콜이 정의되어 있지 않으며, 따라서 순차 API도 제공하지 않는다.

<div style="page-break-after: always; margin-top: 40px;"></div>

### 3.2. 단일호출 API

| API                 | URL                        | Description                  | 표준API |
| ------------------- | -------------------------- | ---------------------------- | ------- |
| `get-diddoc`        | /api/v1/diddoc             | DID Document 조회            | N       |
| `get-vcmeta`        | /api/v1/vcmeta             | VC 메타데이터 조회           | N       |
| `get-zkp-credschema` | /api/v1/zkp-cred-schema    | ZKP Credential Schema 조회   | N       |
| `get-zkp-creddef`   | /api/v1/zkp-cred-def       | ZKP Credential Definition 조회 | N       |

<div style="page-break-after: always; margin-top: 50px;"></div>

## 4. 단일 호출 API

단일 호출 API는 특정 기능을 수행하는 하나의 독립된 API이다.
따라서 순서대로 호출해야 하는 API의 집단인 순차 API(aka, 프로토콜)이 아니므로 프로토콜 번호가 부여되지 않는다.
API Gateway Service가 제공하는 단일 호출 API 목록은 아래 표와 같다.

| API                 | URL                        | Description                  | 표준API |
| ------------------- | -------------------------- | ---------------------------- | ------- |
| `get-diddoc`        | /api/v1/diddoc             | DID Document 조회            | N       |
| `get-vcmeta`        | /api/v1/vcmeta             | VC 메타데이터 조회           | N       |
| `get-zkp-credschema` | /api/v1/zkp-cred-schema    | ZKP Credential Schema 조회   | N       |
| `get-zkp-creddef`   | /api/v1/zkp-cred-def       | ZKP Credential Definition 조회 | N       |

■ Authorization

프로토콜에는 '호출자의 호출 권한을 확인'(authorization)하는 API가 포함되어 있다.
상기 목록의 단일 호출 API는 authroization에 대하여 정의하지 않았으나,
향후 다음의 방안을 고려하여 추가할 예정이다.

- 1안) 인가앱 사업자가 서명한 `AttestedAppInfo` 정보를 확인한 후 일정기간 사용이 가능한 토큰을 발급
    - 단일 API 호출 시 헤더에 TAS 발행 토큰을 첨부
    - 별도의 토큰 관리 API 필요
- 2안) 인가앱 사업자가 인가앱에 토큰을 발행하고 TAS가 인가앱 사업자에 토큰 검증을 요청
    - 단일 API 호출 시 헤더에 인가앱 사업자 발행 토큰을 첨부
    - 인가앱 사업자가 토큰을 발행하고 검증해주는 기능 구현 필요

### 4.1. Get DID Document

DID Document를 조회한다.

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

#### 4.1.2. Resposne

**■ Process**
1. did로 DID Document 조회

**■ Status 200 - Success**

```c#
def object _GetDidDoc: "Get DID Document 응답문"
{
    @spread(DidDoc)  // 데이터 명세서 참고
}
```

**■ Status 400 - Client error**

| Code         | Description              |
| ------------ | ------------------------ |
| SSRVAGW00300 | 존재하지 않는 DID입니다. |
| SSRVAGW00301 | 유효하지 않은 DID입니다. |

**■ Status 500 - Server error**

| Code         | Description                   |
| ------------ | ----------------------------- |
| SSRVAGW00200 | DID 조회에 실패했습니다.      |


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

VC Metadata를 조회한다.

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
1. vcId로 VC Metadata 조회

**■ Status 200 - Success**

```c#
def object _GetVcMEta: "Get VC Metadata 응답문"
{
    @spread(VcMeta)  // 데이터 명세서 참고
}
```

**■ Status 400 - Client error**

| Code         | Description             |
| ------------ | ----------------------- |
| SSRVAGW00401 | 존재하지 않는 VC입니다. |
| SSRVAGW00400 | 유효하지 않은 VC입니다. |

**■ Status 500 - Server error**

| Code         | Description                   |
| ------------ | ----------------------------- |
| SSRVAGW00201 | VC meta 조회에 실패했습니다.  |

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

ZKP Credential Schema를 조회한다.

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
1. ZKP Credential Schema id로 ZKP Credential Schema 조회

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

ZKP Credential Definition을 조회한다.

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
1. ZKP credential definition id로 ZKP Credential Definition 조회

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