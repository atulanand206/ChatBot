package com.creations.chatbot.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.creations.chatbot.R;
import com.creations.chatbot.network.IAPIChat;
import com.creations.chatbot.utils.ViewUtils;

import javax.inject.Inject;

import dagger.android.AndroidInjection;

public class MainActivity extends AppCompatActivity implements MainContract.View,
        View.OnClickListener, TextView.OnEditorActionListener {

    private EditText entryView;

    private ImageButton sendButton;

    private RecyclerView recyclerView;
    private ChatRecyclerAdapter recyclerAdapter;
    private RecyclerView.LayoutManager layoutManager;

    private MainContract.Presenter presenter;

    @Inject IAPIChat apiChat;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        presenter = new MainPresenter(this);

        entryView = findViewById(R.id.form_entry);
        sendButton = findViewById(R.id.send);
        recyclerView = findViewById(R.id.messages);

        layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerAdapter = new ChatRecyclerAdapter(presenter.getItems(),getApplicationContext());

        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(recyclerAdapter);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        sendButton.setOnClickListener(this);
        entryView.setOnEditorActionListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.start();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.send:
                String newEntry = entryView.getText().toString();
                entryView.setText(null);
                presenter.onSendClicked(newEntry);
                hideKeyboard(entryView);
        }
    }

    @Override
    public boolean onEditorAction(TextView exampleView, int actionId, KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            String newEntry = entryView.getText().toString();
            entryView.setText(null);
            presenter.onSendClicked(newEntry);
            hideKeyboard(entryView);
        }
        return true;
    }

    @Override
    public void onItemsLoaded() {
        recyclerAdapter.notifyDataSetChanged();
    }

    private void hideKeyboard(View view) {
        InputMethodManager imm = (InputMethodManager) getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (ViewUtils.isKeyboardShown(view.getRootView())) {
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}
