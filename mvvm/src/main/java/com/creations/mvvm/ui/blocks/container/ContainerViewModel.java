package com.creations.mvvm.ui.blocks.container;

import android.app.Application;
import android.view.View;

import com.creations.blogger.callback.ListResponseCallback;
import com.creations.blogger.model.APIResponseBody;
import com.creations.condition.Preconditions;
import com.creations.mvvm.R;
import com.creations.mvvm.constants.IAPIChat;
import com.creations.mvvm.live.MutableLiveData;
import com.creations.mvvm.models.blocks.Board;
import com.creations.mvvm.models.blocks.ContainerProps;
import com.creations.mvvm.models.blocks.Row;
import com.creations.mvvm.models.blocks.Word;
import com.creations.mvvm.ui.blocks.add.AddContract;
import com.creations.mvvm.ui.blocks.add.AddViewModel;
import com.creations.mvvm.ui.blocks.board.BoardContract;
import com.creations.mvvm.ui.blocks.board.BoardViewModel;
import com.creations.mvvm.ui.blocks.done.DoneViewModel;
import com.creations.mvvm.ui.blocks.score.ScoreContract;
import com.creations.mvvm.ui.blocks.score.ScoreViewModel;
import com.creations.mvvm.ui.recycler.RecyclerViewModel;
import com.creations.mvvm.utils.BoardUtils;
import com.creations.mvvm.viewmodel.MVVMViewModel;
import com.creations.tools.utils.JsonConvertor;
import com.example.application.utils.RecyclerUtils;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;

import static com.creations.mvvm.ui.blocks.add.AddContract.ViewModel.CLICK_ADD_ROW;
import static com.creations.mvvm.ui.blocks.add.AddContract.ViewModel.CLICK_CANCEL_WORD;
import static com.creations.mvvm.ui.blocks.add.AddContract.ViewModel.CLICK_REFRESH;

/**
 * This ViewModel works with a TextInputLayout and is to be used for creating forms.
 */
public class ContainerViewModel extends RecyclerViewModel<ContainerProps> implements ContainerContract.ViewModel<ContainerProps> {

    @NonNull
    private final IAPIChat mApiChat;
    @NonNull
    private final JsonConvertor mJsonConvertor;
    @NonNull
    private final MutableLiveData<Integer> mPossibleScore = new MutableLiveData<>();
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
    @NonNull
    private final MutableLiveData<Integer> mRefreshVisibility = new MutableLiveData<>(View.VISIBLE);

