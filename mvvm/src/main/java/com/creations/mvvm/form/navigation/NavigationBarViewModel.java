package com.creations.mvvm.form.navigation;

import android.app.Application;
import android.graphics.drawable.Drawable;
import android.view.View;

import com.creations.condition.Preconditions;
import com.creations.mvvm.R;
import com.creations.mvvm.form.FormViewModelBase;
import com.creations.mvvm.live.MutableLiveData;
import com.creations.mvvm.models.navigation.NavigationBarProps;
import com.creations.mvvm.models.navigation.NavigationItem;
import com.creations.mvvm.viewmodel.MVVMViewModel;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.RecyclerView;

/**
 * This ViewModel works with a TextInputLayout and is to be used for creating forms.
 */
public class NavigationBarViewModel extends FormViewModelBase implements NavigationBarContract.ViewModel {

    @NonNull
    private NavigationBarProps mProps;
    @NonNull
    private MutableLiveData<RecyclerView.Adapter> mNavigationAdapter = new MutableLiveData<>(new NavigationBarAdapter(R.layout.card_advisory_navigation, this));

    public NavigationBarViewModel(@NonNull final Application application,
                                  @NonNull final NavigationBarProps navigationBarProps) {
        super(application, "navigationBarProps");
        Preconditions.requiresNonNull(navigationBarProps, "NavigationBarProps");
        mProps = navigationBarProps;
    }


    @Override
    public void setProps(@NonNull final NavigationBarProps props) {
        mProps = Preconditions.requiresNonNull(props, "Props");
        RecyclerView.Adapter adapter = mNavigationAdapter.getValue();
        if (adapter != null)
            adapter.notifyDataSetChanged();
    }

    @Override
    public Integer getHeaderVisibility() {
        List<NavigationItem> items = mProps.getItems();
        if (items == null || items.isEmpty())
            return View.GONE;
        if (items.size() == 1)
            return View.GONE;
        return View.VISIBLE;
    }

    @NonNull
    @Override
    public String getTitle(int position) {
        List<NavigationItem> items = mProps.getItems();
        if (items == null || position >= items.size())
            return "";
        NavigationItem navigationItem = items.get(position);
        return navigationItem.getLabel().name();
    }

    @Override
    public Drawable getDrawable(int position) {
        List<NavigationItem> items = mProps.getItems();
        if (items == null || position >= items.size())
            return null;
        NavigationItem navigationItem = items.get(position);
        return navigationItem.getNavigationState().getDrawable(getApplication());
    }

    @NonNull
    @Override
    public LiveData<RecyclerView.Adapter> getAdapter() {
        return mNavigationAdapter;
    }

    @Override
    public Integer getLineVisibility(final int position) {
        List<NavigationItem> items = mProps.getItems();
        if (items == null || items.isEmpty())
            return View.GONE;
        if (position == items.size()-1)
            return View.GONE;
        return View.VISIBLE;
    }

    @NonNull
    @Override
    public NavigationBarProps getProps() {
        return mProps;
    }

    public static class Factory extends MVVMViewModel.Factory<NavigationBarViewModel> {

        @NonNull
        private final NavigationBarProps mNavigationBarProps;

        public Factory(@NonNull final Application application,
                       @NonNull final NavigationBarProps navigationBarProps) {
            super(NavigationBarViewModel.class, application);
            mNavigationBarProps = Preconditions.requiresNonNull(navigationBarProps, "NavigationBarProps");
        }

        @NonNull
        @Override
        public NavigationBarViewModel create() {
            return new NavigationBarViewModel(mApplication, mNavigationBarProps);
        }
    }
}
