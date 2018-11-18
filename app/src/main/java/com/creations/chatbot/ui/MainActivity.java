package com.creations.chatbot.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.creations.chatbot.R;
import com.creations.chatbot.data.ChatRepository;
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

    @Inject ChatRepository repository;

    private static MainActivity mInstance;

    private ChatFragment fragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        retrieveFragments();
        mInstance = this;
    }

    public static synchronized MainActivity getInstance() {
        return mInstance;
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
        fragment = ChatFragment.newInstance(user);

        fm.beginTransaction()
                .add(R.id.main_container, fragment)
                .addToBackStack("Chat for " + user.getUser())
                .commit();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle(user.getUser());
    }

    @Override
    public void onBackPressed() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        setTitle("ChatBot");
        super.onBackPressed();
    }

    @Override
    public boolean onSupportNavigateUp() {
        this.onBackPressed();
        return super.onSupportNavigateUp();
    }

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return fragmentDispatchingAndroidInjector;
    }

    public void notifyActiveChatScreen() {
        if(fragment!=null && fragment.getUserVisibleHint())
            fragment.onItemsLoaded();
    }
}
