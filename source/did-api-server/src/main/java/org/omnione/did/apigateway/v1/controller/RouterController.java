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
     * @param did The Decentralized Identifier (DID) to look up.
     * @return DidDocResDto containing the DID document.
     */
    @GetMapping(value = UrlConstant.GateWay.DID_DOC)
    @ResponseBody
    public DidDocResDto getDid(@RequestParam(name = "did") String did) {
        return storageService.findDidDocument(did);
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
}