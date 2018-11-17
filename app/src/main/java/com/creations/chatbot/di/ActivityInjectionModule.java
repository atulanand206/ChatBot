package com.creations.chatbot.di;

import com.creations.chatbot.di.scopes.ActivityScope;
import com.creations.chatbot.ui.MainActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityInjectionModule {

    @ActivityScope
    @ContributesAndroidInjector()
    public abstract MainActivity contributeMainActivityInjector();

}
