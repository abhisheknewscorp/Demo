
package com.abhishek.topgithub.data;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class RepositoryModule {


    private static final String DOMAIN_URL = "https://github-trending-api.now.sh";

    @Provides
    @Singleton
    Retrofit provideRetrofit() {
        return new Retrofit.Builder()
                .baseUrl(getDomainUrl())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
    }

    @Provides
    @Singleton
    @SuppressWarnings("unused")
    public RepoApi providesRepoApi(Retrofit retrofit) {
        return retrofit.create(RepoApi.class);
    }

    @Provides
    @Singleton
    @SuppressWarnings("unused")
    public RepoService providesRepoService(RepoApi networkService) {
        return new RepoService(networkService);
    }

    public String getDomainUrl() {
        return DOMAIN_URL;
    }
}
