package com.creations.blocks.ui.home;

import android.app.Application;
import android.view.View;

import com.creations.blocks.models.Home;
import com.creations.blocks.ui.add.AddContract;
import com.creations.condition.Preconditions;
import com.creations.mvvm.R;
import com.creations.mvvm.ui.recycler.RecyclerViewModel;
import com.creations.mvvm.viewmodel.MVVMViewModel;

import androidx.annotation.NonNull;

import static com.creations.blocks.ui.add.AddContract.ViewModel.CLICK_SHOW_BOARDS;
import static com.creations.blocks.ui.add.AddContract.ViewModel.CLICK_SHOW_SCORES;

/**
 * This ViewModel works with a TextInputLayout and is to be used for creating forms.
 */
public class HomeViewModel extends RecyclerViewModel<Home> implements HomeContract.ViewModel<Home> {


    public HomeViewModel(@NonNull final Application application,
                         @NonNull final Home props) {
        super(application, props);
        setVisibility(View.VISIBLE);
        setBackgroundColor(props.getColorResId());
        setTextSize(application.getResources().getDimension(R.dimen.font_xxxxxx_large));
        setTextColorResId(application.getResources().getColor(AddContract.ViewModel.COLOR_NORMAL));
    }

    @Override
    public void onClick(@NonNull Object object) {
        if (object instanceof Integer) {
            if (object.equals(CLICK_SHOW_BOARDS)) {
                HomeViewModel.this.getClickEvent().postEvent(CLICK_SHOW_BOARDS);
            } else if (object.equals(CLICK_SHOW_SCORES)) {
                HomeViewModel.this.getClickEvent().postEvent(CLICK_SHOW_SCORES);
            }
        }
    }

    public static class Factory extends MVVMViewModel.Factory<HomeViewModel> {

        @NonNull
        private final Home mProps;

        public Factory(@NonNull final Application application,
                       @NonNull final Home props) {
            super(HomeViewModel.class, application);
            mProps = Preconditions.requiresNonNull(props, "Props");
        }

        @NonNull
        @Override
        public HomeViewModel create() {
            return new HomeViewModel(mApplication, mProps);
        }
    }
}
