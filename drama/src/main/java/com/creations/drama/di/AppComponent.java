package com.creations.drama.di;

import com.creations.drama.App;
import com.creations.drama.ui.container.ContainerModule;
import com.example.dagger.scopes.AppScope;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.support.AndroidSupportInjectionModule;


@AppScope
@Component(modules = {
        AndroidSupportInjectionModule.class,
        AndroidInjectionModule.class,
        ContainerModule.class,
        NetworkModule.class
})
public interface AppComponent {

    void inject(App application);

    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder application(App application);

        AppComponent build();
    }
}


