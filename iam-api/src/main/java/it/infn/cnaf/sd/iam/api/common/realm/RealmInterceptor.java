package it.infn.cnaf.sd.iam.api.common.realm;

import static java.lang.String.format;
import static java.util.Objects.isNull;

import java.util.function.Supplier;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;

import it.infn.cnaf.sd.iam.api.common.error.BadRequestError;
import it.infn.cnaf.sd.iam.api.common.error.NotFoundError;
import it.infn.cnaf.sd.iam.persistence.repository.RealmRepository;

public class RealmInterceptor implements HandlerInterceptor {
  
  public static final String REALM_KEY = "iam.realm";

  private final RealmNameResolver resolver;
  private final RealmRepository repo;

  public RealmInterceptor(RealmNameResolver resolver, RealmRepository repo) {
    this.resolver = resolver;
    this.repo = repo;
  }

  public Supplier<NotFoundError> realmNotFound(String realm) {
    return () -> new NotFoundError(format("Unknown realm: %s", realm));
  }

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
      throws Exception {
    final String realm = resolver.resolveRealmName(request);
    if (isNull(realm)) {
      throw new BadRequestError("Unspecified realm");
    }
    repo.findByName(realm).orElseThrow(realmNotFound(realm));
    request.setAttribute(REALM_KEY, realm);
    RealmContext.setCurrentRealm(realm);
    return true;
  }

  @Override
  public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
      Object handler, Exception ex) throws Exception {
    RealmContext.clear();
  }

}
