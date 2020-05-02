package com.creations.mvvm.ui.blocks.scoreList;

import android.app.Application;

import com.creations.blogger.callback.ListResponseCallback;
import com.creations.blogger.model.APIResponseBody;
import com.creations.condition.Preconditions;
import com.creations.mvvm.R;
import com.creations.mvvm.constants.IAPIChat;
import com.creations.mvvm.databinding.CardBlocksScoreItemBinding;
import com.creations.mvvm.models.blocks.ScoreItem;
import com.creations.mvvm.models.blocks.Scores;
import com.creations.mvvm.ui.blocks.add.AddContract;
import com.creations.mvvm.ui.blocks.scoreItem.ScoreItemContract;
import com.creations.mvvm.ui.blocks.scoreItem.ScoreItemViewModel;
import com.creations.mvvm.ui.recycler.RecyclerViewModel;
import com.creations.mvvm.viewmodel.MVVMViewModel;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * This ViewModel works with a TextInputLayout and is to be used for creating forms.
 */
public class ScoreListViewModel extends RecyclerViewModel<Scores> implements ScoreListContract.ViewModel<Scores> {


    @NonNull
    private final ScoreItemViewModel.Factory mFactory;

    @NonNull
    private final IAPIChat mApiChat;

    @NonNull
    private final ScoreListAdapter<ScoreItemContract.ViewModel, CardBlocksScoreItemBinding> adapter = new ScoreListAdapter<>(viewModel -> {

    }, R.layout.card_blocks_score_item);

    public ScoreListViewModel(@NonNull final Application application,
                              @NonNull final ScoreItemViewModel.Factory factory,
                              @NonNull final IAPIChat apiChat,
                              @NonNull final Scores props) {
        super(application, props);
        mFactory = factory;
        mApiChat = apiChat;
        mApiChat.getScores(new ListResponseCallback<ScoreItem>() {
            @Override
            public void onSuccess(@NonNull List<ScoreItem> response) {
                List<ScoreItemContract.ViewModel> viewModels = new ArrayList<>();
                for (ScoreItem item : response) {
                    ScoreItemViewModel scoreItemViewModel = mFactory.create();
                    scoreItemViewModel.setProps(item);
                    viewModels.add(scoreItemViewModel);
                }
                adapter.setViewModels(viewModels);
            }

            @Override
            public void onError(int statusCode, @NonNull String errorResponse, @NonNull APIResponseBody serializedErrorResponse, @Nullable Exception e) {

            }
        });
    }

    @Override
    public void onClick(@NonNull Object object) {
        if (object instanceof Integer) {
            if (object.equals(AddContract.ViewModel.CLICK_TO_HOME))
                ScoreListViewModel.this.getClickEvent().postEvent(AddContract.ViewModel.CLICK_TO_HOME);
        }
    }

    @NonNull
    @Override
    public ScoreListAdapter<ScoreItemContract.ViewModel, CardBlocksScoreItemBinding> getAdapter() {
        return adapter;
    }

    public static class Factory extends MVVMViewModel.Factory<ScoreListViewModel> {

        @NonNull
        private final Scores mProps;

        @NonNull
        private final ScoreItemViewModel.Factory mFactory;

        @NonNull
        private final IAPIChat mApiChat;

        public Factory(@NonNull final Application application,
                       @NonNull final ScoreItemViewModel.Factory factory,
                       @NonNull final IAPIChat apiChat,
                       @NonNull final Scores props) {
            super(ScoreListViewModel.class, application);
            mProps = Preconditions.requiresNonNull(props, "Props");
            mFactory = Preconditions.requiresNonNull(factory, "Factory");
            mApiChat = Preconditions.requiresNonNull(apiChat, "ApiChat");
        }

        @NonNull
        @Override
        public ScoreListViewModel create() {
            return new ScoreListViewModel(mApplication, mFactory,  mApiChat, mProps);
        }
    }
}
