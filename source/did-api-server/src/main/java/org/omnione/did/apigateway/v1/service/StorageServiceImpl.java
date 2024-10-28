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

package org.omnione.did.apigateway.v1.service;

import org.omnione.did.apigateway.v1.api.RepositoryFeign;
import org.omnione.did.apigateway.v1.api.dto.DidDocApiResDto;
import org.omnione.did.apigateway.v1.api.dto.VcMetaApiResDto;
import org.omnione.did.apigateway.v1.dto.DidDocResDto;
import org.omnione.did.apigateway.v1.dto.VcMetaResDto;
import org.omnione.did.base.exception.ErrorCode;
import org.omnione.did.base.exception.OpenDidException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@Profile("repository")
@Service
public class StorageServiceImpl implements StorageService {
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
        checkValidation(did);
        DidDocApiResDto didDoc = repositoryFeign.getDid(did);
        DidDocResDto resDidDoc = DidDocResDto.builder()
                .didDoc(didDoc.getDidDoc())
                .build();

        if(resDidDoc == null || resDidDoc.getDidDoc().isEmpty()){
            throw new OpenDidException(ErrorCode.DID_NOT_FOUND);
        }
        return resDidDoc;
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
        if(vcId == null || vcId.isEmpty()){
            throw new OpenDidException(ErrorCode.VC_ID_INVALID);
        }
        VcMetaApiResDto vcMetaData = repositoryFeign.getVcMetaData(vcId);

        if(vcMetaData == null || vcMetaData.getVcMeta().isEmpty()){
            throw new OpenDidException(ErrorCode.VC_NOT_FOUND);
        }

        return VcMetaResDto.builder()
                .vcMeta(vcMetaData.getVcMeta())
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