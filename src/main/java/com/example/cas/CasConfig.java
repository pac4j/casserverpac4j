package com.example.cas;

import org.apereo.cas.authentication.AuthenticationEventExecutionPlanConfigurer;
import org.apereo.cas.authentication.principal.PrincipalFactory;
import org.apereo.cas.services.ServicesManager;
import org.apereo.cas.web.CasWebSecurityConfigurer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CasConfig {

    @Bean
    public StaticAuthenticationHandler fakeAuthenticationHandler(@Qualifier("servicesManager")
                                                               final ServicesManager servicesManager,
                                                                 @Qualifier("principalFactory")
                                                               final PrincipalFactory principalFactory) {
        return new StaticAuthenticationHandler(servicesManager, principalFactory);
    }

    @Bean
    public AuthenticationEventExecutionPlanConfigurer registerInternalHandler(final StaticAuthenticationHandler fakeAuthenticationHandler) {
        return plan -> plan.registerAuthenticationHandler(fakeAuthenticationHandler);
    }

    @Bean
    public CasWebSecurityConfigurer customCasWebSecurityConfigurer() {
        return new CustomCasWebSecurityConfigurer();
    }
}
