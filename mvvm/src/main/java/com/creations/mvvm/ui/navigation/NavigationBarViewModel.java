package com.creations.mvvm.ui.navigation;

import android.app.Application;
import android.graphics.drawable.Drawable;
import android.view.View;

import com.creations.condition.Preconditions;
import com.creations.mvvm.R;
import com.creations.mvvm.live.LiveEvent;
import com.creations.mvvm.live.MutableLiveData;
import com.creations.mvvm.models.navigation.NavigationBarProps;
import com.creations.mvvm.models.navigation.NavigationItem;
import com.creations.mvvm.ui.FormViewModelBase;
import com.creations.mvvm.viewmodel.MVVMViewModel;

import java.util.List;

import androidx.annotation.ColorRes;
import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.RecyclerView;

/**
 * This ViewModel works with a TextInputLayout and is to be used for creating forms.
 */
public class NavigationBarViewModel extends FormViewModelBase implements NavigationBarContract.ViewModel {

    @NonNull
    private final LiveEvent.Mutable<Integer> mEvent = new LiveEvent.Mutable<>();
    @NonNull
    private NavigationBarProps mProps;
    @NonNull
    private MutableLiveData<RecyclerView.Adapter> mNavigationAdapter = new MutableLiveData<>(new NavigationBarAdapter(R.layout.card_advisory_navigation, this));

    private int[] colors = new int[] {R.color.black, R.color.colorMenuText, R.color.colorBottomButton, R.color.colorAccent};
    private int currentColorIndex = 0;

    public NavigationBarViewModel(@NonNull final Application application,
                                  @NonNull final NavigationBarProps navigationBarProps) {
        super(application, "navigationBarProps");
        Preconditions.requiresNonNull(navigationBarProps, "NavigationBarProps");
        mProps = navigationBarProps;
        setTopColor(R.color.message_progress);
    }


    @Override
    public void setProps(@NonNull final NavigationBarProps props) {
        mProps = Preconditions.requiresNonNull(props, "Props");
        RecyclerView.Adapter adapter = mNavigationAdapter.getValue();
        if (adapter != null)
            adapter.notifyDataSetChanged();
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

    @Override
    public LiveData<Integer> getVisibility() {
        List<NavigationItem> items = mProps.getItems();
        if (items == null || items.isEmpty()) {
            setVisibility(View.GONE);
            return super.getVisibility();
        }
        if (items.size() == 1) {
            setVisibility(View.GONE);
            return super.getVisibility();
        }
        setVisibility(View.VISIBLE);
        return super.getVisibility();
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
