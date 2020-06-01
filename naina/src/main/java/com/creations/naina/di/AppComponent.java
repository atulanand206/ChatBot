package com.creations.naina.di;

import com.creations.bang.di.PropsModule;
import com.creations.naina.App;
import com.creations.naina.ui.container.ContainerModule;
import com.example.dagger.scopes.AppScope;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.support.AndroidSupportInjectionModule;


@AppScope
@Component(modules = {
        AndroidSupportInjectionModule.class,
        AndroidInjectionModule.class,
        ActivityInjectionModule.class,
        NetworkModule.class,
        AppModule.class,
        PropsModule.class,
        ContainerModule.class
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


