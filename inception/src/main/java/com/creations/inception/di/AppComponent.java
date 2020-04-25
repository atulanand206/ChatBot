package com.creations.inception.di;

import com.creations.inception.ChatBotApplication;
import com.creations.inception.network.NetworkModule;
import com.example.dagger.scopes.AppScope;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.support.AndroidSupportInjectionModule;


@AppScope
@Component(modules = {
        AndroidSupportInjectionModule.class,
        ActivityInjectionModule.class,
        FragmentInjectionModule.class,
        APIInjectionModule.class,
        RepositoryModule.class,
        ViewModelModule.class,
        MessageModule.class,
        NetworkModule.class,
        AppModule.class
})
public interface AppComponent {

    void inject(ChatBotApplication application);

    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder application(ChatBotApplication application);

        AppComponent build();
    }
}


