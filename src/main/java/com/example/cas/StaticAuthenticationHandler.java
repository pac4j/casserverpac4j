package com.example.cas;

import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.apache.commons.lang3.StringUtils;
import org.apereo.cas.authentication.AuthenticationHandlerExecutionResult;
import org.apereo.cas.authentication.PreventedException;
import org.apereo.cas.authentication.credential.UsernamePasswordCredential;
import org.apereo.cas.authentication.handler.support.AbstractUsernamePasswordAuthenticationHandler;
import org.apereo.cas.authentication.principal.PrincipalFactory;
import org.apereo.cas.services.ServicesManager;

import javax.security.auth.login.AccountNotFoundException;
import java.security.GeneralSecurityException;
import java.util.*;

@Slf4j
public class StaticAuthenticationHandler extends AbstractUsernamePasswordAuthenticationHandler {

    public StaticAuthenticationHandler(final ServicesManager servicesManager, final PrincipalFactory principalFactory) {
        super("FakeAuthenticationHandler", servicesManager, principalFactory, 1);
    }

    @Override
    protected AuthenticationHandlerExecutionResult authenticateUsernamePasswordInternal(final UsernamePasswordCredential credential,
                                                                                        final String originalPassword) throws GeneralSecurityException, PreventedException {
        val username = credential.getUsername();
        if (StringUtils.startsWith(username, "jleleu") || StringUtils.equals(username, originalPassword)) {
            LOGGER.info("@@@ Login: {}", username);
            val attributes = new HashMap<String, List<Object>>();
            attributes.put("firstname", Collections.singletonList("Jérôme"));
            attributes.put("lastname", Collections.singletonList("LELEU"));
            attributes.put("email", Collections.singletonList("jerome@casinthecloud.com"));
            val principal = principalFactory.createPrincipal(username, attributes);
            return createHandlerResult(credential, principal, new ArrayList<>());
        }

        throw new AccountNotFoundException("Not account found for: " + username);
    }
}
