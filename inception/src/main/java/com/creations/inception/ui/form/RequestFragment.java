package com.creations.inception.ui.form;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.creations.condition.Preconditions;
import com.creations.inception.R;
import com.creations.inception.databinding.FragmentRequestBinding;
import com.creations.inception.ui.form.RequestModule.RequestSubcomponent.Builder;
import com.creations.mvvm.fragment.MVVMFragmentView;

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
        mViewModel.getNavigation().getStatusBarColorEvent().listen(getViewLifecycleOwner(), mListener::setStatusBarColr);
        mViewModel.getBlogger().getAnimation().listen(getViewLifecycleOwner(), super::crossfade);
        mViewModel.getDrawer().getOpenDrawerEvent().listen(getViewLifecycleOwner(), super::openDrawer);
        mViewModel.getDrawer().getCloseDrawerEvent().listen(getViewLifecycleOwner(), super::closeDrawer);
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
