package com.creations.naina.di;

import com.creations.naina.ui.MainActivity;
import com.example.dagger.scopes.ActivityScope;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityInjectionModule {

    @ActivityScope
    @ContributesAndroidInjector()
    public abstract MainActivity contributeMainActivityInjector();

}
