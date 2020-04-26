package com.creations.mvvm.ui.navigation;

import android.app.Application;
import android.view.View;

import com.creations.condition.Preconditions;
import com.creations.mvvm.R;
import com.creations.mvvm.databinding.CardAdvisoryNavigationBinding;
import com.creations.mvvm.live.LiveEvent;
import com.creations.mvvm.models.navigation.NavigationBarProps;
import com.creations.mvvm.models.navigation.NavigationItem;
import com.creations.mvvm.ui.navigation.item.NavItemContract;
import com.creations.mvvm.ui.navigation.item.NavItemViewModel;
import com.creations.mvvm.ui.recycler.RecyclerViewModel;
import com.creations.mvvm.viewmodel.MVVMViewModel;

import java.util.List;

import androidx.annotation.ColorRes;
import androidx.annotation.NonNull;

/**
 * This ViewModel works with a TextInputLayout and is to be used for creating forms.
 */
public class NavigationBarViewModel extends RecyclerViewModel implements NavigationBarContract.ViewModel {

    @NonNull
    private final LiveEvent.Mutable<Integer> mEvent = new LiveEvent.Mutable<>();
    @NonNull
    private NavigationBarProps mProps;
    @NonNull
    private NavigationBarAdapter<NavItemContract.ViewModel, CardAdvisoryNavigationBinding> mNavigationAdapter = new NavigationBarAdapter<>(viewModel -> {

    }, R.layout.card_advisory_navigation);

    @NonNull
    private final NavItemViewModel.Factory mItemFactory;
    private int[] colors = new int[] {R.color.black, R.color.colorMenuText, R.color.colorBottomButton, R.color.colorAccent};
    private int currentColorIndex = 0;

    public NavigationBarViewModel(@NonNull final Application application,
                                  @NonNull final NavItemViewModel.Factory itemFactory,
                                  @NonNull final NavigationBarProps navigationBarProps) {
        super(application);
        Preconditions.requiresNonNull(navigationBarProps, "NavigationBarProps");
        mItemFactory = Preconditions.requiresNonNull(itemFactory, "ItemFactory");
        setProps(navigationBarProps);
        setTopColor(R.color.message_progress);
        setVisibility();
    }

    @Override
    public void setProps(@NonNull final NavigationBarProps props) {
        mNavigationAdapter.clearItems();
        mProps = Preconditions.requiresNonNull(props, "Props");
        for (NavigationItem item : props.getItems()) {
            NavItemViewModel navItemViewModel = mItemFactory.create();
            navItemViewModel.setData(item);
            mNavigationAdapter.addItem(navItemViewModel);
        }
    }

    @NonNull
    @Override
    public LiveEvent.Mutable<Integer> getStatusBarColorEvent() {
        return mEvent;
    }

    @Override
    public void setTopColor(@ColorRes final int backgroundColorResId) {
        setBackgroundColor(backgroundColorResId);
        mEvent.postEvent(backgroundColorResId);
    }

    public void setVisibility() {
        List<NavigationItem> items = mProps.getItems();
        if (items == null || items.isEmpty()) {
            setVisibility(View.GONE);
            return;
        }
        if (items.size() == 1) {
            setVisibility(View.GONE);
            return;
        }
        setVisibility(View.VISIBLE);
    }

    @NonNull
    @Override
    public NavigationBarAdapter getAdapter() {
        return mNavigationAdapter;
    }

    @NonNull
    @Override
    public NavigationBarProps getProps() {
        return mProps;
    }

    public static class Factory extends MVVMViewModel.Factory<NavigationBarViewModel> {

        @NonNull
        private final NavigationBarProps mNavigationBarProps;

        @NonNull
        private final NavItemViewModel.Factory mItemFactory;

        public Factory(@NonNull final Application application,
                       @NonNull final NavItemViewModel.Factory itemFactory,
                       @NonNull final NavigationBarProps navigationBarProps) {
            super(NavigationBarViewModel.class, application);
            mNavigationBarProps = Preconditions.requiresNonNull(navigationBarProps, "NavigationBarProps");
            mItemFactory = Preconditions.requiresNonNull(itemFactory, "NavigationBarProps");
        }

        @NonNull
        @Override
        public NavigationBarViewModel create() {
            return new NavigationBarViewModel(mApplication, mItemFactory, mNavigationBarProps);
        }
    }
}
