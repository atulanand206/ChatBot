package com.creations.blocks.ui.add;

import android.app.Application;
import android.text.Editable;

import com.creations.blocks.models.Add;
import com.creations.blocks.models.Board;
import com.creations.blocks.ui.board.BoardContract;
import com.creations.blocks.ui.board.BoardViewModel;
import com.creations.condition.Preconditions;
import com.creations.mvvm.live.LiveEvent;
import com.creations.mvvm.models.props.Props;
import com.creations.mvvm.ui.recycler.RecyclerViewModel;
import com.creations.mvvm.viewmodel.MVVMViewModel;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import static android.view.View.GONE;
import static com.creations.blocks.ui.container.ContainerContract.ViewModel.MIN_COLUMNS;

/**
 * This ViewModel works with a TextInputLayout and is to be used for creating forms.
 */
public class AddViewModel extends RecyclerViewModel<Add> implements AddContract.ViewModel<Add> {

    @NonNull
    private final BoardViewModel mBoardViewModel;

    @NonNull
    private final LiveEvent.Mutable<Props> mAddDoneEvent = new LiveEvent.Mutable<>();

    @NonNull
    private final LiveEvent.Mutable<Props> mAddCancelEvent = new LiveEvent.Mutable<>();


    public AddViewModel(@NonNull final Application application,
                        @NonNull final BoardViewModel.Factory rowFactory,
                        @NonNull final Add props) {
        super(application, props, GONE);
        mBoardViewModel = rowFactory.create();
        setRows(props);
    }

    @Override
    public void setRows(@NonNull Add props) {
        setProps(props);
    }

    @Override
    public void setProps(@NonNull Add props) {
        super.setProps(props);
        mBoardViewModel.setProps(new Board(props.getRows()));
        setText("");
        setVisibility(GONE);
    }

    @Override
    public void onClick(@NonNull Object object) {
        if (object instanceof Integer){
            if (object.equals(CLICK_BACKGROUND)) {
                setVisibility(GONE);
                mAddCancelEvent.postEvent(new Props());
            } else if (object.equals(CLICK_ADD_BUTTON)) {
                String txt = getText().getValue();
                if (txt == null || txt.length() < MIN_COLUMNS) {
                    mBoardViewModel.setBackgroundColor(COLOR_ADD_ERROR);
                    return;
                }
                mBoardViewModel.setBackgroundColor(COLOR_NORMAL);
            }
        }
    }

    @Override
    public void afterTextChanged(@Nullable Editable editable) {
        super.afterTextChanged(editable);
        hideNavigation();
//        mBoardViewModel.setProps(new Board(BoardUtils.row(getText().getValue(), LINEAR_HORIZONTAL)));
//        mBoardViewModel.setBackgroundColor(COLOR_NORMAL);
    }

    @NonNull
    @Override
    public BoardContract.ViewModel getBoardViewModel() {
        return mBoardViewModel;
    }

    @NonNull
    @Override
    public LiveEvent.Mutable<Props> getAddDoneEvent() {
        return mAddDoneEvent;
    }

    @NonNull
    @Override
    public LiveEvent.Mutable<Props> getAddCancelEvent() {
        return mAddCancelEvent;
    }

    public static class Factory extends MVVMViewModel.Factory<AddViewModel> {

        @NonNull
        private final Add mProps;

        @NonNull
        private final BoardViewModel.Factory mRowFactory;

        public Factory(@NonNull final Application application,
                       @NonNull final BoardViewModel.Factory cellFactory,
                       @NonNull final Add props) {
            super(AddViewModel.class, application);
            mProps = Preconditions.requiresNonNull(props, "Props");
            mRowFactory = Preconditions.requiresNonNull(cellFactory, "CellFactory");
        }

        @NonNull
        @Override
        public AddViewModel create() {
            return new AddViewModel(mApplication, mRowFactory, mProps);
        }
    }
}
