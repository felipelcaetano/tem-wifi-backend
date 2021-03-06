package br.com.temwifi.domains.auth.component;

import br.com.temwifi.domains.auth.module.AuthModule;
import br.com.temwifi.domains.auth.service.*;
import dagger.Component;

import javax.inject.Singleton;

@Singleton
@Component(modules = AuthModule.class)
public interface AuthComponent {

    CreateUserService buildCreateUserService();

    ReadUserService buildReadUserService();

    LoginService buildLoginService();

    ValidateTokenService buildValidateTokenService();

    CreateUserOrchestration buildCreateUserOrchestration();
}
