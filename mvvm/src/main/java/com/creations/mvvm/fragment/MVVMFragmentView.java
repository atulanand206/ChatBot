package com.creations.mvvm.fragment;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.creations.condition.Preconditions;
import com.creations.mvvm.viewmodel.IMVVMViewModel;
import com.example.application.base.BaseContract;
import com.example.application.base.BaseFragment;
import com.example.application.utils.MVVMInjector;

import java.lang.ref.WeakReference;

import androidx.annotation.CallSuper;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import dagger.android.AndroidInjector;

/**
 * A helper class to automate some of working with an {@link IMVVMViewModel}.
 *
 * Override {@link MVVMFragmentView#configureObservations()} to observe or listen to {@link androidx.lifecycle.LiveData}
 * from the ViewModel. Call getBuilder to access the Builder instance. The
 * mContext field holds a {@link WeakReference} to the {@link Context}.
 *
 * @param <VM> the IMVVMViewModel sub-interface to work with.
 * @param <B> the {@link dagger.Subcomponent.Builder} to use to inject the Fragment.
 * @param <V> the subclass being implemented.
 */
public abstract class MVVMFragmentView<VM extends IMVVMViewModel,
        B extends AndroidInjector.Builder<V>, V extends MVVMFragmentView<VM, B, V>>
        extends BaseFragment implements BaseContract.BaseView {

    private static final String TAG = MVVMFragmentView.class.getSimpleName();

    @NonNull
    private final Class<B> mBKlass;
    @NonNull
    private final Class<V> mVKlass;
    @Nullable
    protected B mBuilder;

    @Nullable
    public abstract VM getViewModel();

    @NonNull
    protected WeakReference<Context> mContext = new WeakReference<>(null);

    protected MVVMFragmentView(@NonNull final Class<B> bKlass, @NonNull final Class<V> vKlass) {
        mBKlass = Preconditions.requiresNonNull(bKlass, "ClassOfB");
        mVKlass = Preconditions.requiresNonNull(vKlass, "ClassOfV");
    }

    @CallSuper
    @Override
    public void onAttach(@NonNull final Context context) {
        Preconditions.requiresNonNull(context, "Context");

        mContext = new WeakReference<>(context);

        super.onAttach(context);
    }

    @CallSuper
    @Override
    public void onDetach() {
        super.onDetach();

        mContext.clear();
    }

    @CallSuper
    @Override
    public void onActivityCreated(@Nullable final Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        getBuilder();
    }

    @CallSuper
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater,
                             @Nullable final ViewGroup container,
                             @Nullable final Bundle savedInstanceState) {
        Preconditions.requiresNonNull(inflater, "LayoutInflater");

        //noinspection unchecked
        getBuilder().create((V)this).inject((V)this);

        VM viewModel = Preconditions.verifyNonNull(getViewModel(), "GetViewModel");
        viewModel.getContextCallback().listen(getViewLifecycleOwner(), contextCallback -> {
            Preconditions.requiresNonNull(contextCallback, "ContextCallback");
            Context context = getContext();
            if(context != null) {
                contextCallback.call(context);
            } else {
                Log.w(TAG, "Did not have a Context to provide to the view model");
            }
        });
        configureObservations();

        return null;
    }

    abstract protected void configureObservations();

    @NonNull
    protected B getBuilder() {
        if (mBuilder == null) {
            MVVMInjector mainActivity = Preconditions.verifyInstanceOf(getActivity(), MVVMInjector.class, "GetActivityIsMainActivity");
            mBuilder = mainActivity.getBuilder(mVKlass, mBKlass);
        }
        return mBuilder;
    }

}
