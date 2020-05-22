package com.creations.bang.di;

import com.creations.bang.models.Bang;
import com.example.dagger.scopes.AppScope;

import dagger.Module;
import dagger.Provides;

@Module
public class BangModule {

    @AppScope @Provides
    public static Bang prvdBang() {
        return new Bang();
    }
}
