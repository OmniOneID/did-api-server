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

package org.omnione.did.apigateway.v1.service.sample;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.omnione.did.apigateway.v1.api.RepositoryFeign;
import org.omnione.did.apigateway.v1.api.dto.DidDocApiResDto;
import org.omnione.did.apigateway.v1.api.dto.VcMetaApiResDto;
import org.omnione.did.apigateway.v1.dto.DidDocResDto;
import org.omnione.did.apigateway.v1.dto.VcMetaResDto;
import org.omnione.did.apigateway.v1.service.StorageService;
import org.omnione.did.base.exception.ErrorCode;
import org.omnione.did.base.exception.OpenDidException;
import org.omnione.did.common.util.DidValidator;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

/**
 * Implementation of the StorageService interface.
 * This service manages the retrieval of DID documents and VC metadata from a repository.
 *
 */
@RequiredArgsConstructor
@Slf4j
@Profile("sample")
@Service
public class StorageServiceSample implements StorageService {
    private final RepositoryFeign repositoryFeign;

    /**
     * Retrieves a DID document for a given DID.
     *
     * @param did The Decentralized Identifier (DID) to look up.
     * @return DidDocResDto containing the DID document.
     * @throws OpenDidException if the DID is invalid or not found.
     */
    @Override
    public DidDocResDto findDidDocument(String did) {
        return DidDocResDto.builder()
                .didDoc("meyJAY29udGV4dCI6WyJodHRwczovL3d3dy53My5vcmcvbnMvZGlkL3YxIl0sImFzc2VydGlvbk1ldGhvZCI6WyJhc3NlcnQiXSwiYXV0aGVudGljYXRpb24iOlsiYXV0aCJdLCJjb250cm9sbGVyIjoiZGlkOm9tbjppc3N1ZXIiLCJjcmVhdGVkIjoiMjAyNC0wOS0wNVQwMTo1NTowNFoiLCJkZWFjdGl2YXRlZCI6ZmFsc2UsImlkIjoiZGlkOm9tbjppc3N1ZXIiLCJrZXlBZ3JlZW1lbnQiOlsia2V5YWdyZWUiXSwidXBkYXRlZCI6IjIwMjQtMDktMDVUMDE6NTU6MDRaIiwidmVyaWZpY2F0aW9uTWV0aG9kIjpbeyJhdXRoVHlwZSI6MSwiY29udHJvbGxlciI6ImRpZDpvbW46aXNzdWVyIiwiaWQiOiJhc3NlcnQiLCJwdWJsaWNLZXlNdWx0aWJhc2UiOiJ6Y3lzMWluNVdBUmdLeXZkdDd0NFFNMlhuUjZ2ck4xeVhjTmtpN3JwY1JncFoiLCJ0eXBlIjoiU2VjcDI1NnIxVmVyaWZpY2F0aW9uS2V5MjAxOCJ9LHsiYXV0aFR5cGUiOjEsImNvbnRyb2xsZXIiOiJkaWQ6b21uOmlzc3VlciIsImlkIjoiYXV0aCIsInB1YmxpY0tleU11bHRpYmFzZSI6InoyMW03am5oYmhkejZxNkZ4MWF6ZGpiQ1U5cDZuenBoMXNUdGY1OVBuM3J4QlkiLCJ0eXBlIjoiU2VjcDI1NnIxVmVyaWZpY2F0aW9uS2V5MjAxOCJ9LHsiYXV0aFR5cGUiOjEsImNvbnRyb2xsZXIiOiJkaWQ6b21uOmlzc3VlciIsImlkIjoia2V5YWdyZWUiLCJwdWJsaWNLZXlNdWx0aWJhc2UiOiJ6Y2lGOFp5aERpUkVvcUN1eTNwTVFrM2J5SlluNThXZkdXVjdoeWZqUmlYQ0IiLCJ0eXBlIjoiU2VjcDI1NnIxVmVyaWZpY2F0aW9uS2V5MjAxOCJ9XSwidmVyc2lvbklkIjoiMSJ9")
                .build();
    }

    /**
     * Retrieves metadata for a Verifiable Credential (VC).
     *
     * @param vcId The identifier of the Verifiable Credential.
     * @return VcMetaResDto containing the VC metadata.
     * @throws OpenDidException if the VC ID is invalid or the VC is not found.
     */
    @Override
    public VcMetaResDto findVcMeta(String vcId) {
        return VcMetaResDto.builder()
                .vcId("c184fb29-e6e1-4144-bae0-ccc44a3770df")
                .vcMeta("meyJjcmVkZW50aWFsU2NoZW1hIjp7ImlkIjoiaHR0cDovLzE5Mi4xNjguMy4xMzA6ODA5MC90YXMvYXBpL3YxL3ZjLXNjaGVtYT9uYW1lPWNlcnRpZmljYXRlIiwidHlwZSI6Ik9zZFNjaGVtYUNyZWRlbnRpYWwifSwiZm9ybWF0VmVyc2lvbiI6IjEuMCIsImlkIjoiYzE4NGZiMjktZTZlMS00MTQ0LWJhZTAtY2NjNDRhMzc3MGRmIiwiaXNzdWFuY2VEYXRlIjoiMjAyNC0wOS0wNVQwMTo0ODo1OFoiLCJpc3N1ZXIiOnsiY2VydFZjUmVmIjoiaHR0cDovLzE5Mi4xNjguMy4xMzA6ODA5MC90YXMvYXBpL3YxL2NlcnRpZmljYXRlLXZjIiwiZGlkIjoiZGlkOm9tbjp0YXMifSwibGFuZ3VhZ2UiOiJrbyIsInN0YXR1cyI6IkFDVElWRSIsInN1YmplY3QiOiJkaWQ6b21uOnRhcyIsInZhbGlkRnJvbSI6IjIwMjQtMDktMDVUMDE6NDg6NThaIiwidmFsaWRVbnRpbCI6IjIwMjUtMDktMDVUMDE6NDg6NThaIn0")
                .build();
    }

    /**
     * Validates the given DID.
     *
     * @param didKeyUrl The DID to validate.
     * @throws OpenDidException if the DID is null, empty, or invalid.
     */
    private void checkValidation(String didKeyUrl) {
        if(didKeyUrl == null || didKeyUrl.isEmpty()){
            throw new OpenDidException(ErrorCode.DID_NOT_FOUND);
        }
        if (!DidValidator.isValidDid(didKeyUrl)) {
            throw new OpenDidException(ErrorCode.DID_INVALID);
        }
    }
}