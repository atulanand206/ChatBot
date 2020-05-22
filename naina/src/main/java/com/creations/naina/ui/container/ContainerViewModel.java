package com.creations.naina.ui.container;

import android.app.Application;

import com.creations.bang.ui.bang.BangViewModel;
import com.creations.condition.Preconditions;
import com.creations.mvvm.models.props.Props;
import com.creations.mvvm.ui.menu.MenuViewModel;
import com.creations.mvvm.viewmodel.MVVMViewModel;
import com.creations.naina.models.CanvasP;

import androidx.annotation.NonNull;

import static android.view.View.VISIBLE;

/**
 * This ViewModel works with a TextInputLayout and is to be used for creating forms.
 */
public class ContainerViewModel extends MenuViewModel<Props> implements ContainerContract.ViewModel<Props> {

    @NonNull
    BangViewModel mBangViewModel;

    public ContainerViewModel(@NonNull final Application application,
                              @NonNull final BangViewModel.Factory bangFactory,
                              @NonNull final Props props) {
        super(application, props);
        mBangViewModel = bangFactory.create();
        setVisibility(VISIBLE);
    }

    @NonNull
    @Override
    public BangViewModel getBangViewModel() {
        return mBangViewModel;
    }

    public static class Factory extends MVVMViewModel.Factory<ContainerViewModel> {

        @NonNull
        private final Props mProps;

        @NonNull
        private final BangViewModel.Factory mBangFactory;

        public Factory(@NonNull final Application application,
                       @NonNull final BangViewModel.Factory bangFactory,
                       @NonNull final CanvasP props) {
            super(ContainerViewModel.class, application);
            mBangFactory = Preconditions.requiresNonNull(bangFactory, "BangFactory");
            mProps = Preconditions.requiresNonNull(props, "Props");
        }

        @NonNull
        @Override
        public ContainerViewModel create() {
            return new ContainerViewModel(mApplication, mBangFactory,  mProps);
        }
    }
}
