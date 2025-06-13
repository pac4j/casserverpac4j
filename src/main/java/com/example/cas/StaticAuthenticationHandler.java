package com.example.cas;

import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.apache.commons.lang3.StringUtils;
import org.apereo.cas.authentication.AuthenticationHandlerExecutionResult;
import org.apereo.cas.authentication.credential.UsernamePasswordCredential;
import org.apereo.cas.authentication.handler.support.AbstractUsernamePasswordAuthenticationHandler;
import org.apereo.cas.authentication.principal.PrincipalFactory;
import org.apereo.cas.services.ServicesManager;

import javax.security.auth.login.AccountNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

@Slf4j
public class StaticAuthenticationHandler extends AbstractUsernamePasswordAuthenticationHandler {

    public StaticAuthenticationHandler(final ServicesManager servicesManager, final PrincipalFactory principalFactory) {
        super("FakeAuthenticationHandler", servicesManager, principalFactory, 1);
    }

    @Override
    protected AuthenticationHandlerExecutionResult authenticateUsernamePasswordInternal(final UsernamePasswordCredential credential,
                                                                                        final String originalPassword) throws Throwable {
        val username = credential.getUsername();
        if (StringUtils.startsWith(username, "jleleu") || StringUtils.startsWith(username, "leleuj")
            || StringUtils.equals(username, originalPassword)
            || "password".equals(originalPassword) || "pwd".equals(originalPassword)) {

            LOGGER.info("@@@ Login: {}", username);
            val attributes = new HashMap<String, List<Object>>();
            attributes.put("firstname", Collections.singletonList("Jérôme"));
            attributes.put("lastname", Collections.singletonList("LELEU"));
            if (username.startsWith("sub")) {
                attributes.put("sub", Collections.singletonList(username.substring(3)));
            }
            if (username.contains("@")) {
                attributes.put("email", Collections.singletonList(username));
            } else {
                attributes.put("email", Collections.singletonList("jerome@casinthecloud.com"));
            }
            if ("subguillaume".equals(username)) {
                attributes.put("family_name", Collections.singletonList("dupond"));
                attributes.put("given_name", Collections.singletonList("guillaume"));
                attributes.put("birthdate", Collections.singletonList("1979-06-08"));
                attributes.put("gender", Collections.singletonList("male"));
                attributes.put("birthplace", Collections.singletonList("34172"));
                attributes.put("birthcountry", Collections.singletonList("99139"));
                attributes.put("email", Collections.singletonList("gdupond@test.org"));
            }
            val principal = principalFactory.createPrincipal(username, attributes);
            return createHandlerResult(credential, principal, new ArrayList<>());
        }

        throw new AccountNotFoundException("Not account found for: " + username);
    }
}
