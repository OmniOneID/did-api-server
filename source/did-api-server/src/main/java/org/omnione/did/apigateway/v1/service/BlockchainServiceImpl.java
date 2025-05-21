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

import org.omnione.did.ContractApi;
import org.omnione.did.ContractFactory;
import org.omnione.did.apigateway.v1.dto.DidDocResDto;
import org.omnione.did.apigateway.v1.dto.VcMetaResDto;
import org.omnione.did.apigateway.v1.dto.ZkpCredDefResDto;
import org.omnione.did.apigateway.v1.dto.ZkpCredSchemaResDto;
import org.omnione.did.base.exception.ErrorCode;
import org.omnione.did.base.exception.OpenDidException;
import org.omnione.did.base.property.BlockchainProperty;
import org.omnione.did.base.util.BaseMultibaseUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.omnione.did.common.util.DidValidator;
import org.omnione.did.data.model.did.DidDocAndStatus;
import org.omnione.did.data.model.vc.VcMeta;
import org.omnione.did.zkp.datamodel.definition.CredentialDefinition;
import org.omnione.did.zkp.datamodel.schema.CredentialSchema;
import org.omnione.exception.BlockChainException;
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

    private ContractApi contractApiInstance = null;

    private final BlockchainProperty blockchainProperty;

    /**
     * Initializes the blockchain connection.
     *
     * @return a ContractApi instance.
     */
    public ContractApi initBlockChain() {
        return ContractFactory.EVM.create(blockchainProperty.getFilePath());
    }

    public ContractApi getContractApiInstance() {
        if (contractApiInstance == null) {
            synchronized (BlockchainServiceImpl.class) {
                if (contractApiInstance == null) {
                    contractApiInstance = initBlockChain();
                }
            }
        }
        return contractApiInstance;
    }

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
            ContractApi contractApi = getContractApiInstance();
            DidDocAndStatus didDocAndStatus = (DidDocAndStatus) contractApi.getDidDoc(didKeyUrl);

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
            throw new OpenDidException(ErrorCode.GET_DID_DOC_FAILED);
        } catch (Exception e) {
            log.error("Failed to find DID Document: " + e.getMessage());
            throw new OpenDidException(ErrorCode.GET_DID_DOC_FAILED);
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
        try {
            ContractApi contractApi = getContractApiInstance();
            VcMeta vcMeta = (VcMeta) contractApi.getVcMetadata(vcId);

            if (vcMeta == null)  {
                throw new OpenDidException(ErrorCode.VC_NOT_FOUND);
            }
            String encodedVcMeta = BaseMultibaseUtil.encode(vcMeta.toJson().getBytes(StandardCharsets.UTF_8));

            return VcMetaResDto.builder()
                    .vcId(vcId)
                    .vcMeta(encodedVcMeta)
                    .build();
        } catch (BlockChainException e) {
            log.error("Failed to find VC Meta: " + e.getMessage());
            throw new OpenDidException(ErrorCode.VC_META_RETRIEVAL_FAILED);
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
        try {
            ContractApi contractApi = getContractApiInstance();
            CredentialSchema credSchema = (CredentialSchema) contractApi.getZKPCredential(id);

            if (credSchema == null) {
                throw new OpenDidException(ErrorCode.ZKP_CRED_SCHEMA_NOT_FOUND);
            }

            String encodedCredSchema = BaseMultibaseUtil.encode(credSchema.toJson().getBytes(StandardCharsets.UTF_8));

            return ZkpCredSchemaResDto.builder()
                    .credSchema(encodedCredSchema)
                    .build();
        } catch (OpenDidException e) {
            log.error("Failed to find ZKP Credential Schema: " + e.getMessage());
            throw new OpenDidException(ErrorCode.ZKP_CRED_SCHEMA_RETRIEVAL_FAILED);
        } catch (BlockChainException e) {
            log.error("Failed to find ZKP Credential Schema: " + e.getMessage());
            throw new OpenDidException(ErrorCode.ZKP_CRED_SCHEMA_RETRIEVAL_FAILED);
        }
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
        try {
            ContractApi contractApi = getContractApiInstance();
            CredentialDefinition credDef = (CredentialDefinition) contractApi.getZKPCredentialDefinition(id);

            if (credDef == null) {
                throw new OpenDidException(ErrorCode.ZKP_CRED_DEF_NOT_FOUND);
            }

            String encodedDredDef = BaseMultibaseUtil.encode(credDef.toJson().getBytes(StandardCharsets.UTF_8));

            return ZkpCredDefResDto.builder()
                .credDef(encodedDredDef)
                .build();
        } catch (OpenDidException e) {
            log.error("Failed to find ZKP Credential Definition: " + e.getMessage());
            throw new OpenDidException(ErrorCode.ZKP_CRED_DEF_RETRIEVAL_FAILED);

        } catch (BlockChainException e) {
            log.error("Failed to find ZKP Credential Definition: " + e.getMessage());
            throw new OpenDidException(ErrorCode.ZKP_CRED_DEF_RETRIEVAL_FAILED);
        }
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