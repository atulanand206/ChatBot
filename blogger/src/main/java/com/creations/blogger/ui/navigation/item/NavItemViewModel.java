package com.creations.blogger.ui.navigation.item;

import android.app.Application;
import android.graphics.drawable.Drawable;
import android.view.View;

import com.creations.blogger.model.navigation.NavigationItem;
import com.creations.condition.Preconditions;
import com.creations.mvvm.live.MutableLiveData;
import com.creations.mvvm.ui.recycler.RecyclerViewModel;
import com.creations.mvvm.viewmodel.MVVMViewModel;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

/**
 * This ViewModel works with a TextInputLayout and is to be used for creating forms.
 */
public class NavItemViewModel extends RecyclerViewModel<NavigationItem> implements NavItemContract.ViewModel<NavigationItem> {

    @NonNull
    private MutableLiveData<Integer> mLineVisibility = new MutableLiveData<>();

    public NavItemViewModel(@NonNull final Application application,
                            @NonNull final NavigationItem navigationBarProps) {
        super(application, navigationBarProps);
    }

    @Override
    public Drawable getDrawable() {
        return getProps().getNavigationState().getDrawable(getApplication());
    }

    @Override
    public LiveData<Integer> getLineVisibility() {
        return mLineVisibility;
    }

    @Override
    public void setSize(@NonNull Integer size) {
        super.setSize(size);
        mLineVisibility.postValue(visib());
    }

    private int visib() {
//        Integer size = getSize().getValue();
//        Integer posn = getPosition().getValue();
//        if (size == null || posn == null)
            return View.GONE;
//        return size.equals(posn+1) ? View.GONE : View.VISIBLE;
    }

    public static class Factory extends MVVMViewModel.Factory<NavItemViewModel> {

        @NonNull
        private final NavigationItem mNavigationBarProps;

        public Factory(@NonNull final Application application,
                       @NonNull final NavigationItem navigationBarProps) {
            super(NavItemViewModel.class, application);
            mNavigationBarProps = Preconditions.requiresNonNull(navigationBarProps, "NavigationBarProps");
        }

        @NonNull
        @Override
        public NavItemViewModel create() {
            return new NavItemViewModel(mApplication, mNavigationBarProps);
        }
    }
}
