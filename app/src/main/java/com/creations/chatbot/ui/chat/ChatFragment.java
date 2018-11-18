package com.creations.chatbot.ui.chat;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.creations.chatbot.R;
import com.creations.chatbot.data.ChatRepository;
import com.creations.chatbot.model.User;
import com.creations.chatbot.utils.ViewUtils;

import javax.inject.Inject;

import dagger.android.support.AndroidSupportInjection;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ChatFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ChatFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ChatFragment extends Fragment implements ChatContract.View,
        View.OnClickListener, TextView.OnEditorActionListener {
    private static final String ARG_USER = "user";

    private User user;

    private EditText entryView;

    private ImageButton sendButton;

    private RecyclerView recyclerView;
    private ChatRecyclerAdapter recyclerAdapter;
    private RecyclerView.LayoutManager layoutManager;

    private ChatContract.Presenter presenter;

    @Inject
    ChatRepository chatRepository;

    private OnFragmentInteractionListener mListener;

    public ChatFragment() {
        // Required empty public constructor
    }

    public static ChatFragment newInstance(User user) {
        ChatFragment fragment = new ChatFragment();
        Bundle args = new Bundle();
        args.putString(ARG_USER, user.getUser());
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            String userName =  getArguments().getString(ARG_USER);
            user = chatRepository.getUser(userName);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_chat, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        entryView = view.findViewById(R.id.form_entry);
        sendButton = view.findViewById(R.id.send);
        recyclerView = view.findViewById(R.id.messages);
        setHasOptionsMenu(true);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        presenter = new ChatPresenter(this, chatRepository, user);

        layoutManager = new LinearLayoutManager(getContext());
        recyclerAdapter = new ChatRecyclerAdapter(presenter.getItems(),getContext());

        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(recyclerAdapter);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        sendButton.setOnClickListener(this);
        entryView.setOnEditorActionListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.start();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onAttach(Context context) {
        AndroidSupportInjection.inject(this);
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
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
        InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (ViewUtils.isKeyboardShown(view.getRootView())) {
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
    }
}
