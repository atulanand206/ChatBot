package com.creations.chatbot.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.creations.chatbot.R;
import com.creations.chatbot.model.User;
import com.creations.chatbot.ui.chat.ChatFragment;
import com.creations.chatbot.ui.list.ChatsFragment;

import javax.inject.Inject;

import dagger.android.AndroidInjection;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;

public class MainActivity extends AppCompatActivity implements HasSupportFragmentInjector,
        ChatFragment.OnFragmentInteractionListener, ChatsFragment.OnListInteractionListener {

    @Inject DispatchingAndroidInjector<Fragment> fragmentDispatchingAndroidInjector;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        retrieveFragments();
    }

    private void retrieveFragments() {

        FragmentManager fm = getSupportFragmentManager();
        ChatsFragment fragment = (ChatsFragment) fm.findFragmentById(R.id.main_container);

        if(fragment == null) {
            fragment = ChatsFragment.newInstance();
            fm.beginTransaction()
                    .add(R.id.main_container, fragment)
                    .commit();
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onUserClicked(User user) {
        FragmentManager fm = getSupportFragmentManager();

        ChatFragment fragment = ChatFragment.newInstance(user);

        fm.beginTransaction()
                .add(R.id.main_container, fragment)
                .addToBackStack("Chat for " + user.getUser())
                .commit();
    }

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return fragmentDispatchingAndroidInjector;
    }
}
