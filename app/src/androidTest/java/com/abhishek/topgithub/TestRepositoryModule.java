package com.abhishek.topgithub;

import com.abhishek.topgithub.data.RepositoryModule;

import dagger.Module;

@Module
public class TestRepositoryModule extends RepositoryModule {

    public TestRepositoryModule() {
        super();
    }

    @Override
    public String getDomainUrl() {
        return "http://127.0.0.1:8080/";
    }
}
