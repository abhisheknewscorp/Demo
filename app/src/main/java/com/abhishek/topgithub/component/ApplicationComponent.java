
package com.abhishek.topgithub.component;

import com.abhishek.topgithub.activity.MainActivity;
import com.abhishek.topgithub.data.RepositoryModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {RepositoryModule.class})
public interface ApplicationComponent {
    void inject(MainActivity activity);
}
