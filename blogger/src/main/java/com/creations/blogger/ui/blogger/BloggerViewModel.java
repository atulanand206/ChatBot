package com.creations.blogger.ui.blogger;

import android.app.Application;

import com.creations.blogger.R;
import com.creations.blogger.databinding.ContentAdvisoryNavigationBinding;
import com.creations.blogger.model.navigation.NavigationBarProps;
import com.creations.blogger.model.navigation.NavigationState;
import com.creations.blogger.ui.navigation.NavigationBarContract;
import com.creations.blogger.ui.navigation.NavigationBarViewModel;
import com.creations.blogger.ui.navigation.NavigationRecycler;
import com.creations.condition.Preconditions;
import com.creations.mvvm.databinding.CardImageBinding;
import com.creations.mvvm.live.MutableLiveData;
import com.creations.mvvm.models.props.Props;
import com.creations.mvvm.ui.animate.AnimatorViewModel;
import com.creations.mvvm.ui.image.ImageAdapter;
import com.creations.mvvm.ui.image.ImageContract;
import com.creations.mvvm.ui.image.ImageViewModel;
import com.creations.mvvm.viewmodel.MVVMViewModel;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

/**
 * This ViewModel works with a Button and is to be used for creating forms.
 */
public class BloggerViewModel extends AnimatorViewModel implements BloggerContract.ViewModel {

    @NonNull
    private final ImageViewModel.Factory mImageFactory;
    @NonNull
    private MutableLiveData<String> mTitle = new MutableLiveData<>();
    @NonNull
    private MutableLiveData<ImageViewModel> mImageViewModel = new MutableLiveData<>();

    @NonNull
    private final ImageAdapter<ImageContract.ViewModel, CardImageBinding> mImageAdapter = new ImageAdapter<>(viewModel -> {
        mImageViewModel.setValue(((ImageViewModel) viewModel));
        animate(true);
    }, com.creations.mvvm.R.layout.card_image);

    @NonNull
    private final NavigationRecycler<NavigationBarContract.ViewModel, ContentAdvisoryNavigationBinding> mNavigationListAdapter = new NavigationRecycler<>(viewModel -> {

    }, R.layout.content_advisory_navigation);

    public BloggerViewModel(@NonNull final Application application,
                            @NonNull final ImageViewModel.Factory imageFactory,
                            @NonNull final NavigationBarViewModel.Factory navigationFactory) {
        super(application, new Props());
        mImageFactory = Preconditions.requiresNonNull(imageFactory, "ImageFactory");
        mTitle.postValue("Let's get going!");
//        mAirspaceApi.getBlogPosts(new ListResponseCallback<Post>() {
//            @Override
//            public void onSuccess(@NonNull List<Post> response) {
//                if (!response.isEmpty())
//                    mImageViewModel.postValue(viewModel(response.get(0)));
//                for (Post post : response) {
//                    addPost(post);
//                    addPost(post);
//                    addPost(post);
//                    addPost(post);
//                    addPost(post);
//                    addPost(post);
//                }
//            }
//
//            @Override
//            public void onError(int statusCode, @NonNull String errorResponse, @NonNull APIResponseBody serializedErrorResponse, @Nullable Exception e) {
//
//            }
//
//            private void addPost(@NonNull final Post post) {
//                mImageAdapter.addItem(viewModel(post));
//            }
//
//            private ImageViewModel viewModel(@NonNull final Post post) {
//                ImageViewModel imageViewModel = mImageFactory.create();
//                imageViewModel.setProps(imageData(post));
//                return imageViewModel;
//            }
//        });
        animate(false);
        NavigationBarViewModel viewModel = navigationFactory.create();
        viewModel.setProps(new NavigationBarProps.Builder()
                .withADVANCED(NavigationState.COMPLETED)
                .withEXPERT(NavigationState.CURRENT)
                .build());
        mNavigationListAdapter.addItem(viewModel);

        NavigationBarViewModel viewModel2 = navigationFactory.create();
        viewModel2.setProps(new NavigationBarProps.Builder()
                .withNOVICE(NavigationState.COMPLETED)
                .withINTERMEDIATE(NavigationState.COMPLETED)
                .withINTERMEDIATE(NavigationState.COMPLETED)
                .withINTERMEDIATE(NavigationState.COMPLETED)
                .withINTERMEDIATE(NavigationState.COMPLETED)
                .withINTERMEDIATE(NavigationState.COMPLETED)
                .withADVANCED(NavigationState.CURRENT)
                .build());
        mNavigationListAdapter.addItem(viewModel2);
        mNavigationListAdapter.addItem(viewModel);
        mNavigationListAdapter.addItem(viewModel);
        mNavigationListAdapter.addItem(viewModel2);
        mNavigationListAdapter.addItem(viewModel2);
    }

    @NonNull
    @Override
    public LiveData<ImageViewModel> getImageViewModel() {
        return mImageViewModel;
    }

    @NonNull
    @Override
    public ImageAdapter getImageAdapter() {
        return mImageAdapter;
    }

    @NonNull
    @Override
    public NavigationRecycler getNavigationAdapter() {
        return mNavigationListAdapter;
    }

    @Override
    public void onOverlayCloseClicked() {
        animate(false);
    }

    public static class Factory extends MVVMViewModel.Factory<BloggerViewModel> {

        @NonNull
        private final NavigationBarViewModel.Factory mNavigationFactory;
        @NonNull
        private final ImageViewModel.Factory mImageFactory;

        public Factory(@NonNull final Application application,
                       @NonNull final ImageViewModel.Factory imageFactory,
                       @NonNull final NavigationBarViewModel.Factory factory) {
            super(BloggerViewModel.class, application);
            mImageFactory = Preconditions.requiresNonNull(imageFactory, "ImageFactory");
            mNavigationFactory =Preconditions.requiresNonNull(factory, "NavigationFactory");
        }

        @NonNull
        @Override
        public BloggerViewModel create() {
            return new BloggerViewModel(mApplication, mImageFactory, mNavigationFactory);
        }
    }
}
