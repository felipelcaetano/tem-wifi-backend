package br.com.temwifi.domains.auth.module;

import br.com.temwifi.domains.auth.entity.impl.DynamoDBUserEntity;
import br.com.temwifi.domains.auth.entity.interfaces.UserEntity;
import dagger.Module;
import dagger.Provides;

import javax.inject.Singleton;

@Module
public class AuthModule {

    @Provides
    @Singleton
    public static UserEntity provideUserEntity() {
        return new DynamoDBUserEntity();
    }
}
