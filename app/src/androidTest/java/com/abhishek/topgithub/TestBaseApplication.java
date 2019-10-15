package com.abhishek.topgithub;

import com.abhishek.topgithub.base.BaseApplication;
import com.abhishek.topgithub.component.ApplicationComponent;
import com.abhishek.topgithub.component.DaggerApplicationComponent;

public class TestBaseApplication extends BaseApplication {

    @Override
    public ApplicationComponent getAppComponent() {

        return DaggerApplicationComponent.builder()
                .repositoryModule(new TestRepositoryModule())
                .build();
    }
}