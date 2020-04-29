package com.creations.mvvm.ui.blocks.add;

import android.app.Application;
import android.text.Editable;

import com.creations.condition.Preconditions;
import com.creations.mvvm.live.LiveEvent;
import com.creations.mvvm.models.blocks.Add;
import com.creations.mvvm.models.blocks.Board;
import com.creations.mvvm.models.blocks.Row;
import com.creations.mvvm.models.props.Props;
import com.creations.mvvm.ui.blocks.board.BoardContract;
import com.creations.mvvm.ui.blocks.board.BoardViewModel;
import com.creations.mvvm.ui.recycler.RecyclerViewModel;
import com.creations.mvvm.utils.BoardUtils;
import com.creations.mvvm.viewmodel.MVVMViewModel;
import com.example.application.utils.RecyclerUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import static android.view.View.GONE;

/**
 * This ViewModel works with a TextInputLayout and is to be used for creating forms.
 */
public class AddViewModel extends RecyclerViewModel<Add> implements AddContract.ViewModel<Add> {

    @NonNull
    private final BoardViewModel mBoardViewModel;

    @NonNull
    private final LiveEvent.Mutable<Props> mAddDoneEvent = new LiveEvent.Mutable<>();

    public AddViewModel(@NonNull final Application application,
                        @NonNull final BoardViewModel.Factory rowFactory,
                        @NonNull final Add props) {
        super(application, props);
        setVisibility(GONE);
        mBoardViewModel = rowFactory.create();
        setProps(props);
    }

    @Override
    public void setProps(@NonNull Add props) {
        super.setProps(props);
        mBoardViewModel.setProps(new Board(props.getRows()));
        setText("");
    }

    @Override
    public void onClick(@NonNull Object object) {
        if (object instanceof Integer){
            if (object.equals(CLICK_BACKGROUND))
                setVisibility(GONE);
            else if (object.equals(CLICK_ADD_BUTTON)) {
                Row row = BoardUtils.row(getText().getValue());
                row.setLayoutType(RecyclerUtils.LayoutType.LINEAR_HORIZONTAL);
                setProps(new Add(row));
            }
        }
    }

    @Override
    public void afterTextChanged(@Nullable Editable editable) {
        super.afterTextChanged(editable);
        mBoardViewModel.setProps(new Board(BoardUtils.row(getText().getValue())));
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