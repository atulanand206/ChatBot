package com.creations.chatbot.di;

import com.creations.chatbot.ChatBotApplication;
import com.creations.chatbot.di.scopes.AppScope;
import com.creations.chatbot.network.NetworkModule;

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
