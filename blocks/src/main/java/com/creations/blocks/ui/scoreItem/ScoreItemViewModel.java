package com.creations.blocks.ui.scoreItem;

import android.app.Application;
import android.view.View;

import com.creations.blocks.models.ScoreItem;
import com.creations.condition.Preconditions;
import com.creations.mvvm.ui.recycler.RecyclerViewModel;
import com.creations.mvvm.viewmodel.MVVMViewModel;

import androidx.annotation.NonNull;

/**
 * This ViewModel works with a TextInputLayout and is to be used for creating forms.
 */
public class ScoreItemViewModel extends RecyclerViewModel<ScoreItem> implements ScoreItemContract.ViewModel<ScoreItem> {


    public ScoreItemViewModel(@NonNull final Application application,
                              @NonNull final ScoreItem props) {
        super(application, props);
    }

    @Override
    public void setProps(@NonNull ScoreItem props) {
        super.setProps(props);
        setTitle(props.getName());
        setText(props.getBoardname());
        setSubHeader(String.valueOf(props.getScore()));
        setVisibility(View.VISIBLE);
    }

    public static class Factory extends MVVMViewModel.Factory<ScoreItemViewModel> {

        @NonNull
        private final ScoreItem mProps;

        public Factory(@NonNull final Application application,
                       @NonNull final ScoreItem props) {
            super(ScoreItemViewModel.class, application);
            mProps = Preconditions.requiresNonNull(props, "Props");
        }

        @NonNull
        @Override
        public ScoreItemViewModel create() {
            return new ScoreItemViewModel(mApplication, mProps);
        }
    }
}
