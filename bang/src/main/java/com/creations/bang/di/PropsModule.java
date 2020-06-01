package com.creations.bang.di;

import com.creations.bang.models.Bang;
import com.creations.bang.models.Card;
import com.example.dagger.scopes.AppScope;

import dagger.Module;
import dagger.Provides;

@Module
public class PropsModule {

    @AppScope @Provides
    public static Bang prvdBang() {
        return new Bang();
    }

    @AppScope @Provides
    public static Card prvdCard() {
        return new Card();
    }
}
