package com.creations.mvvm.viewmodel;

import android.app.Application;
import android.content.Intent;
import android.net.Uri;
import android.view.View;

import com.creations.condition.Preconditions;
import com.creations.mvvm.R;
import com.creations.mvvm.live.LiveEvent;
import com.creations.mvvm.live.MutableLiveData;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

/**
 * Base class implementation for {@link IMVVMViewModel}'s. Allows getting a Context
 * for a callback and has a helper class for implementing {@link Factory}'s for ViewModel.
 */
public abstract class MVVMViewModel extends AndroidViewModel implements IMVVMViewModel {

    @NonNull
    private final MutableLiveData<Integer> mVisibility = new MutableLiveData<>(View.GONE);

    @NonNull
    private  MutableLiveData<Integer> mId = new MutableLiveData<>();

    @NonNull
    private MutableLiveData<Integer> mBackgroundColor = new MutableLiveData<>(R.color.yellow);

    protected MVVMViewModel(@NonNull final Application application) {
        super(application);
    }

    @NonNull
    protected final LiveEvent.Mutable<ContextCallback> mContextCallback = new LiveEvent.Mutable<>();

    @NonNull
    public LiveEvent<ContextCallback> getContextCallback() {
        return mContextCallback;
    }

    @Override
    public LiveData<Integer> getVisibility() {
        return mVisibility;
    }

    @Override
    public void setVisibility(Integer visibility) {
        mVisibility.postValue(visibility);
    }

    @NonNull
    @Override
    public LiveData<Integer> getId() {
        return mId;
    }

    @Override
    public void setId(@IdRes final int id) {
        mId.postValue(id);
    }

    @Override
    public LiveData<Integer> getBackgroundColor() {
        return mBackgroundColor;
    }

    @Override
    public void setBackgroundColor(final int backgroundColorResId) {
        mBackgroundColor.postValue(backgroundColorResId);
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

    protected void removeSetError(@Nullable final MutableLiveData<String> textFieldError,
                                  @Nullable final MutableLiveData<Boolean> errorEnabled) {
        textFieldError.setValue(null);
        errorEnabled.setValue(false);
    }

    protected void removePostError(@Nullable final MutableLiveData<String> textFieldError,
                                   @Nullable final MutableLiveData<Boolean> errorEnabled) {
        textFieldError.postValue(null);
        errorEnabled.postValue(false);
    }

    protected void setError(@Nullable final MutableLiveData<String> textFieldError,
                            @Nullable final MutableLiveData<Boolean> errorEnabled,
                            @StringRes final int errorResId) {
        removeSetError(textFieldError, errorEnabled);
        textFieldError.setValue(getApplication().getString(errorResId));
        errorEnabled.setValue(true);
    }

    protected void postError(@Nullable final MutableLiveData<String> textFieldError,
                             @Nullable final MutableLiveData<Boolean> errorEnabled,
                             @StringRes final int errorResId) {
        removePostError(textFieldError, errorEnabled);
        textFieldError.postValue(getApplication().getString(errorResId));
        errorEnabled.postValue(true);
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
