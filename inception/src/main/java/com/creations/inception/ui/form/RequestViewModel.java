package com.creations.inception.ui.form;

import android.app.Application;

import com.creations.condition.Info;
import com.creations.condition.Preconditions;
import com.creations.inception.network.IAPIChat;
import com.creations.mvvm.form.button.ButtonViewModel;
import com.creations.mvvm.form.daterange.DateRangeViewModel;
import com.creations.mvvm.form.editable.EditableViewModel;
import com.creations.mvvm.form.navigation.NavigationBarViewModel;
import com.creations.mvvm.form.spinner.SpinnerViewModel;
import com.creations.mvvm.models.navigation.NavigationBarProps;
import com.creations.mvvm.viewmodel.MVVMViewModel;
import com.example.application.messages.IMessageManager;

import androidx.annotation.NonNull;

public class RequestViewModel extends MVVMViewModel implements RequestContract.ViewModel {
    private static final String TAG = RequestViewModel.class.getName();

    @NonNull
    private final Application mApplication;
    @NonNull
    private final Info mInfo;
    @NonNull
    private final IAPIChat mAirspaceApi;
    @NonNull
    private final IMessageManager mMessageManager;
    @NonNull
    private final NavigationBarViewModel mAdvisoryNavigation;


    public RequestViewModel(@NonNull final Application application,
                            @NonNull final Info info,
                            @NonNull final NavigationBarProps navigationBarProps,
                            @NonNull final NavigationBarViewModel.Factory navigationFactory,
                            @NonNull final SpinnerViewModel.Factory spinnerFactory,
                            @NonNull final ButtonViewModel.Factory buttonFactory,
                            @NonNull final DateRangeViewModel.Factory dateRangeFactory,
                            @NonNull final EditableViewModel.Factory editableFactory,
                            @NonNull final IMessageManager messageManager,
                            @NonNull final IAPIChat airspaceApi) {
        super(application);

        mApplication = Preconditions.requiresNonNull(application, "Application");
        Preconditions.requiresNonNull(dateRangeFactory, "DateRangeFactory");
        Preconditions.requiresNonNull(editableFactory, "EditableFactory");
        mInfo = Preconditions.requiresNonNull(info, "Airspace");
        Preconditions.verifyNonNull(info.getId(), "AirspaceId");
        Preconditions.requiresNonNull(spinnerFactory, "SpinnerFactory");
        mAdvisoryNavigation = navigationFactory.create();
        mAdvisoryNavigation.setProps(navigationBarProps);
        mAirspaceApi = Preconditions.requiresNonNull(airspaceApi, "ApiAirspace");
        mMessageManager = Preconditions.requiresNonNull(messageManager, "MessageManager");

    }

    private void addFieldValidations() {

        disableSubmitButtonIfError();
    }

    private void mapDisableOnFields() {

    }

    private void disableSubmitButtonIfError() {

    }


    private boolean canSave() {
        return true;
    }

    @NonNull
    @Override
    public NavigationBarViewModel getNavigationBar() {
        return mAdvisoryNavigation;
    }

    @Override
    public void setNavigationProps(@NonNull NavigationBarProps props) {
        mAdvisoryNavigation.setProps(props);
    }

    public static class Factory extends MVVMViewModel.Factory<RequestViewModel> {

        @NonNull
        private final Info mInfo;
        @NonNull
        private final DateRangeViewModel.Factory mDateRangeFactory;
        @NonNull
        private final EditableViewModel.Factory mEditableFactory;
        @NonNull
        private final IAPIChat mAirspaceApi;
        @NonNull
        private final SpinnerViewModel.Factory mSpinnerFactory;
        @NonNull
        private final ButtonViewModel.Factory mButtonFactory;
        @NonNull
        private final NavigationBarViewModel.Factory mNavigationFactory;
        @NonNull
        private final NavigationBarProps mNavigationProps;
        @NonNull
        private final IMessageManager mMessageManager;

        public Factory(@NonNull final Application application,
                       @NonNull final Info info,
                       @NonNull final NavigationBarProps navigationBarProps,
                       @NonNull final NavigationBarViewModel.Factory navigationFactory,
                       @NonNull final SpinnerViewModel.Factory spinnerFactory,
                       @NonNull final DateRangeViewModel.Factory dateRangeFactory,
                       @NonNull final EditableViewModel.Factory editableFactory,
                       @NonNull final ButtonViewModel.Factory buttonFactory,
                       @NonNull final IMessageManager messageManager,
                       @NonNull final IAPIChat airspaceApi) {
            super(RequestViewModel.class, application);
            mInfo = Preconditions.requiresNonNull(info, "Airspace");
            mNavigationProps = Preconditions.requiresNonNull(navigationBarProps, "NavigationProps");
            mDateRangeFactory = Preconditions.requiresNonNull(dateRangeFactory, "DateRangeFactory");
            mEditableFactory = Preconditions.requiresNonNull(editableFactory, "EditableFactory");
            mAirspaceApi = Preconditions.requiresNonNull(airspaceApi, "AirspaceApi");
            mSpinnerFactory = Preconditions.requiresNonNull(spinnerFactory, "EventType");
            mButtonFactory = Preconditions.requiresNonNull(buttonFactory, "ButtonFactory");
            mNavigationFactory = Preconditions.requiresNonNull(navigationFactory, "NavigationFactory");
            mMessageManager = Preconditions.requiresNonNull(messageManager, "IMessageManager");
        }

        @NonNull
        @Override
        public RequestViewModel create() {
            return new RequestViewModel(mApplication, mInfo,
                    mNavigationProps, mNavigationFactory, mSpinnerFactory,
                    mButtonFactory, mDateRangeFactory, mEditableFactory,
                    mMessageManager, mAirspaceApi);
        }
    }
}
