/*
 * Copyright 2024 OmniOne.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.omnione.did.base.exception;

import lombok.Getter;

/**
 * Enumeration of error codes used in the DID Verifier system.
 * Each error code contains a unique identifier, a descriptive message, and an associated HTTP status code.
 *
 */
@Getter
public enum ErrorCode {

    ENCODING_FAILED("SSRVAGW00100", "Failed to encode data.", 500),
    DECODING_FAILED("SSRVAGW00101", "Failed to decode data.", 500),

    GET_DID_DOC_FAILED("SSRVAGW00200", "Failed to retrieve DID document.", 500),
    VC_META_RETRIEVAL_FAILED("SSRVAGW00201", "Failed to retrieve VC meta.", 500),
    ZKP_CRED_SCHEMA_RETRIEVAL_FAILED("SSRVAGW00202", "Failed to retrieve ZKP Credential Schema", 500),
    ZKP_CRED_DEF_RETRIEVAL_FAILED("SSRVAGW00203", "Failed to retrieve ZKP Credential Definition", 500),

    DID_NOT_FOUND("SSRVAGW00300", "Failed to find DID: DID value is invalid.", 400),
    DID_INVALID("SSRVAGW00301", "Failed to process DID: DID is invalid.", 400),

    VC_ID_INVALID("SSRVAGW00400", "Failed to process VC: VC ID is invalid.", 400),
    VC_NOT_FOUND("SSRVAGW00401", "Failed to find VC: VC META data not found.", 400),

    ZKP_CRED_SCHEMA_NOT_FOUND("SSRVAGW00500", "Failed to find ZKP Credential Schema", 400),
    ZKP_CRED_DEF_NOT_FOUND("SSRVAGW00501", "Failed to find ZKP Credential Definition", 400);

    private final String code;
    private final String message;
    private final int httpStatus;

    /**
     * Constructor for ErrorCode enum.
     *
     * @param code The unique error code.
     * @param message The descriptive error message.
     * @param httpStatus The associated HTTP status code.
     */
    ErrorCode(String code, String message, int httpStatus) {
        this.code = code;
        this.message = message;
        this.httpStatus = httpStatus;
    }

    /**
     * Retrieves the error message associated with a given error code.
     *
     * @param code The error code to look up.
     * @return The corresponding error message, or a default message if the code is not found.
     */
    public static String getMessageByCode(String code) {
        for (ErrorCode errorCode : values()) {
            if (errorCode.getCode().equals(code)) {
                return errorCode.getMessage();
            }
        }
        return "Unknown error code: " + code;
    }
}
