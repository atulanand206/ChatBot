package com.creations.mvvm.ui.blocks.container;

import android.app.Application;
import android.view.View;

import com.creations.condition.Preconditions;
import com.creations.mvvm.live.MutableLiveData;
import com.creations.mvvm.models.blocks.ContainerProps;
import com.creations.mvvm.models.blocks.Row;
import com.creations.mvvm.ui.animate.AnimatorViewModel;
import com.creations.mvvm.ui.blocks.add.AddContract;
import com.creations.mvvm.ui.blocks.add.AddViewModel;
import com.creations.mvvm.ui.blocks.board.BoardContract;
import com.creations.mvvm.ui.blocks.board.BoardViewModel;
import com.creations.mvvm.ui.blocks.score.ScoreContract;
import com.creations.mvvm.ui.blocks.score.ScoreViewModel;
import com.creations.mvvm.utils.BoardUtils;
import com.creations.mvvm.viewmodel.MVVMViewModel;
import com.example.application.utils.RecyclerUtils;

import androidx.annotation.NonNull;

import static com.creations.mvvm.ui.blocks.add.AddContract.ViewModel.CLICK_ADD_ROW;

/**
 * This ViewModel works with a TextInputLayout and is to be used for creating forms.
 */
public class ContainerViewModel extends AnimatorViewModel<ContainerProps> implements ContainerContract.ViewModel<ContainerProps> {

    @NonNull
    private final MutableLiveData<Integer> mBorderWidth = new MutableLiveData<>();
    @NonNull
    private final BoardViewModel mBoardViewModel;
    @NonNull
    private final AddViewModel mAddViewModel;
    @NonNull
    private final ScoreViewModel mScoreViewModel;

    public ContainerViewModel(@NonNull final Application application,
                              @NonNull final AddViewModel.Factory addFactory,
                              @NonNull final BoardViewModel.Factory boardFactory,
                              @NonNull final ScoreViewModel.Factory scoreFactory,
                              @NonNull final ContainerProps props) {
        super(application, props);
        mAddViewModel = addFactory.create();
        mBoardViewModel = boardFactory.create();
        mScoreViewModel = scoreFactory.create();
        mBoardViewModel.setProps(props.getBoard());
        mBorderWidth.postValue(props.getBorderWidth());
        mAddViewModel.getAddDoneEvent().observeForever(row -> {
            if (row instanceof Row) {
                if (((Row) row).getCells().isEmpty())
                    return;
                ((Row) row).setLayoutType(RecyclerUtils.LayoutType.LOOP_HORIZONTAL);
                mBoardViewModel.addRow(((Row) row));
                mScoreViewModel.add("1");
            }
            mAddViewModel.setVisibility(View.GONE);
        });
    }

    @Override
    public void onClick(@NonNull Object object) {
        if (object instanceof Integer){
            if (object.equals(CLICK_ADD_ROW)) {
                mAddViewModel.setProps(BoardUtils.newRow());
                mAddViewModel.setVisibility(View.VISIBLE);
            }
        }
    }

    @NonNull
    @Override
    public AddContract.ViewModel getAddViewModel() {
        return mAddViewModel;
    }

    @NonNull
    @Override
    public MutableLiveData<Integer> getBorderWidth() {
        return mBorderWidth;
    }

    @NonNull
    @Override
    public BoardContract.ViewModel getBoardViewModel() {
        return mBoardViewModel;
    }

    @NonNull
    @Override
    public ScoreContract.ViewModel getScoreViewModel() {
        return mScoreViewModel;
    }

    public static class Factory extends MVVMViewModel.Factory<ContainerViewModel> {

        @NonNull
        private final ContainerProps mProps;

        @NonNull
        private final AddViewModel.Factory mAddFactory;

        @NonNull
        private final BoardViewModel.Factory mFactory;

        @NonNull
        private final ScoreViewModel.Factory mScoreFactory;

        public Factory(@NonNull final Application application,
                       @NonNull final AddViewModel.Factory addFactory,
                       @NonNull final BoardViewModel.Factory boardFactory,
                       @NonNull final ScoreViewModel.Factory scoreFactory,
                       @NonNull final ContainerProps props) {
            super(ContainerViewModel.class, application);
            mProps = Preconditions.requiresNonNull(props, "Props");
            mFactory = Preconditions.requiresNonNull(boardFactory, "Factory");
            mAddFactory = Preconditions.requiresNonNull(addFactory, "Factory");
            mScoreFactory = Preconditions.requiresNonNull(scoreFactory, "Factory");
        }

        @NonNull
        @Override
        public ContainerViewModel create() {
            return new ContainerViewModel(mApplication, mAddFactory, mFactory,  mScoreFactory, mProps);
        }
    }
}
