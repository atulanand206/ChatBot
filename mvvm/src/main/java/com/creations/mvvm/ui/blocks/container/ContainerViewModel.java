package com.creations.mvvm.ui.blocks.container;

import android.app.Application;
import android.view.View;

import com.creations.condition.Preconditions;
import com.creations.mvvm.live.MutableLiveData;
import com.creations.mvvm.models.blocks.ContainerProps;
import com.creations.mvvm.models.blocks.Row;
import com.creations.mvvm.models.props.Props;
import com.creations.mvvm.ui.animate.AnimatorViewModel;
import com.creations.mvvm.ui.blocks.add.AddContract;
import com.creations.mvvm.ui.blocks.add.AddViewModel;
import com.creations.mvvm.ui.blocks.board.BoardContract;
import com.creations.mvvm.ui.blocks.board.BoardViewModel;
import com.creations.mvvm.ui.blocks.done.DoneViewModel;
import com.creations.mvvm.ui.blocks.score.ScoreContract;
import com.creations.mvvm.ui.blocks.score.ScoreViewModel;
import com.creations.mvvm.utils.BoardUtils;
import com.creations.mvvm.viewmodel.MVVMViewModel;
import com.example.application.utils.RecyclerUtils;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import static com.creations.mvvm.ui.blocks.add.AddContract.ViewModel.CLICK_ADD_ROW;
import static com.creations.mvvm.ui.blocks.add.AddContract.ViewModel.CLICK_ADD_WORD;
import static com.creations.mvvm.ui.blocks.add.AddContract.ViewModel.CLICK_CANCEL_WORD;

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
    @NonNull
    private final DoneViewModel mDoneViewModel;
    @NonNull
    private final MutableLiveData<Integer> mAddVisibility = new MutableLiveData<>(View.VISIBLE);
    @NonNull
    private final MutableLiveData<Integer> mCloseVisibility = new MutableLiveData<>(View.VISIBLE);
    @NonNull
    private final MutableLiveData<Integer> mTickVisibility = new MutableLiveData<>(View.VISIBLE);

    public ContainerViewModel(@NonNull final Application application,
                              @NonNull final AddViewModel.Factory addFactory,
                              @NonNull final BoardViewModel.Factory boardFactory,
                              @NonNull final ScoreViewModel.Factory scoreFactory,
                              @NonNull final DoneViewModel.Factory doneFactory,
                              @NonNull final ContainerProps props) {
        super(application, props);
        mAddViewModel = addFactory.create();
        mBoardViewModel = boardFactory.create();
        mScoreViewModel = scoreFactory.create();
        mDoneViewModel = doneFactory.create();
        mBoardViewModel.setProps(props.getBoard());
        mBorderWidth.postValue(props.getBorderWidth());
        mContextCallback.addSource(mBoardViewModel.getContextCallback());
        mContextCallback.addSource(mDoneViewModel.getContextCallback());
        mAddViewModel.getAddDoneEvent().observeForever(row -> {
            mAddViewModel.setVisibility(View.GONE);
            if (row instanceof Row) {
                if (((Row) row).getCells().isEmpty())
                    return;
                row.setClickable(true);
                ((Row) row).setLayoutType(RecyclerUtils.LayoutType.LOOP_HORIZONTAL);
                mBoardViewModel.addRow(((Row) row));
                mScoreViewModel.add("1");
                if (mBoardViewModel.getAdapter().getViewModels().size() == MAX_ROWS) {
                    mAddVisibility.postValue(View.GONE);
                }
                closeKeyboard();
            }
        });
//        mBoardViewModel.getClickEvent().observeForever(o -> {
//            if (o instanceof Cell) {
//                mWordViewModel.addCell(((Cell) o));
//                mWordViewModel.getToastEvent().postEvent(String.valueOf(((Cell) o).getCharacter()));
//                if (mWordViewModel.valid())
//                    mBoardViewModel.valid();
//                else
//                    mBoardViewModel.invalid();
//            }
//        });
        mAddViewModel.getAddCancelEvent().observeForever(props1 -> closeKeyboard());
        mBoardViewModel.getAddWordEvent().observeForever(new Observer<Props>() {
            @Override
            public void onChanged(Props props) {

            }
        });
    }

    @Override
    public void onClick(@NonNull Object object) {
        if (object instanceof Integer){
            if (object.equals(CLICK_ADD_ROW)) {
                mAddViewModel.setProps(BoardUtils.newRow());
                mAddViewModel.setVisibility(View.VISIBLE);
            } else if (object.equals(CLICK_CANCEL_WORD)) {
                mBoardViewModel.clear();
            } else if (object.equals(CLICK_ADD_WORD)) {
                mDoneViewModel.done(mBoardViewModel.getWord());
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

    @NonNull
    @Override
    public DoneViewModel getDoneViewModel() {
        return mDoneViewModel;
    }

    @NonNull
    @Override
    public LiveData<Integer> getAddVisibility() {
        return mAddVisibility;
    }

    @NonNull
    @Override
    public LiveData<Integer> getCloseVisibility() {
        return mCloseVisibility;
    }

    @NonNull
    @Override
    public LiveData<Integer> getTickVisibility() {
        return mTickVisibility;
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



        @NonNull
        private final DoneViewModel.Factory mDoneFactory;

        public Factory(@NonNull final Application application,
                       @NonNull final AddViewModel.Factory addFactory,
                       @NonNull final BoardViewModel.Factory boardFactory,
                       @NonNull final ScoreViewModel.Factory scoreFactory,
                       @NonNull final DoneViewModel.Factory doneFactory,
                       @NonNull final ContainerProps props) {
            super(ContainerViewModel.class, application);
            mProps = Preconditions.requiresNonNull(props, "Props");
            mFactory = Preconditions.requiresNonNull(boardFactory, "Factory");
            mAddFactory = Preconditions.requiresNonNull(addFactory, "Factory");
            mScoreFactory = Preconditions.requiresNonNull(scoreFactory, "Factory");

            mDoneFactory = Preconditions.requiresNonNull(doneFactory, "Factory");
        }

        @NonNull
        @Override
        public ContainerViewModel create() {
            return new ContainerViewModel(mApplication, mAddFactory,
                    mFactory,  mScoreFactory, mDoneFactory,  mProps);
        }
    }
}
