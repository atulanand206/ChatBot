package com.creations.blocks.ui.cell;

import android.app.Application;
import android.view.View;

import com.creations.blocks.models.Cell;
import com.creations.blocks.ui.add.AddContract;
import com.creations.condition.Preconditions;
import com.creations.mvvm.live.LiveRunnable;
import com.creations.mvvm.live.MutableLiveData;
import com.creations.mvvm.ui.recycler.RecyclerViewModel;
import com.creations.mvvm.viewmodel.MVVMViewModel;

import androidx.annotation.ColorRes;
import androidx.annotation.Dimension;
import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

/**
 * This ViewModel works with a TextInputLayout and is to be used for creating forms.
 */
public class CellViewModel extends RecyclerViewModel<Cell> implements CellContract.ViewModel<Cell> {

    @NonNull
    private Application mApplication;

    private MutableLiveData<Integer> cellColor = new MutableLiveData<>(AddContract.ViewModel.COLOR_NORMAL);

    private MutableLiveData<Integer> colorResId = new MutableLiveData<>();

    private MutableLiveData<String> character = new MutableLiveData<>();

    private MutableLiveData<Float> side = new MutableLiveData<>();

    @NonNull
    private final LiveRunnable.Mutable mAddEvent = new LiveRunnable.Mutable();

    @NonNull
    private final LiveRunnable.Mutable mSelectionToggleEvent = new LiveRunnable.Mutable();

    public CellViewModel(@NonNull final Application application,
                         @NonNull final Cell cell) {
        super(application, cell);
        mApplication = application;
    }

    @Override
    public void setProps(@NonNull final Cell cell) {
        super.setProps(cell);
        setClickable(cell.isClickable());
        setCharacter(cell.getCharacter());
        setTextColorResId(mApplication.getResources().getColor(cell.getTextColorResId()));
        setTextSize(mApplication.getResources().getDimension(cell.getTextSize()));
        setSide(mApplication.getResources().getDimension(cell.getSide()));
        setCellColor(cell.getColorResId());
        setColorResId(cell.getColorResId());
        setBackgroundColor(cell.getColorResId());
        setVisibility(View.VISIBLE);
    }

    @Override
    public void onClick() {
        Boolean clickable = isClickable().getValue();
        if (clickable != null && clickable) {
            switch (getProps().getType()) {
                case Cell.Type.ADD:
                    showAddDialog();
                    return;
                case Cell.Type.FULL:
                    switch (getProps().getState()) {
                        case Cell.State.COLORS:
                            shuffle(true);
                            getRefreshEvent().postEvent();
                            return;
                        case Cell.State.SELECTED:
                            deselect();
                            return;
                        case Cell.State.NOT_SELECTED:
                            select();
                            return;
                    }
                    break;
            }
        }
        super.onClick();
    }

    @Override
    public void shuffle(final boolean shuffle) {
        super.shuffle(shuffle);
    }

    private void select() {
        getClickEvent().postEvent(getProps());
    }

    private void deselect() {
        getClickEvent().postEvent(getProps());
    }

    private void showAddDialog() {
        mAddEvent.postEvent();
    }

    @NonNull
    @Override
    public LiveRunnable.Mutable getAddEvent() {
        return mAddEvent;
    }

    @NonNull
    @Override
    public LiveRunnable.Mutable getSelectionEvent() {
        return mSelectionToggleEvent;
    }

    @Override
    public void setColorResId(@ColorRes final int colorResId) {
        this.colorResId.postValue(colorResId);
    }

    @Override
    public void setCharacter(char character) {
        this.character.postValue(Character.toString(character));
    }

    public void setSide(@Dimension float side) {
        this.side.postValue(side);
    }

    @Override
    public LiveData<Integer> getCellColor() {
        return cellColor;
    }

    @Override
    public void setCellColor(final int color) {
        cellColor.postValue(color);
    }

    @Override
    public LiveData<Integer> getColorResId() {
        return colorResId;
    }

    @Override
    public LiveData<String> getCharacter() {
        return character;
    }

    @Override
    public LiveData<Float> getSide() {
        return side;
    }

    public static class Factory extends MVVMViewModel.Factory<CellViewModel> {

        @NonNull
        private final Cell mCell;

        public Factory(@NonNull final Application application,
                       @NonNull final Cell cell) {
            super(CellViewModel.class, application);
            mCell = Preconditions.requiresNonNull(cell, "Cell");
        }

        @NonNull
        @Override
        public CellViewModel create() {
            return new CellViewModel(mApplication, mCell);
        }
    }
}
