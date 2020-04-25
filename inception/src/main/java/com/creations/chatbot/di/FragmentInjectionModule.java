package com.creations.chatbot.di;

import com.creations.chatbot.ui.chat.ChatFragment;
import com.creations.chatbot.ui.list.ChatsFragment;
import com.example.dagger.scopes.FragmentScope;

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
