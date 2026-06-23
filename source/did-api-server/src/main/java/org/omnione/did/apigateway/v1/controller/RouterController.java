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

package org.omnione.did.apigateway.v1.controller;

import org.omnione.did.apigateway.v1.dto.DidDocResDto;
import org.omnione.did.apigateway.v1.dto.VcMetaResDto;
import org.omnione.did.apigateway.v1.dto.ZkpCredDefResDto;
import org.omnione.did.apigateway.v1.dto.ZkpCredSchemaResDto;
import org.omnione.did.apigateway.v1.service.StorageService;
import org.omnione.did.base.constants.UrlConstant;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
 * Router Controller for handling API Gateway requests.
 * This controller manages routing for DID document and VC metadata retrieval.
 *
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(value = UrlConstant.GateWay.V1)
public class RouterController {

    private final StorageService storageService;

    /**
     * Retrieves a DID document for a given DID.
     *
     * <p>The standard DID key URL embeds the version as a query, e.g. {@code did:omn:foo?versionId=2}.
     * Since the leading {@code ?} collides with HTTP query parsing, {@code versionId} is exposed as a
     * separate, optional query parameter and recombined here into the standard form before lookup.
     *
     * @param did       The Decentralized Identifier (DID) to look up.
     * @param versionId The optional version of the DID document. When present, it is combined with
     *                  the DID as {@code <did>?versionId=<versionId>}; when absent, the DID is used alone.
     * @return DidDocResDto containing the DID document.
     */
    @GetMapping(value = UrlConstant.GateWay.DID_DOC)
    @ResponseBody
    public DidDocResDto getDid(@RequestParam(name = "did") String did,
                               @RequestParam(name = "versionId", required = false) Long versionId) {
        String didKeyUrl = (versionId != null) ? did + "?versionId=" + versionId : did;
        return storageService.findDidDocument(didKeyUrl);
    }

    /**
     * Retrieves metadata for a Verifiable Credential (VC).
     *
     * @param vcId The identifier of the Verifiable Credential.
     * @return VcMetaResDto containing the VC metadata.
     */
    @GetMapping(value = UrlConstant.GateWay.VC_META)
    @ResponseBody
    public VcMetaResDto getVcMetaData(@RequestParam(name = "vcId") String vcId) {
        return storageService.findVcMeta(vcId);
    }

    /**
     * Retrieves a Zero-Knowledge Proof (ZKP) credential schema by its identifier.
     *
     * @param id The identifier of the ZKP credential schema to retrieve.
     * @return ZkpCredSchemaResDto containing the ZKP credential schema.
     */
    @GetMapping(value = UrlConstant.GateWay.ZKP_CRED_SCHEMA)
    @ResponseBody
    public ZkpCredSchemaResDto getZkpCredSchema(@RequestParam(name = "id") String id) {
        return storageService.findZkpCredSchema(id);
    }

    /**
     * Retrieves a Zero-Knowledge Proof (ZKP) credential definition by its identifier.
     *
     * @param id The identifier of the ZKP credential definition to retrieve.
     * @return ZkpCredDefResDto containing the ZKP credential definition.
     */
    @GetMapping(value = UrlConstant.GateWay.ZKP_CRED_DEF)
    @ResponseBody
    public ZkpCredDefResDto getZkpCredDef(@RequestParam(name = "id") String id) {
        return storageService.findZkpCredDef(id);
    }
}