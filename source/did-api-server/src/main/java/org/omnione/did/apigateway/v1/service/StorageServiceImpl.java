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

import feign.FeignException;
import org.omnione.did.apigateway.v1.api.RepositoryFeign;
import org.omnione.did.apigateway.v1.api.dto.DidDocApiResDto;
import org.omnione.did.apigateway.v1.api.dto.VcMetaApiResDto;
import org.omnione.did.apigateway.v1.dto.DidDocResDto;
import org.omnione.did.apigateway.v1.dto.VcMetaResDto;
import org.omnione.did.apigateway.v1.dto.ZkpCredDefResDto;
import org.omnione.did.apigateway.v1.dto.ZkpCredSchemaResDto;
import org.omnione.did.base.exception.ErrorCode;
import org.omnione.did.base.exception.OpenDidException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.omnione.did.base.util.BaseMultibaseUtil;
import org.omnione.did.common.util.DidValidator;
import org.omnione.did.data.model.vc.VcMeta;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;

/**
 * Implementation of the StorageService interface.
 * This service manages the retrieval of DID documents and VC metadata from a repository.
 */
@RequiredArgsConstructor
@Slf4j
@Profile("lls")
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
        try {
            checkValidation(did);
            String didDocument = repositoryFeign.getDid(did);

            String encodedDidDoc = BaseMultibaseUtil.encode(didDocument.getBytes(StandardCharsets.UTF_8));

            return DidDocResDto.builder()
                    .didDoc(encodedDidDoc)
                    .build();
        } catch (OpenDidException e) {
            log.error("Failed to find DID document.", e);
            throw e;
        } catch (
                FeignException e) {
            log.error("Failed to find DID document.", e);
            throw new OpenDidException(ErrorCode.DID_NOT_FOUND);
        } catch (Exception e) {
            log.error("Failed to find DID document.", e);
            throw new OpenDidException(ErrorCode.DID_NOT_FOUND);
        }
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
        if (vcId == null || vcId.isEmpty()) {
            throw new OpenDidException(ErrorCode.VC_ID_INVALID);
        }

        try {
            String vcMetaData = repositoryFeign.getVcMetaData(vcId);
            String encodedVcMeta = BaseMultibaseUtil.encode(vcMetaData.getBytes(StandardCharsets.UTF_8));

            return VcMetaResDto.builder()
                    .vcId(vcId)
                    .vcMeta(encodedVcMeta)
                    .build();
        } catch (OpenDidException e) {
            log.error("Failed to find VC meta data.", e);
            throw e;
        } catch (FeignException e) {
            log.error("Failed to find VC meta data.", e);
            throw new OpenDidException(ErrorCode.VC_NOT_FOUND);
        } catch (Exception e) {
            log.error("Failed to find VC meta data.", e);
            throw new OpenDidException(ErrorCode.VC_NOT_FOUND);
        }
    }

    /**
     * Retrieves a Zero-Knowledge Proof (ZKP) credential schema from the blockchain.
     *
     * @param id The identifier of the ZKP credential schema to retrieve.
     * @return ZkpCredSchemaResDto containing the encoded credential schema.
     * @throws OpenDidException if the schema is not found or cannot be retrieved.
     */
    @Override
    public ZkpCredSchemaResDto findZkpCredSchema(String id) {
        return null;
    }

    /**
     * Retrieves a Zero-Knowledge Proof (ZKP) credential definition from the blockchain.
     *
     * @param id The identifier of the ZKP credential definition to retrieve.
     * @return ZkpCredDefResDto containing the encoded credential definition.
     * @throws OpenDidException if the definition is not found or cannot be retrieved.
     */
    @Override
    public ZkpCredDefResDto findZkpCredDef(String id) {
        return null;
    }

    /**
     * Validates the given DID.
     *
     * @param didKeyUrl The DID to validate.
     * @throws OpenDidException if the DID is null, empty, or invalid.
     */
    private void checkValidation(String didKeyUrl) {
        if (didKeyUrl == null || didKeyUrl.isEmpty()) {
            throw new OpenDidException(ErrorCode.DID_NOT_FOUND);
        }
        if (!DidValidator.isValidDid(didKeyUrl)) {
            throw new OpenDidException(ErrorCode.DID_INVALID);
        }
    }
}