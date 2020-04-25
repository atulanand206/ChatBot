package com.creations.inception.ui.form;

import android.app.Application;

import com.creations.blogger.IAPIChat;
import com.creations.blogger.callback.ListResponseCallback;
import com.creations.blogger.model.APIResponseBody;
import com.creations.blogger.model.blogger.Post;
import com.creations.condition.Info;
import com.creations.condition.Preconditions;
import com.creations.mvvm.form.button.ButtonViewModel;
import com.creations.mvvm.form.contact.ContactViewModel;
import com.creations.mvvm.form.daterange.DateRangeViewModel;
import com.creations.mvvm.form.editable.EditableViewModel;
import com.creations.mvvm.form.image.ImageAdapter;
import com.creations.mvvm.form.image.ImageContract;
import com.creations.mvvm.form.image.ImageViewModel;
import com.creations.mvvm.form.navigation.NavigationBarViewModel;
import com.creations.mvvm.models.navigation.NavigationBarProps;
import com.creations.mvvm.viewmodel.MVVMViewModel;
import com.example.application.messages.IMessageManager;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import static com.creations.inception.models.convertor.BloggerConverter.imageData;

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
    @NonNull
    private final ImageViewModel.Factory mImageFactory;

    @NonNull
    private final List<ImageContract.ViewModel> mImageViewModels = new ArrayList<>();

    @NonNull
    private ImageViewModel mImageViewModel;
    @NonNull
    private final ImageAdapter mImageAdapter = new ImageAdapter(mImageViewModels);



    public RequestViewModel(@NonNull final Application application,
                            @NonNull final Info info,
                            @NonNull final NavigationBarProps navigationBarProps,
                            @NonNull final NavigationBarViewModel.Factory navigationFactory,
                            @NonNull final ContactViewModel.Factory spinnerFactory,
                            @NonNull final ButtonViewModel.Factory buttonFactory,
                            @NonNull final ImageViewModel.Factory imageFactory,
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
        mImageFactory = Preconditions.requiresNonNull(imageFactory, "ImageFactory");
        mAirspaceApi.getBlogPosts(new ListResponseCallback<Post>() {
            @Override
            public void onSuccess(@NonNull List<Post> response) {
                if (!response.isEmpty())
                    addPost(response.get(0));
                for (Post post : response) {
                    addPost(post);
                    addPost(post);
                    addPost(post);
                    addPost(post);
                    addPost(post);
                    addPost(post);
                }
            }

            @Override
            public void onError(int statusCode, @NonNull String errorResponse, @NonNull APIResponseBody serializedErrorResponse, @Nullable Exception e) {

            }

            private void addPost(@NonNull final Post post) {
                ImageViewModel imageViewModel = mImageFactory.create();
                imageViewModel.setData(imageData(post));
                mImageAdapter.addItem(imageViewModel);
            }
        });

    }

    @NonNull
    @Override
    public ImageViewModel getImageViewModel() {
        return mImageViewModel;
    }

    @NonNull
    @Override
    public ImageAdapter getImageAdapter() {
        return mImageAdapter;
    }

    @NonNull
    @Override
    public NavigationBarViewModel getNavigationBar() {
        return mAdvisoryNavigation;
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
        private final ContactViewModel.Factory mListFactory;
        @NonNull
        private final ButtonViewModel.Factory mButtonFactory;
        @NonNull
        private final ImageViewModel.Factory mImageFactory;
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
                       @NonNull final ContactViewModel.Factory spinnerFactory,
                       @NonNull final DateRangeViewModel.Factory dateRangeFactory,
                       @NonNull final EditableViewModel.Factory editableFactory,
                       @NonNull final ButtonViewModel.Factory buttonFactory,
                       @NonNull final ImageViewModel.Factory imageFactory,
                       @NonNull final IMessageManager messageManager,
                       @NonNull final IAPIChat airspaceApi) {
            super(RequestViewModel.class, application);
            mInfo = Preconditions.requiresNonNull(info, "Airspace");
            mNavigationProps = Preconditions.requiresNonNull(navigationBarProps, "NavigationProps");
            mDateRangeFactory = Preconditions.requiresNonNull(dateRangeFactory, "DateRangeFactory");
            mEditableFactory = Preconditions.requiresNonNull(editableFactory, "EditableFactory");
            mAirspaceApi = Preconditions.requiresNonNull(airspaceApi, "AirspaceApi");
            mListFactory = Preconditions.requiresNonNull(spinnerFactory, "EventType");
            mButtonFactory = Preconditions.requiresNonNull(buttonFactory, "ButtonFactory");
            mImageFactory = Preconditions.requiresNonNull(imageFactory, "ImageFactory");
            mNavigationFactory = Preconditions.requiresNonNull(navigationFactory, "NavigationFactory");
            mMessageManager = Preconditions.requiresNonNull(messageManager, "IMessageManager");
        }

        @NonNull
        @Override
        public RequestViewModel create() {
            return new RequestViewModel(mApplication, mInfo,
                    mNavigationProps, mNavigationFactory, mListFactory,
                    mButtonFactory, mImageFactory, mDateRangeFactory, mEditableFactory,
                    mMessageManager, mAirspaceApi);
        }
    }
}
