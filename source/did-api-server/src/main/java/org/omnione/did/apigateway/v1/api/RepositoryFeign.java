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

import org.omnione.did.apigateway.v1.api.dto.DidDocApiResDto;
import org.omnione.did.apigateway.v1.api.dto.VcMetaApiResDto;
import org.omnione.did.apigateway.v1.api.dto.ZkpCredDefResDto;
import org.omnione.did.apigateway.v1.api.dto.ZkpCredSchemaResDto;
import org.omnione.did.base.constants.UrlConstant;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Repository Feign
 */
@FeignClient(value = "Storage", url = "127.0.0.1:8097", path = "/repository/api/v1")
public interface RepositoryFeign {

    /**
     * Get DID Document
     *
     * @param did DID
     * @return DID Document API Response DTO
     */
    @GetMapping("/did-doc")
    DidDocApiResDto getDid(@RequestParam(name = "did") String did);

    /**
     * Get VC Meta Data
     *
     * @param vcId VC ID
     * @return VC Meta Data API Response DTO
     */
    @GetMapping("/vc-meta")
    VcMetaApiResDto getVcMetaData(@RequestParam(name = "vcId") String vcId);

    /**
     * Get ZKP Credential Schema
     *
     * @param id Schema ID
     * @return ZKP Credential Schema API Response DTO
     */
    @GetMapping("/zkp-cred-schema")
    ZkpCredSchemaResDto getZkpCredSchema(@RequestParam(name = "id") String id);

    /**
     * Get ZKP Credential Definition
     *
     * @param id Definition ID
     * @return ZKP Credential Definition API Response DTO
     */
    @GetMapping("/zkp-cred-def")
    ZkpCredDefResDto getZkpCredDef(@RequestParam(name = "id") String id);
}
