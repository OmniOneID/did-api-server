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

import org.omnione.did.base.property.LSSProperty;
import feign.RequestInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class for Repository Feign client.
 * This configuration provides the request interceptor that adds X-API-Key header
 * to all Repository service requests.
 * API key is read from application properties (lss.api-key) since this server has no database.
 */
@Configuration
@RequiredArgsConstructor
public class RepositoryFeignConfig {

    private final LSSProperty lssProperty;

    /**
     * Creates a request interceptor for Repository Feign client.
     * This interceptor adds X-API-Key header using the API key from application properties.
     *
     * @return RequestInterceptor for Repository Feign client
     */
    @Bean
    public RequestInterceptor repositoryRequestInterceptor() {
        return new RepositoryRequestInterceptor(lssProperty);
    }
}
