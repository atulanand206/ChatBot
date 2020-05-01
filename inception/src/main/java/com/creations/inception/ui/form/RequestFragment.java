package com.creations.inception.ui.form;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.creations.condition.Preconditions;
import com.creations.inception.R;
import com.creations.inception.databinding.FragmentRequestBinding;
import com.creations.inception.ui.form.RequestModule.RequestSubcomponent.Builder;
import com.creations.mvvm.fragment.MVVMFragmentView;
import com.creations.mvvm.ui.blocks.board.BoardViewModel;
import com.example.application.messages.MessageType;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

public class RequestFragment extends MVVMFragmentView<RequestContract.ViewModel,
        Builder, RequestFragment> {

    @Inject
    @Nullable
    RequestContract.ViewModel mViewModel;
    @Nullable
    private RequestContract.InteractionListener mListener;

    @NonNull
    public static RequestFragment newInstance() {
        Bundle args = new Bundle();
        RequestFragment fragment = new RequestFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable final ViewGroup container,
                             @Nullable final Bundle savedInstanceState) {
        Preconditions.requiresNonNull(inflater, "LayoutInflater");

        FragmentRequestBinding binding = DataBindingUtil.inflate(inflater,
                R.layout.fragment_request, container, false);
        View view = binding.getRoot();
        mRootView = view;
        binding.setLifecycleOwner(getViewLifecycleOwner());

        super.onCreateView(inflater, container, savedInstanceState);

        binding.setViewmodel(mViewModel);

        hideNavigation(mRootView);

        return view;
    }

    @Nullable
    @Override
    public RequestContract.ViewModel getViewModel() {
        return mViewModel;
    }

    @Override
    protected void configureObservations() {
        Preconditions.verifyNonNull(mViewModel, "RequestViewModel");
        Preconditions.verifyNonNull(mRootView, "RootView");
        Preconditions.verifyNonNull(mListener, "RequestInteractionListener");
        ((BoardViewModel) mViewModel.getBoard().getBoardViewModel()).getStatusBarColorEvent().listen(getViewLifecycleOwner(), mListener::setStatusBarColr);
        mViewModel.getBoard().getAnimation().listen(getViewLifecycleOwner(), super::crossfade);
        mViewModel.getBoard().getCloseKeyboardEvent().listen(getViewLifecycleOwner(), () -> hideKeyboard(mRootView));
        mViewModel.getBoard().getAddViewModel().getHideNavigationEvent().listen(getViewLifecycleOwner(), () -> hideNavigation(mRootView));
        mViewModel.getBoard().getBoardViewModel().getWordViewModel().getToastEvent().listen(getViewLifecycleOwner(), (x) -> showToast(x, MessageType.SUCCESS, Toast.LENGTH_SHORT));
        ((BoardViewModel) mViewModel.getBoard().getBoardViewModel()).getToastEvent().listen(getViewLifecycleOwner(), (x) -> showToast(x, MessageType.SUCCESS, Toast.LENGTH_SHORT));
        mViewModel.getBoard().getDoneViewModel().getToastEvent().listen(getViewLifecycleOwner(), (x) -> showToast(x, MessageType.SUCCESS, Toast.LENGTH_SHORT));
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mRootView != null)
            hideNavigation(mRootView);
    }

    @Override
    public void onAttach(@NonNull final Context context) {
        super.onAttach(context);
        mListener = Preconditions.verifyInstanceOf(context,
                RequestContract.InteractionListener.class, "ContextIsInteractionListener");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public RequestFragment() {
        super(Builder.class, RequestFragment.class);
    }
}
