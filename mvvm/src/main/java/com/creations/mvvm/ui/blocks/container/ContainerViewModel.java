package com.creations.mvvm.ui.blocks.container;

import android.app.Application;
import android.view.View;

import com.creations.blogger.callback.ObjectResponseCallback;
import com.creations.blogger.model.APIResponseBody;
import com.creations.condition.Preconditions;
import com.creations.mvvm.R;
import com.creations.mvvm.constants.IAPIChat;
import com.creations.mvvm.live.MutableLiveData;
import com.creations.mvvm.models.blocks.Board;
import com.creations.mvvm.models.blocks.ContainerProps;
import com.creations.mvvm.models.blocks.Score;
import com.creations.mvvm.models.blocks.ScoreItem;
import com.creations.mvvm.models.blocks.Word;
import com.creations.mvvm.ui.animate.AnimatorViewModel;
import com.creations.mvvm.ui.blocks.add.AddContract;
import com.creations.mvvm.ui.blocks.add.AddViewModel;
import com.creations.mvvm.ui.blocks.board.BoardContract;
import com.creations.mvvm.ui.blocks.board.BoardViewModel;
import com.creations.mvvm.ui.blocks.done.DoneViewModel;
import com.creations.mvvm.ui.blocks.home.HomeContract;
import com.creations.mvvm.ui.blocks.home.HomeViewModel;
import com.creations.mvvm.ui.blocks.preset.PresetViewModel;
import com.creations.mvvm.ui.blocks.score.ScoreContract;
import com.creations.mvvm.ui.blocks.score.ScoreViewModel;
import com.creations.mvvm.ui.blocks.scoreList.ScoreListContract;
import com.creations.mvvm.ui.blocks.scoreList.ScoreListViewModel;
import com.creations.mvvm.viewmodel.MVVMViewModel;
import com.creations.tools.utils.JsonConvertor;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;

import static com.creations.mvvm.ui.blocks.add.AddContract.ViewModel.CLICK_ADD_ROW;
import static com.creations.mvvm.ui.blocks.add.AddContract.ViewModel.CLICK_ADD_WORD;
import static com.creations.mvvm.ui.blocks.add.AddContract.ViewModel.CLICK_CANCEL_WORD;
import static com.creations.mvvm.ui.blocks.add.AddContract.ViewModel.CLICK_EXIT_BOARD;
import static com.creations.mvvm.ui.blocks.add.AddContract.ViewModel.CLICK_SHOW_BOARDS;
import static com.creations.mvvm.ui.blocks.add.AddContract.ViewModel.CLICK_SHOW_SCORES;
import static com.creations.mvvm.ui.blocks.add.AddContract.ViewModel.CLICK_TO_HOME;
import static com.creations.mvvm.ui.blocks.add.AddContract.ViewModel.COLOR_NORMAL;

/**
 * This ViewModel works with a TextInputLayout and is to be used for creating forms.
 */
