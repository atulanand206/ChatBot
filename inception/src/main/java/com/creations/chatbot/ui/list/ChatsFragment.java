package com.creations.chatbot.ui.list;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.creations.chatbot.R;
import com.creations.chatbot.data.ChatRepository;
import com.creations.chatbot.model.User;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import dagger.android.support.AndroidSupportInjection;

public class ChatsFragment extends Fragment implements ListContract.View, ListRecyclerAdapter.OnItemClickListener {

    private RecyclerView recyclerView;
    private ListRecyclerAdapter recyclerAdapter;
    private RecyclerView.LayoutManager layoutManager;

    private ListContract.Presenter presenter;

    private OnListInteractionListener mListener;

    @Inject
    ChatRepository repository;

    public ChatsFragment() {
        // Required empty public constructor
    }

    public static ChatsFragment newInstance() {
        ChatsFragment fragment = new ChatsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.chats);
        setHasOptionsMenu(true);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        presenter = new ListPresenter(this, repository);

        layoutManager = new LinearLayoutManager(getContext());
        recyclerAdapter = new ListRecyclerAdapter(presenter.getUsers(),getContext(),this);

        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(recyclerAdapter);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.start();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.menu_user,menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add_user:
                presenter.addUser();

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onAttach(Context context) {
        AndroidSupportInjection.inject(this);
        super.onAttach(context);
        if (context instanceof OnListInteractionListener) {
            mListener = (OnListInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onItemsLoaded() {
        recyclerAdapter.notifyDataSetChanged();
    }

    @Override
    public void onUserClicked(User user) {
        mListener.onUserClicked(user);
    }

    public interface OnListInteractionListener {

        void onUserClicked(User user);
    }
}
