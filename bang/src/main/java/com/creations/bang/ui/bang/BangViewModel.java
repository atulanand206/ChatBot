package com.creations.bang.ui.bang;

import android.app.Application;

import com.creations.bang.R;
import com.creations.bang.databinding.CardBaseBinding;
import com.creations.bang.models.Bang;
import com.creations.bang.ui.card.CardAdapter;
import com.creations.bang.ui.card.CardContract;
import com.creations.bang.ui.card.CardViewModel;
import com.creations.condition.Preconditions;
import com.creations.mvvm.ui.menu.MenuViewModel;
import com.creations.mvvm.viewmodel.MVVMViewModel;

import androidx.annotation.NonNull;

import static android.view.View.VISIBLE;

/**
 * This ViewModel works with a TextInputLayout and is to be used for creating forms.
 */
public class BangViewModel extends MenuViewModel<Bang> implements BangContract.ViewModel<Bang> {

    private CardAdapter<CardContract.ViewModel, CardBaseBinding> cardAdapter = new CardAdapter<>(viewModel -> {

    }, R.layout.card_base);

    public BangViewModel(@NonNull final Application application,
                         @NonNull final CardViewModel.Factory factory,
                         @NonNull final Bang props) {
        super(application, props);
        setVisibility(VISIBLE);
        cardAdapter.addItem(factory.create());
        cardAdapter.addItem(factory.create());
        cardAdapter.addItem(factory.create());
        cardAdapter.addItem(factory.create());
        cardAdapter.notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CardAdapter getCardAdapter() {
        return cardAdapter;
    }

    public static class Factory extends MVVMViewModel.Factory<BangViewModel> {

        @NonNull
        private final Bang mProps;

        @NonNull
        private final CardViewModel.Factory mFactory;

        public Factory(@NonNull final Application application,
                       @NonNull final CardViewModel.Factory factory,
                       @NonNull final Bang props) {
            super(BangViewModel.class, application);
            mFactory = Preconditions.requiresNonNull(factory, "Factory");
            mProps = Preconditions.requiresNonNull(props, "Props");
        }

        @NonNull
        @Override
        public BangViewModel create() {
            return new BangViewModel(mApplication, mFactory, mProps);
        }
    }
}
