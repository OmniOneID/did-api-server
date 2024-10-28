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

import org.omnione.did.apigateway.v1.dto.DidDocResDto;
import org.omnione.did.apigateway.v1.dto.VcMetaResDto;
import org.omnione.did.base.exception.ErrorCode;
import org.omnione.did.base.exception.OpenDidException;
import org.omnione.did.base.util.BaseBlockChainUtil;
import org.omnione.did.base.util.BaseMultibaseUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.omnione.did.common.util.DidValidator;
import org.omnione.did.data.model.did.DidDocAndStatus;
import org.omnione.did.data.model.vc.VcMeta;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;

/**
 * Implementation of the StorageService interface using blockchain.
 * This service manages the retrieval of DID documents and VC metadata from a blockchain.
 *
 */
@RequiredArgsConstructor
@Slf4j
@Profile({"!repository & !sample"})
@Service
public class BlockchainServiceImpl implements StorageService {

    /**
     * Retrieves a DID document for a given DID from the blockchain.
     *
     * @param didKeyUrl The Decentralized Identifier (DID) to look up.
     * @return DidDocResDto containing the encoded DID document.
     * @throws OpenDidException if the DID is invalid or not found.
     */
    @Override
    public DidDocResDto findDidDocument(String didKeyUrl) {
        checkValidation(didKeyUrl);

        try {
            DidDocAndStatus didDocAndStatus = BaseBlockChainUtil.findDidDocument(didKeyUrl);

            if (didDocAndStatus == null) {
                throw new OpenDidException(ErrorCode.DID_NOT_FOUND);
            }

            String encodedDidDoc = BaseMultibaseUtil.encode(didDocAndStatus.getDocument()
                    .toJson().getBytes(StandardCharsets.UTF_8));

            return DidDocResDto.builder()
                    .didDoc(encodedDidDoc)
                    .build();
        } catch (OpenDidException e) {
            log.error("Failed to find DID Document: " + e.getMessage());
            throw e;
        }
    }

    /**
     * Retrieves metadata for a Verifiable Credential (VC) from the blockchain.
     *
     * @param vcId The identifier of the Verifiable Credential.
     * @return VcMetaResDto containing the encoded VC metadata.
     * @throws OpenDidException if the VC ID is invalid or the VC is not found.
     */
    @Override
    public VcMetaResDto findVcMeta(String vcId) {
        if (vcId == null || vcId.isEmpty()) {
            throw new OpenDidException(ErrorCode.VC_NOT_FOUND);
        }
        VcMeta vcMeta = BaseBlockChainUtil.findVcMeta(vcId);

        if (vcMeta == null)  {
            throw new OpenDidException(ErrorCode.VC_NOT_FOUND);
        }
        String encodedVcMeta = BaseMultibaseUtil.encode(vcMeta.toJson().getBytes(StandardCharsets.UTF_8));

        return VcMetaResDto.builder()
                .vcId(vcId)
                .vcMeta(encodedVcMeta)
                .build();
    }

    /**
     * Validates the given DID.
     *
     * @param did The DID to validate.
     * @throws OpenDidException if the DID is null, empty, or invalid.
     */
    private void checkValidation(String did) {
        if (did == null || did.isEmpty()) {
            throw new OpenDidException(ErrorCode.DID_NOT_FOUND);
        }
        if (!DidValidator.isValidDid(did)) {
            throw new OpenDidException(ErrorCode.DID_INVALID);
        }
    }
}