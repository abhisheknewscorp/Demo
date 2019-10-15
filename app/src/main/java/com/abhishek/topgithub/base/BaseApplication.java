package com.abhishek.topgithub.base;

import android.app.Application;

import com.abhishek.topgithub.component.ApplicationComponent;
import com.abhishek.topgithub.component.DaggerApplicationComponent;

public class BaseApplication extends Application {

    ApplicationComponent applicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        getAppComponent();
    }

    public ApplicationComponent getAppComponent() {
        if (applicationComponent == null) {
            applicationComponent = DaggerApplicationComponent.builder().build();
        }
        return applicationComponent;
    }
}
