package com.creations.mvvm.ui.navigation;

import android.app.Application;
import android.view.View;

import com.creations.condition.Preconditions;
import com.creations.mvvm.R;
import com.creations.mvvm.databinding.CardAdvisoryNavigationBinding;
import com.creations.mvvm.models.navigation.NavigationBarProps;
import com.creations.mvvm.models.navigation.NavigationItem;
import com.creations.mvvm.ui.navigation.item.NavItemContract;
import com.creations.mvvm.ui.navigation.item.NavItemViewModel;
import com.creations.mvvm.ui.recycler.RecyclerViewModel;
import com.creations.mvvm.viewmodel.MVVMViewModel;

import java.util.List;

import androidx.annotation.NonNull;

/**
 * This ViewModel works with a TextInputLayout and is to be used for creating forms.
 */
public class NavigationBarViewModel extends RecyclerViewModel<NavigationBarProps> implements NavigationBarContract.ViewModel<NavigationBarProps> {

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
        super(application, navigationBarProps);
        Preconditions.requiresNonNull(navigationBarProps, "NavigationBarProps");
        mItemFactory = Preconditions.requiresNonNull(itemFactory, "ItemFactory");
        setProps(navigationBarProps);
        setBackgroundColor(R.color.message_progress);
        setTopColor(R.color.message_progress);
        setVisibility();
    }

    @Override
    public void setProps(@NonNull NavigationBarProps props) {
        super.setProps(props);
        mNavigationAdapter.clearItems();
        for (NavigationItem item : props.getItems()) {
            NavItemViewModel navItemViewModel = mItemFactory.create();
            navItemViewModel.setProps(item);
            mNavigationAdapter.addItem(navItemViewModel);
        }
    }

    public void setVisibility() {
        NavigationBarProps value = getProps();
        List<NavigationItem> items = value.getItems();
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
