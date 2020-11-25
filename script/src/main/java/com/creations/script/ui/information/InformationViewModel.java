package com.creations.script.ui.information;

import android.app.Application;

import androidx.annotation.NonNull;

import com.creations.condition.Preconditions;
import com.creations.mvvm.models.props.Props;
import com.creations.mvvm.ui.menu.MenuViewModel;
import com.creations.mvvm.viewmodel.MVVMViewModel;
import com.creations.script.models.Information;
import com.creations.script.ui.contact.ContactViewModel;

/**
 * This ViewModel works with a TextInputLayout and is to be used for creating forms.
 */
public class InformationViewModel extends ContactViewModel<Information> implements InformationContract.ViewModel<Information> {


    public InformationViewModel(@NonNull final Application application,
                                @NonNull final Information props) {
        super(application);

    }

    public static class Factory extends MVVMViewModel.Factory<InformationViewModel> {

        @NonNull
        private final Information mProps;

        public Factory(@NonNull final Application application,
                       @NonNull final Information props) {
            super(InformationViewModel.class, application);
            mProps = Preconditions.requiresNonNull(props, "Props");
        }

        @NonNull
        @Override
        public InformationViewModel create() {
            return new InformationViewModel(mApplication, mProps);
        }
    }
}
