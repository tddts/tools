/*
 * Copyright 2017 Tigran Dadaiants
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.github.tddts.tools.web.oauth;

/**
 * OAuth 2.0 authorization grant types.
 *
 * @author Tigran_Dadaiants dtkcommon@gmail.com
 * @see <a href="https://tools.ietf.org/html/rfc6749#section-1.3">RFC 6749, Section 1.3</a>
 */
public enum OAuthGrantType {

  /**
   * OAuth 2.0 authorization grant type - Authorization code.
   */
  AUTHORIZATION_CODE,

  /**
   * OAuth 2.0 authorization grant type - Implicit.
   */
  IMPLICIT,

  /**
   * OAuth 2.0 authorization grant type - Resource owner password credentials.
   */
  RO_PASSWORD_CREDENTIALS,

  /**
   * OAuth 2.0 authorization grant type - Client credentials.
   */
  CLIENT_CREDENTIALS

}
