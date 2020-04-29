package com.creations.mvvm.ui.blocks.score;

import android.app.Application;

import com.creations.condition.Preconditions;
import com.creations.mvvm.R;
import com.creations.mvvm.models.blocks.Score;
import com.creations.mvvm.ui.menu.MenuViewModel;
import com.creations.mvvm.viewmodel.MVVMViewModel;

import androidx.annotation.NonNull;

/**
 * This ViewModel works with a TextInputLayout and is to be used for creating forms.
 */
public class ScoreViewModel extends MenuViewModel<Score> implements ScoreContract.ViewModel<Score> {

    public ScoreViewModel(@NonNull final Application application,
                          @NonNull final Score props) {
        super(application, props);
        setProps(props);
    }

    @Override
    public void setProps(@NonNull Score props) {
        super.setProps(props);
        setText(String.valueOf(props.score()));
        setTextSize(mApplication.getResources().getDimension(R.dimen.font_xxxx_large));
        setTextColorResId(mApplication.getResources().getColor(R.color.colorPrimary));
    }

    @Override
    public void add(final String lengthOfWord) {
        setText(String.valueOf(getProps().add()));
    }

    public static class Factory extends MVVMViewModel.Factory<ScoreViewModel> {

        @NonNull
        private final Score mProps;

        public Factory(@NonNull final Application application,
                       @NonNull final Score props) {
            super(ScoreViewModel.class, application);
            mProps = Preconditions.requiresNonNull(props, "Props");
        }

        @NonNull
        @Override
        public ScoreViewModel create() {
            return new ScoreViewModel(mApplication, mProps);
        }
    }
}
