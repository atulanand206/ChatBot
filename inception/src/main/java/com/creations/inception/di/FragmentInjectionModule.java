package com.creations.inception.di;

import com.creations.inception.ui.chat.ChatFragment;
import com.creations.inception.ui.form.RequestModule;
import com.creations.inception.ui.list.ChatsFragment;
import com.example.dagger.scopes.FragmentScope;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module(includes = {
        RequestModule.class
})
public abstract class FragmentInjectionModule {

    @FragmentScope
    @ContributesAndroidInjector()
    public abstract ChatFragment provideChatFragmentInjector();

    @FragmentScope
    @ContributesAndroidInjector()
    public abstract ChatsFragment provideChatsFragmentInjector();
}
