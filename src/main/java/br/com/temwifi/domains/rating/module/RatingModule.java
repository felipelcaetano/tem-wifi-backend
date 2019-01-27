package br.com.temwifi.domains.rating.module;

import br.com.temwifi.domains.rating.entity.impl.DynamoDBRatingEntity;
import br.com.temwifi.domains.rating.entity.interfaces.RatingEntity;
import dagger.Module;
import dagger.Provides;

import javax.inject.Singleton;

@Module
public class RatingModule {

    @Provides
    @Singleton
    public static RatingEntity provideUserEntity() {
        return new DynamoDBRatingEntity();
    }
}
