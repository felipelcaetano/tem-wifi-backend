package br.com.temwifi.domains.locations.module;

import br.com.temwifi.domains.locations.entity.impl.DynamoDBLocationEntity;
import br.com.temwifi.domains.locations.entity.interfaces.LocationEntity;
import dagger.Module;
import dagger.Provides;

import javax.inject.Singleton;

@Module
public class LocationModule {

    @Provides
    @Singleton
    public static LocationEntity provideUserEntity() {
        return new DynamoDBLocationEntity();
    }
}
