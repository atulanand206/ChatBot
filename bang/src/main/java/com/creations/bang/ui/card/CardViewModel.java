package com.creations.bang.ui.card;

import android.app.Application;

import com.creations.bang.models.Card;
import com.creations.condition.Preconditions;
import com.creations.mvvm.ui.recycler.RecyclerViewModel;
import com.creations.mvvm.viewmodel.MVVMViewModel;

import androidx.annotation.NonNull;

import static android.view.View.VISIBLE;

/**
 * This ViewModel works with a TextInputLayout and is to be used for creating forms.
 */
public class CardViewModel extends RecyclerViewModel<Card> implements CardContract.ViewModel<Card> {


    public CardViewModel(@NonNull final Application application,
                         @NonNull final Card props) {
        super(application, props);
        setVisibility(VISIBLE);

    }

    public static class Factory extends MVVMViewModel.Factory<CardViewModel> {

        @NonNull
        private final Card mProps;

        public Factory(@NonNull final Application application,
                       @NonNull final Card props) {
            super(CardViewModel.class, application);
            mProps = Preconditions.requiresNonNull(props, "Props");
        }

        @NonNull
        @Override
        public CardViewModel create() {
            return new CardViewModel(mApplication, mProps);
        }
    }
}