public class ContainerViewModel extends AnimatorViewModel<ContainerProps> implements ContainerContract.ViewModel<ContainerProps> {

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
    private final PresetViewModel mPresetViewModel;
    @NonNull
    private final ScoreListViewModel mScoreListViewModel;
    @NonNull
    private final HomeViewModel mHomeViewModel;
    @NonNull
    private final MutableLiveData<Integer> mActionVisibility = new MutableLiveData<>(View.GONE);
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
                              @NonNull final PresetViewModel.Factory presetFactory,
                              @NonNull final ScoreListViewModel.Factory scoreListFactory,
                              @NonNull final HomeViewModel.Factory homeFactory,
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
        mPresetViewModel = presetFactory.create();
        mScoreListViewModel = scoreListFactory.create();
        mHomeViewModel = homeFactory.create();
        mBorderWidth.postValue(props.getBorderWidth());
        mContextCallback.addSource(mScoreListViewModel.getContextCallback());
        mContextCallback.addSource(mPresetViewModel.getContextCallback());
        mContextCallback.addSource(mHomeViewModel.getContextCallback());
        mContextCallback.addSource(mDoneViewModel.getContextCallback());
        mPresetViewModel.setVisibility(View.GONE);
        mScoreListViewModel.setVisibility(View.GONE);
        mHomeViewModel.setVisibility(View.VISIBLE);
        mPresetViewModel.getClickEvent().observeForever(o -> {
            if (o instanceof Board) {
                ContainerViewModel.this.loadBoard(((Board) o));
            } else if (o instanceof Integer) {
                if (o.equals(CLICK_TO_HOME)) {
                    mHomeViewModel.setVisibility(View.VISIBLE);
                    mPresetViewModel.setVisibility(View.GONE);
                }
            }
        });
        mScoreListViewModel.getClickEvent().observeForever(o -> {
            if (o instanceof Integer) {
                if (o.equals(CLICK_TO_HOME)) {
                    mHomeViewModel.setVisibility(View.VISIBLE);
                    mScoreListViewModel.setVisibility(View.GONE);
                }
            }
        });
        mHomeViewModel.getClickEvent().observeForever(o -> {
            if (o instanceof Integer) {
                if (o.equals(CLICK_SHOW_BOARDS)) {
                    mPresetViewModel.setVisibility(View.VISIBLE);
                    mHomeViewModel.setVisibility(View.GONE);
                } else if (o.equals(CLICK_SHOW_SCORES)) {
                    mScoreListViewModel.setVisibility(View.VISIBLE);
                    mHomeViewModel.setVisibility(View.GONE);
                }
            }
        });

//        mAddViewModel.getAddDoneEvent().observeForever(row -> {
//            mAddViewModel.setVisibility(View.GONE);
//            if (row instanceof Row) {
//                if (((Row) row).getCells().isEmpty())
//                    return;
//                row.setClickable(true);
//                ((Row) row).setLayoutType(RecyclerUtils.LayoutType.LOOP_HORIZONTAL);
//                closeKeyboard();
//            }
//        });
//
//        mAddViewModel.getAddCancelEvent().observeForever(props1 -> closeKeyboard());
    }

    private void loadBoard(@NonNull Board board) {
        board = board.refresh(true);
        board.setColorResId(COLOR_NORMAL);
        setTitle(board.getName());
        setTitleTextSize(mApplication.getResources().getDimension(R.dimen.font_xxxx_large));
        setTitleTextColorResId(mApplication.getResources().getColor(R.color.black));
        mPresetViewModel.setVisibility(View.GONE);
        mScoreViewModel.setVisibility(View.VISIBLE);
        mActionVisibility.postValue(View.VISIBLE);
        mBoardViewModel.setRows(board);
        mBoardViewModel.setVisibility(View.VISIBLE);
        mBoardViewModel.getAddWordEvent().observeForever(props -> {
            if (props instanceof Word) {
                String word = mBoardViewModel.getWord();
                mDoneViewModel.done(word);
                Word newWord = (Word) props;
                setWord(newWord);
            } else {
                setWord(null);
            }
        });
        mBoardViewModel.getRefreshEvent().observeForever(sentinel -> {
            if (sentinel != null) {


            }
        });
//        mBoardViewModel.getClickEvent().observeForever(o -> {
//            if (o instanceof Cell) {
//                mWordViewModel.addCell(((Cell) o));

//                if (mWordViewModel.valid())
//                    mBoardViewModel.valid();
//                else
//                    mBoardViewModel.invalid();
//            }
//        });
        mContextCallback.addSource(mBoardViewModel.getContextCallback());
    }

    private void setWord(@Nullable Word newWord) {
        if (newWord == null || newWord.getWord() == null || newWord.getWord().length() == 0) {
            setHeader("");
            setSubHeader("");
            setMeaning("");
            mPossibleScore.setValue(0);
        } else {
            String word = newWord.getWord();
            setHeader(word);
            setSubHeader(newWord.getSpeech());
            setMeaning(newWord.getMeaning());
            mPossibleScore.setValue(mScoreViewModel.score(word.length()));
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
//                mAddViewModel.setRows(BoardUtils.newRow());
//                mAddViewModel.setVisibility(View.VISIBLE);
            } else if (object.equals(CLICK_EXIT_BOARD)) {
                mPresetViewModel.setVisibility(View.VISIBLE);
                mScoreViewModel.setVisibility(View.GONE);
                mBoardViewModel.setVisibility(View.GONE);
                mActionVisibility.setValue(View.GONE);
            } else if (object.equals(CLICK_CANCEL_WORD)) {
                mBoardViewModel.clear();
            } else if (object.equals(CLICK_ADD_WORD)) {
                Score score = mScoreViewModel.getProps();
                ScoreItem scoreItem = score.getScoreItem();
                scoreItem.setBoard(mBoardViewModel.getProps().getId());
                scoreItem.setName("User who rocks!");
                int scr = mBoardViewModel.getWordViewModel().getWord().length();
                scoreItem.setScore(scr * scr);
                score.setScoreItem(scoreItem);
                mApiChat.postScore(scoreItem, new ObjectResponseCallback<ScoreItem>() {
                    @Override
                    public void onSuccess(@NonNull ScoreItem response) {
                        mScoreViewModel.add(mBoardViewModel.getWord().length());
                    }

                    @Override
                    public void onError(int statusCode, @NonNull String errorResponse, @NonNull APIResponseBody serializedErrorResponse, @Nullable Exception e) {

                    }
                });
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
    public PresetViewModel getPresetViewModel() {
        return mPresetViewModel;
    }

    @NonNull
    @Override
    public ScoreListContract.ViewModel getScoreListViewModel() {
        return mScoreListViewModel;
    }

    @NonNull
    @Override
    public HomeContract.ViewModel getHomeViewModel() {
        return mHomeViewModel;
    }

    @NonNull
    @Override
    public LiveData<Integer> getActionVisibility() {
        return mActionVisibility;
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
        private final PresetViewModel.Factory mPresetFactory;

        @NonNull
        private final ScoreListViewModel.Factory mScoreListFactory;

        @NonNull
        private final HomeViewModel.Factory mHomeFactory;

        @NonNull
        private final IAPIChat mApiChat;

        @NonNull
        private final JsonConvertor mJsonConvertor;

        public Factory(@NonNull final Application application,
                       @NonNull final AddViewModel.Factory addFactory,
                       @NonNull final BoardViewModel.Factory boardFactory,
                       @NonNull final ScoreViewModel.Factory scoreFactory,
                       @NonNull final DoneViewModel.Factory doneFactory,
                       @NonNull final PresetViewModel.Factory presetFactory,
                       @NonNull final ScoreListViewModel.Factory scoreListFactory,
                       @NonNull final HomeViewModel.Factory homeFactory,
                       @NonNull final IAPIChat apiChat,
                       @NonNull final JsonConvertor jsonConvertor,
                       @NonNull final ContainerProps props) {
            super(ContainerViewModel.class, application);
            mProps = Preconditions.requiresNonNull(props, "Props");
            mFactory = Preconditions.requiresNonNull(boardFactory, "Factory");
            mAddFactory = Preconditions.requiresNonNull(addFactory, "Factory");
            mScoreFactory = Preconditions.requiresNonNull(scoreFactory, "Factory");
            mDoneFactory = Preconditions.requiresNonNull(doneFactory, "Factory");
            mPresetFactory = Preconditions.requiresNonNull(presetFactory, "Factory");
            mScoreListFactory = Preconditions.requiresNonNull(scoreListFactory, "Factory");
            mHomeFactory = Preconditions.requiresNonNull(homeFactory, "Factory");
            mApiChat = Preconditions.requiresNonNull(apiChat, "ApiChat");
            mJsonConvertor = Preconditions.requiresNonNull(jsonConvertor, "Js");
        }

        @NonNull
        @Override
        public ContainerViewModel create() {
            return new ContainerViewModel(mApplication, mAddFactory,
                    mFactory,  mScoreFactory, mDoneFactory, mPresetFactory,
                    mScoreListFactory, mHomeFactory, mApiChat, mJsonConvertor,  mProps);
        }
    }
}
