package com.creations.inception.ui.blogger;

import android.app.Application;

import com.creations.blogger.IAPIChat;
import com.creations.blogger.callback.ListResponseCallback;
import com.creations.blogger.model.APIResponseBody;
import com.creations.blogger.model.blogger.Post;
import com.creations.condition.Preconditions;
import com.creations.mvvm.form.image.ImageAdapter;
import com.creations.mvvm.form.image.ImageContract;
import com.creations.mvvm.form.image.ImageViewModel;
import com.creations.mvvm.live.MutableLiveData;
import com.creations.mvvm.viewmodel.AnimatorViewModel;
import com.creations.mvvm.viewmodel.MVVMViewModel;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;

import static com.creations.inception.models.convertor.BloggerConverter.imageData;

/**
 * This ViewModel works with a Button and is to be used for creating forms.
 */
public class BloggerViewModel extends AnimatorViewModel implements BloggerContract.ViewModel, ImageAdapter.Listener {

    @NonNull
    private final IAPIChat mAirspaceApi;
    @NonNull
    private final ImageViewModel.Factory mImageFactory;
    @NonNull
    private final List<ImageContract.ViewModel> mImageViewModels = new ArrayList<>();
    @NonNull
    private MutableLiveData<String> mTitle = new MutableLiveData<>();
    @NonNull
    private MutableLiveData<ImageViewModel> mImageViewModel = new MutableLiveData<>();
    @NonNull
    private final ImageAdapter mImageAdapter = new ImageAdapter(mImageViewModels, this);

    public BloggerViewModel(@NonNull final Application application,
                            @NonNull final ImageViewModel.Factory imageFactory,
                            @NonNull final IAPIChat airspaceApi) {
        super(application);
        mImageFactory = Preconditions.requiresNonNull(imageFactory, "ImageFactory");
        mTitle.postValue("Let's get going!");
        mAirspaceApi = Preconditions.requiresNonNull(airspaceApi, "AirspaceApi");
        mAirspaceApi.getBlogPosts(new ListResponseCallback<Post>() {
            @Override
            public void onSuccess(@NonNull List<Post> response) {
                if (!response.isEmpty())
                    mImageViewModel.postValue(viewModel(response.get(0)));
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
                mImageAdapter.addItem(viewModel(post));
            }

            private ImageViewModel viewModel(@NonNull final Post post) {
                ImageViewModel imageViewModel = mImageFactory.create();
                imageViewModel.setData(imageData(post));
                return imageViewModel;
            }
        });
    }

    @NonNull
    @Override
    public LiveData<String> getTitle() {
        return mTitle;
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

    @Override
    public void onOverlayCloseClicked() {
        animate(false);
    }

    @Override
    public void onItemClick(int adapterPosition) {
        List<ImageContract.ViewModel> viewModels = mImageAdapter.getViewModels();
        if (viewModels.size() <= adapterPosition)
            return;
        ImageContract.ViewModel viewModel = viewModels.get(adapterPosition);
        if (viewModel instanceof ImageViewModel)
            mImageViewModel.postValue(((ImageViewModel) viewModel));
        animate(true);
    }

    public static class Factory extends MVVMViewModel.Factory<BloggerViewModel> {

        @NonNull
        private final IAPIChat mAirspaceApi;
        @NonNull
        private final ImageViewModel.Factory mImageFactory;

        public Factory(@NonNull final Application application,
                       @NonNull final ImageViewModel.Factory imageFactory,
                       @NonNull final IAPIChat airspaceApi) {
            super(BloggerViewModel.class, application);
            mImageFactory = Preconditions.requiresNonNull(imageFactory, "ImageFactory");
            mAirspaceApi = Preconditions.requiresNonNull(airspaceApi, "AirspaceApi");
        }

        @NonNull
        @Override
        public BloggerViewModel create() {
            return new BloggerViewModel(mApplication, mImageFactory, mAirspaceApi);
        }
    }
}
