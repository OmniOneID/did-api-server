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

package org.omnione.did.base.util;

import lombok.extern.slf4j.Slf4j;
import org.omnione.did.ContractApi;
import org.omnione.did.ContractFactory;
import org.omnione.did.base.exception.ErrorCode;
import org.omnione.did.base.exception.OpenDidException;
import org.omnione.did.data.model.did.DidDocAndStatus;
import org.omnione.did.data.model.vc.VcMeta;
import org.omnione.exception.BlockChainException;

/**
 * BaseBlockChainUtil
 * This class provides utility methods for interacting with the blockchain.
 */
@Slf4j
public class BaseBlockChainUtil {

    private static ContractApi contractApiInstance = getContractApiInstance();
    /**
     * Initializes the blockchain.
     *
     * @return The ContractApi instance.
     * @throws OpenDidException if the blockchain initialization fails.
     */
    public static ContractApi initBlockChain() {
        return ContractFactory.FABRIC.create("properties/blockchain.properties");
    }

    /**
     * Gets the ContractApi instance.
     *
     * @return The ContractApi instance.
     */
    public static ContractApi getContractApiInstance() {
        if (contractApiInstance == null) {
            synchronized (BaseBlockChainUtil.class) {
                if (contractApiInstance == null) {
                    contractApiInstance = initBlockChain();
                }
            }
        }
        return contractApiInstance;
    }

    /**
     * Find DID Document
     * @param didKeyUrl The Decentralized Identifier (DID) to look up.
     * @return DidDocAndStatus containing the DID document and its status.
     * @throws OpenDidException if the DID is invalid or not found.
     */
    public static DidDocAndStatus findDidDocument(String didKeyUrl) {
        try {
            ContractApi contractApi = getContractApiInstance();
            return (DidDocAndStatus) contractApi.getDidDoc(didKeyUrl);
        } catch (BlockChainException e) {
            log.error("Failed to get DID Document: " + e.getMessage());
            throw new OpenDidException(ErrorCode.GET_DID_DOC_FAILED);
        }
    }

    /**
     * Find VC Meta
     * @param vcId The identifier of the Verifiable Credential.
     * @return VcMeta containing the VC metadata.
     * @throws OpenDidException if the VC ID is invalid or the VC is not found.
     */
    public static VcMeta findVcMeta(String vcId) {
        try {
            ContractApi contractApi = getContractApiInstance();
            return (VcMeta) contractApi.getVcMetadata(vcId);
        } catch (BlockChainException e) {
            log.error("Failed to find VC Meta: " + e.getMessage());
            throw new OpenDidException(ErrorCode.VC_META_RETRIEVAL_FAILED);
        }
    }
}
