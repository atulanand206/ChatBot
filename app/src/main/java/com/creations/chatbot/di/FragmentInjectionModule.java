package com.creations.chatbot.di;

import com.creations.chatbot.di.scopes.FragmentScope;
import com.creations.chatbot.ui.chat.ChatFragment;
import com.creations.chatbot.ui.list.ChatsFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class FragmentInjectionModule {

    @FragmentScope
    @ContributesAndroidInjector()
    public abstract ChatFragment provideChatFragmentInjector();

    @FragmentScope
    @ContributesAndroidInjector()
    public abstract ChatsFragment provideChatsFragmentInjector();
}
