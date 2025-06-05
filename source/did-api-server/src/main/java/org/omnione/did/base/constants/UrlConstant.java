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

package org.omnione.did.base.constants;

public class UrlConstant {

    public static class GateWay {
        public static final String V1 = "/api-gateway/api/v1";
        public static final String DID_DOC = "/did-doc";
        public static final String VC_META = "/vc-meta";
        public static final String ZKP_CRED_SCHEMA = "/zkp-cred-schema";
        public static final String ZKP_CRED_DEF = "/zkp-cred-def";

    }

    public static class LLS {
        public static final String V1 = "/lls/api/v1";
        public static final String DID = "/did-doc";
        public static final String VC_META = "/vc-meta";
        public static final String CREDENTIAL_SCHEMA = "/credential-schema";
        public static final String CREDENTIAL_DEFINITION = "/credential-definition";


    }
}
