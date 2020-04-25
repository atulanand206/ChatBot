package com.creations.inception.ui.form;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.creations.condition.Info;
import com.creations.condition.Preconditions;
import com.creations.inception.R;
import com.creations.inception.databinding.FragmentRequestBinding;
import com.creations.inception.ui.form.RequestModule.RequestSubcomponent.Builder;
import com.creations.inception.utils.InceptionSubComponentHelper;
import com.creations.mvvm.fragment.MVVMFragmentView;
import com.creations.mvvm.models.navigation.NavigationBarProps;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import static com.creations.condition.Helper.ARG_INFO;
import static com.creations.inception.utils.BundleHelper.ARG_NAVIGATION_PROPS;

public class RequestFragment extends MVVMFragmentView<RequestContract.ViewModel,
        Builder, RequestFragment> {

    @Nullable
    private View mRootView;
    @Inject
    @Nullable
    RequestContract.ViewModel mViewModel;
    @Nullable
    private RequestContract.InteractionListener mListener;

    @NonNull
    public static RequestFragment newInstance(@NonNull final Info info,
                                              @NonNull final NavigationBarProps props,
                                              @NonNull final Builder componentBuilder) {
        Preconditions.requiresNonNull(info, "Airspace");

        Bundle args = new Bundle();
        RequestFragment fragment = new RequestFragment();
        args.putSerializable(ARG_INFO, info);
        args.putSerializable(ARG_NAVIGATION_PROPS, props);
        fragment.setArguments(args);
        fragment.mBuilder = componentBuilder;
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle arguments = getArguments();
        Builder builder = getBuilder();

        InceptionSubComponentHelper.setInfo(arguments, builder);
        InceptionSubComponentHelper.setNavigationProps(arguments, builder);

    }

    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable final ViewGroup container,
                             @Nullable final Bundle savedInstanceState) {
        Preconditions.requiresNonNull(inflater, "LayoutInflater");

        // Inflate the layout for this fragment
        setHasOptionsMenu(true);

        FragmentRequestBinding binding = DataBindingUtil.inflate(inflater,
                R.layout.fragment_request, container, false);
        View view = binding.getRoot();
        mRootView = view;
        binding.setLifecycleOwner(getViewLifecycleOwner());

        super.onCreateView(inflater, container, savedInstanceState);

        binding.setViewmodel(mViewModel);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onCreateOptionsMenu(@NonNull final Menu menu, @NonNull final MenuInflater inflater) {
        Preconditions.requiresNonNull(menu, "Menu");
        Preconditions.requiresNonNull(inflater, "MenuInflater");

        menu.clear();

        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull final MenuItem item) {
        Preconditions.requiresNonNull(item, "MenuItem");
        Preconditions.verifyNonNull(mViewModel, "ProfileViewModel");
        Preconditions.verifyNonNull(mRootView, "RootView");
        Preconditions.verifyNonNull(mListener, "OnProfileInteractionListener");

        return super.onOptionsItemSelected(item);
    }

    @Nullable
    @Override
    public RequestContract.ViewModel getViewModel() {
        return mViewModel;
    }

    @Override
    protected void configureObservations() {
        Preconditions.verifyNonNull(mViewModel, "RequestViewModel");
        Preconditions.verifyNonNull(mListener, "RequestInteractionListener");

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