    public ContainerViewModel(@NonNull final Application application,
                              @NonNull final AddViewModel.Factory addFactory,
                              @NonNull final BoardViewModel.Factory boardFactory,
                              @NonNull final ScoreViewModel.Factory scoreFactory,
                              @NonNull final DoneViewModel.Factory doneFactory,
                              @NonNull final IAPIChat apiChat,
                              @NonNull final JsonConvertor jsonConvertor,
                              @NonNull final ContainerProps props) {
        super(application, props);
        mApiChat = apiChat;
        mJsonConvertor = Preconditions.requiresNonNull(jsonConvertor, "Js");
        mAddViewModel = addFactory.create();
        mBoardViewModel = boardFactory.create();
        mScoreViewModel = scoreFactory.create();
        mDoneViewModel = doneFactory.create();
        mBorderWidth.postValue(props.getBorderWidth());
        mContextCallback.addSource(mDoneViewModel.getContextCallback());

        setRows(props);

        mAddViewModel.getAddDoneEvent().observeForever(row -> {
            mAddViewModel.setVisibility(View.GONE);
            if (row instanceof Row) {
                if (((Row) row).getCells().isEmpty())
                    return;
                row.setClickable(true);
                ((Row) row).setLayoutType(RecyclerUtils.LayoutType.LOOP_HORIZONTAL);
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
    }

    @Override
    public void setRows(@NonNull ContainerProps board) {
        super.setRows(board);
        mApiChat.getBoards(new ListResponseCallback<Board>() {
            @Override
            public void onSuccess(@NonNull List<Board> response) {
                if (!response.isEmpty()) {
                    Board newBoard = response.get(0);
                    newBoard.setRows();
                    getProps().setColorResId(R.color.colorPrimary);
                    setTitle(newBoard.getName());
                    setTitleTextSize(mApplication.getResources().getDimension(R.dimen.font_xxxx_large));
                    setTitleTextColorResId(mApplication.getResources().getColor(R.color.black));

                    mBoardViewModel.setRows(newBoard);
                    mBoardViewModel.setVisibility(View.VISIBLE);
                    mBoardViewModel.getAddWordEvent().observeForever(props -> {
                        if (props instanceof Word) {
                            String word = mBoardViewModel.getWord();
                            mScoreViewModel.add(word.length());
                            mDoneViewModel.done(word);
                            Word newWord = (Word) props;
                            setWord(newWord);
                        } else {
                            setWord(null);
                        }
                    });
                    mContextCallback.addSource(mBoardViewModel.getContextCallback());
                }
            }

            @Override
            public void onError(int statusCode, @NonNull String errorResponse, @NonNull APIResponseBody serializedErrorResponse, @Nullable Exception e) {

            }
        });
    }

    private void setWord(@Nullable Word newWord) {
        if (newWord == null) {
            setHeader("");
            setSubHeader("");
            setMeaning("");
            mPossibleScore.setValue(0);
        } else {
            setHeader(newWord.getWord());
            setSubHeader(newWord.getSpeech());
            setMeaning(newWord.getMeaning());
            mPossibleScore.setValue(mScoreViewModel.score(newWord.getWord().length()));
        }
    }

    @Override
    public void setProps(@NonNull ContainerProps props) {
        super.setProps(props);
    }

    @Override
    public void onClick(@NonNull Object object) {
        if (object instanceof Integer){
            if (object.equals(CLICK_ADD_ROW)) {
                mAddViewModel.setRows(BoardUtils.newRow());
                mAddViewModel.setVisibility(View.VISIBLE);
            } else if (object.equals(CLICK_CANCEL_WORD)) {
//                setWord(new Word());
            } else if (object.equals(CLICK_REFRESH)) {
                mBoardViewModel.refresh();
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

    @NonNull
    @Override
    public LiveData<Integer> getRefreshVisibility() {
        return mRefreshVisibility;
    }

    @NonNull
    @Override
    public MutableLiveData<Integer> getPossibleScore() {
        return mPossibleScore;
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

        @NonNull
        private final IAPIChat mApiChat;

        @NonNull
        private final JsonConvertor mJsonConvertor;

        public Factory(@NonNull final Application application,
                       @NonNull final AddViewModel.Factory addFactory,
                       @NonNull final BoardViewModel.Factory boardFactory,
                       @NonNull final ScoreViewModel.Factory scoreFactory,
                       @NonNull final DoneViewModel.Factory doneFactory,
                       @NonNull final IAPIChat apiChat,
                       @NonNull final JsonConvertor jsonConvertor,
                       @NonNull final ContainerProps props) {
            super(ContainerViewModel.class, application);
            mProps = Preconditions.requiresNonNull(props, "Props");
            mFactory = Preconditions.requiresNonNull(boardFactory, "Factory");
            mAddFactory = Preconditions.requiresNonNull(addFactory, "Factory");
            mScoreFactory = Preconditions.requiresNonNull(scoreFactory, "Factory");
            mDoneFactory = Preconditions.requiresNonNull(doneFactory, "Factory");
            mApiChat = Preconditions.requiresNonNull(apiChat, "ApiChat");
            mJsonConvertor = Preconditions.requiresNonNull(jsonConvertor, "Js");
        }

        @NonNull
        @Override
        public ContainerViewModel create() {
            return new ContainerViewModel(mApplication, mAddFactory,
                    mFactory,  mScoreFactory, mDoneFactory,  mApiChat, mJsonConvertor,  mProps);
        }
    }
}
