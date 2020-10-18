package com.creations.naina.ui.container;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.creations.bang.api.IAPIBang;
import com.creations.condition.Preconditions;
import com.creations.mvvm.fragment.MVVMFragmentView;
import com.creations.naina.R;
import com.creations.naina.databinding.CardContainerBinding;
import com.creations.naina.models.CanvasP;
import com.creations.naina.ui.container.ContainerModule.ContainerSubcomponent.Builder;
import com.example.application.utils.expandablelayout.ExpandableLayout;

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
    IAPIBang mApiCanvas;

    private ExpandableLayout mEntityExpandableLayout;
    private ExpandableLayout mBankExpandableLayout;
    private ExpandableLayout mRateExpandableLayout;
    private ExpandableLayout mClientExpandableLayout;

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
        mEntityExpandableLayout = view.findViewById(R.id.entity_expandable);
        mBankExpandableLayout = view.findViewById(R.id.bank_expandable);
        mRateExpandableLayout = view.findViewById(R.id.rate_expandable);
        mClientExpandableLayout = view.findViewById(R.id.client_expandable);
        binding.setLifecycleOwner(getViewLifecycleOwner());

        super.onCreateView(inflater, container, savedInstanceState);

        binding.setViewmodel(mViewModel);

        hideNavigation(mRootView);
        if (getActivity() != null)
            setStatusBarColor(getActivity(), R.color.black);
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
        mViewModel.getUploadEvent().listen(getViewLifecycleOwner(), (text) -> mListener.onUploadEventClicked((Integer) text));
        mViewModel.getDocumentEvent().listen(getViewLifecycleOwner(), (text) -> mListener.onDocumentEventClicked(text.toString()));
        mViewModel.getSaveConfigEvent().listen(getViewLifecycleOwner(), (text) -> mListener.onSaveConfig(text.toString()));
        mViewModel.getLoadConfigEvent().listen(getViewLifecycleOwner(), (text) -> mListener.onLoadConfig(text.toString()));
        mViewModel.getEntityExpandEvent().listen(getViewLifecycleOwner(), () -> expandEntity(mEntityExpandableLayout));
        mViewModel.getBankExpandEvent().listen(getViewLifecycleOwner(), () -> expandEntity(mBankExpandableLayout));
        mViewModel.getRateExpandEvent().listen(getViewLifecycleOwner(), () -> expandEntity(mRateExpandableLayout));
        mViewModel.getClientExpandEvent().listen(getViewLifecycleOwner(), () -> expandEntity(mClientExpandableLayout));
    }

    private void expandEntity(ExpandableLayout expandableLayout) {
        if (expandableLayout.isExpanded())
            expandableLayout.collapse();
        else
            expandableLayout.expand();
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

    public void setFileName(String uri) {
        Preconditions.verifyNonNull(mViewModel, "ContainerViewModel");
        mViewModel.setFileName(uri);
    }

    public void loadMainLayout() {
        Preconditions.verifyNonNull(mViewModel, "ContainerViewModel");
        mViewModel.toggleVisibility(true);
    }
}
