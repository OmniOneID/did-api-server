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

package org.omnione.did.apigateway.v1.api;

import org.omnione.did.apigateway.v1.api.dto.ZkpCredDefResDto;
import org.omnione.did.apigateway.v1.api.dto.ZkpCredSchemaResDto;
import org.omnione.did.base.constants.UrlConstant;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Feign client for the Storage server.
 * This class was temporarily used instead of the BlockChain service and is no longer in use.
 */
@FeignClient(value = "Storage", url = "${lls.url:http://127.0.0.1:8098}" + UrlConstant.LLS.V1)
public interface RepositoryFeign {

    /**
     * Gets a DID document by its DID.
     *
     * @param did DID to get the document for.
     * @return Found DID document.
     */
    @GetMapping(UrlConstant.LLS.DID)
    String getDid(@RequestParam(name = "did") String did);

    /**
     * Gets metadata for a Verifiable Credential (VC) by its identifier.
     *
     * @param vcId Identifier of the Verifiable Credential.
     * @return Found VC metadata.
     */
    @GetMapping(UrlConstant.LLS.VC_META)
    String getVcMetaData(@RequestParam(name = "vcId") String vcId);

    /**
     * Get ZKP Credential Schema
     *
     * @param id Schema ID
     * @return ZKP Credential Schema API Response DTO
     */
    @GetMapping(UrlConstant.LLS.CREDENTIAL_SCHEMA)
    ZkpCredSchemaResDto getZkpCredSchema(@RequestParam(name = "id") String id);

    /**
     * Get ZKP Credential Definition
     *
     * @param id Definition ID
     * @return ZKP Credential Definition API Response DTO
     */
    @GetMapping(UrlConstant.LLS.CREDENTIAL_DEFINITION)
    ZkpCredDefResDto getZkpCredDef(@RequestParam(name = "id") String id);
}
