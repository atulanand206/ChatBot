package com.creations.mvvm.viewmodel;

import android.app.Application;
import android.content.Intent;
import android.net.Uri;

import com.creations.condition.Preconditions;
import com.creations.mvvm.live.LiveEvent;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

/**
 * Base class implementation for {@link IMVVMViewModel}'s. Allows getting a Context
 * for a callback and has a helper class for implementing {@link Factory}'s for ViewModel.
 */
public abstract class MVVMViewModel extends AndroidViewModel implements IMVVMViewModel {

    @NonNull
    protected final Application mApplication;

    protected MVVMViewModel(@NonNull final Application application) {
        super(application);
        mApplication = Preconditions.requiresNonNull(application, "Application");
    }

    @NonNull
    protected final LiveEvent.Mutable<ContextCallback> mContextCallback = new LiveEvent.Mutable<>();

    @NonNull
    public LiveEvent<ContextCallback> getContextCallback() {
        return mContextCallback;
    }

    @Override
    public void startIntentWithPhoneNumber(@NonNull final String phoneNumber) {
        Preconditions.requiresNonNull(phoneNumber, "PhoneNumber");
        Intent dialerActivity = new Intent(Intent.ACTION_DIAL,
                Uri.parse("tel:" + phoneNumber));
        dialerActivity.setFlags(
                Intent.FLAG_ACTIVITY_NEW_TASK
                        | Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
        getApplication().startActivity(dialerActivity);
    }

    /**
     * Provide the capability for the Android SDK to manage the lifetime of the ViewModels and caching
     * by supplying a factory. Just need to call the constructor and implement {@link Factory#create()}
     * in the subclass. You can access the {@link Application} for the ViewModel
     * through the mApplication field.
     * @param <VM> the subclass the Factory should be creating.
     */
    public abstract static class Factory<VM extends MVVMViewModel> implements ViewModelProvider.Factory {
        @NonNull
        protected final Class<VM> mKlass;
        @NonNull
        protected final Application mApplication;

        public Factory(@NonNull final Class<VM> klass, @NonNull final Application application) {
            mApplication = Preconditions.requiresNonNull(application, "Application");
            mKlass = Preconditions.requiresNonNull(klass, "ClassOfVM");
        }

        @NonNull
        public abstract VM create();

        /**
         * Create an instance of ViewModel.
         * @param modelClass the class to create for. Must be Assignable From inherited ViewModel.
         * @param <T> the class to create for. Must be Assignable From inherited ViewModel.
         * @return the created ViewModel.
         */
        @Override
        @NonNull
        public <T extends ViewModel> T create(@NonNull final Class<T> modelClass) {
            // We want to check before construction since the constructor for ResetPasswordViewModel
            // potentially does a large amount of work that would need to get cleaned up.
            if(modelClass.isAssignableFrom(mKlass)) {
                VM viewModel = create();
                return Preconditions.verifyInstanceOf(viewModel, modelClass,
                        "ViewModelIsModelClass");
            } else {
                throw new IllegalArgumentException("ModelClass must be assignable from " + mKlass.getName());
            }
        }
    }

}
