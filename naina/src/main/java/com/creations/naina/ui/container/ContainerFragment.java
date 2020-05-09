package com.creations.naina.ui.container;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.creations.condition.Preconditions;
import com.creations.mvvm.fragment.MVVMFragmentView;
import com.creations.naina.R;
import com.creations.naina.api.APICanvas;
import com.creations.naina.databinding.CardContainerBinding;
import com.creations.naina.models.CanvasP;
import com.creations.naina.ui.container.ContainerModule.ContainerSubcomponent.Builder;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

public class ContainerFragment extends MVVMFragmentView<ContainerContract.ViewModel,
        Builder, ContainerFragment> {

    @Inject
    @Nullable
    ContainerContract.ViewModel mViewModel;
    @Nullable
    private ContainerContract.InteractionListener mListener;
    @Inject
    APICanvas mApiCanvas;

    @NonNull
    public static ContainerFragment newInstance(CanvasP canvas, Builder builder) {
        Bundle args = new Bundle();
        ContainerFragment fragment = new ContainerFragment();
        fragment.setArguments(args);
        builder.canvas(canvas);
        fragment.mBuilder = builder;
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater,
                             @Nullable final ViewGroup container,
                             @Nullable final Bundle savedInstanceState) {
        Preconditions.requiresNonNull(inflater, "LayoutInflater");

        CardContainerBinding binding = DataBindingUtil.inflate(inflater,
                R.layout.card_container, container, false);
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
    public ContainerContract.ViewModel getViewModel() {
        return mViewModel;
    }

    @Override
    protected void configureObservations() {
        Preconditions.verifyNonNull(mViewModel, "ContainerViewModel");
        Preconditions.verifyNonNull(mRootView, "RootView");
        Preconditions.verifyNonNull(mListener, "ContainerInteractionListener");
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
                ContainerContract.InteractionListener.class, "ContextIsInteractionListener");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public ContainerFragment() {
        super(Builder.class, ContainerFragment.class);
    }
}
