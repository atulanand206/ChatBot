package com.creations.mvvm.ui.blocks;

import android.app.Application;
import android.view.View;

import com.creations.condition.Preconditions;
import com.creations.mvvm.live.MutableLiveData;
import com.creations.mvvm.models.blocks.Cell;
import com.creations.mvvm.ui.recycler.RecyclerViewModel;
import com.creations.mvvm.viewmodel.MVVMViewModel;

import androidx.annotation.ColorInt;
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

    private MutableLiveData<Integer> colorResId = new MutableLiveData<>();

    private MutableLiveData<String> character = new MutableLiveData<>();

    private MutableLiveData<Integer> textColorResId = new MutableLiveData<>();

    private MutableLiveData<Float> textSize = new MutableLiveData<>();

    private MutableLiveData<Float> side = new MutableLiveData<>();

    public CellViewModel(@NonNull final Application application,
                         @NonNull final Cell cell) {
        super(application, cell);
        mApplication = application;
    }

    @Override
    public void setProps(@NonNull final Cell cell) {
        super.setProps(cell);
        setBackgroundColor(cell.getColorResId());
        setCharacter(cell.getCharacter());
        setTextColorResId(mApplication.getResources().getColor(cell.getTextColorResId()));
        setTextSize(mApplication.getResources().getDimension(cell.getTextSize()));
        setSide(mApplication.getResources().getDimension(cell.getSide()));
        setVisibility(View.VISIBLE);
    }

    @Override
    public void setColorResId(@ColorRes final int colorResId) {
        this.colorResId.postValue(colorResId);
    }

    @Override
    public void setCharacter(char character) {
        this.character.postValue(Character.toString(character));
    }

    @Override
    public void setTextColorResId(@ColorInt int textColorResId) {
        this.textColorResId.postValue(textColorResId);
    }

    @Override
    public void setTextSize(@Dimension float textSize) {
        this.textSize.postValue(textSize);
    }

    public void setSide(@Dimension float side) {
        this.side.postValue(side);
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
    public LiveData<Integer> getTextColorResId() {
        return textColorResId;
    }

    @Override
    public LiveData<Float> getTextSize() {
        return textSize;
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
