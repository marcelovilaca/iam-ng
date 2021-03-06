/**
 * Copyright (c) Istituto Nazionale di Fisica Nucleare (INFN). 2016-2020
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package it.infn.cnaf.sd.iam.api.common.realm;

import static java.lang.String.format;
import static java.util.Objects.isNull;

import java.util.function.Supplier;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;

import it.infn.cnaf.sd.iam.api.common.error.InvalidRequestError;
import it.infn.cnaf.sd.iam.api.common.error.NotFoundError;
import it.infn.cnaf.sd.iam.persistence.repository.RealmRepository;

public class RealmInterceptor implements HandlerInterceptor {

  public static final String API_PATH = "/Realms/";

  private final RealmNameResolver resolver;
  private final RealmRepository repo;

  public RealmInterceptor(RealmNameResolver resolver, RealmRepository repo) {
    this.resolver = resolver;
    this.repo = repo;
  }

  public Supplier<NotFoundError> realmNotFound(String realm) {
    return () -> new NotFoundError(format("Unknown realm: %s", realm));
  }

  protected boolean isApiRequest(HttpServletRequest request) {
    return request.getRequestURI().startsWith(API_PATH);
  }


  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
      throws Exception {

    if (!isApiRequest(request)) {
      return true;
    }
    
    final String realm = resolver.resolveRealmName(request);
    if (isNull(realm)) {
      throw new InvalidRequestError("Unspecified realm");
    }
    RealmContext.setCurrentRealmEntity(repo.findByName(realm).orElseThrow(realmNotFound(realm)));
    return true;
  }

  @Override
  public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
      Object handler, Exception ex) throws Exception {
    RealmContext.clear();
  }

}
