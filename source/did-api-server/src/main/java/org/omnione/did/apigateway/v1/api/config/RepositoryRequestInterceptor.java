/*
 * Copyright 2025 OmniOne.
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
package org.omnione.did.apigateway.v1.api.config;

import org.omnione.did.base.exception.ErrorCode;
import org.omnione.did.base.exception.OpenDidException;
import org.omnione.did.base.property.LSSProperty;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Request interceptor for Repository Feign client to add X-API-Key header.
 * This interceptor adds the API key from application properties (lss.api-key)
 * to all requests made by RepositoryFeign.
 */
@RequiredArgsConstructor
@Slf4j
public class RepositoryRequestInterceptor implements RequestInterceptor {

    private static final String API_KEY_HEADER = "X-API-Key";

    private final LSSProperty lssProperty;

    @Override
    public void apply(RequestTemplate template) {
        try {
            log.debug("Adding X-API-Key header to Repository request: {}", template.url());

            String apiKey = lssProperty.getApiKey();

            if (apiKey == null || apiKey.trim().isEmpty()) {
                log.error("LSS API key is not configured (lss.api-key)");
                throw new OpenDidException(ErrorCode.SERVER_CONFIG_KEY_NOT_FOUND);
            }

            template.header(API_KEY_HEADER, apiKey);

            log.debug("Successfully added X-API-Key header to Repository request");

        } catch (OpenDidException e) {
            log.error("Failed to add X-API-Key header to Repository request", e);
            throw e;
        }
    }
}
