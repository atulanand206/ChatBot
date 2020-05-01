package com.creations.mvvm.ui.blocks.item;

import android.app.Application;
import android.view.View;

import com.creations.condition.Preconditions;
import com.creations.mvvm.models.blocks.Board;
import com.creations.mvvm.ui.recycler.RecyclerViewModel;
import com.creations.mvvm.viewmodel.MVVMViewModel;

import androidx.annotation.NonNull;

import static com.creations.mvvm.ui.blocks.add.AddContract.ViewModel.COLOR_WHITE;

/**
 * This ViewModel works with a TextInputLayout and is to be used for creating forms.
 */
public class ItemViewModel extends RecyclerViewModel<Board> implements ItemContract.ViewModel<Board> {


    public ItemViewModel(@NonNull final Application application,
                         @NonNull final Board props) {
        super(application, props);
        setProps(props);
    }

    @Override
    public void setProps(@NonNull Board props) {
        super.setProps(props);
        setTitle(props.getName());
        setHeader(props.getBoard());
        setSubHeader(String.valueOf(props.getId()));
        setBackgroundColor(COLOR_WHITE);
        setVisibility(View.VISIBLE);
//                new Observer<LiveRunnable.Sentinel>() {
//            @Override
//            public void onChanged(LiveRunnable.Sentinel sentinel) {
//                if (sentinel != null)
//                    ItemViewModel.this.getPropsEvent().postEvent(props);
//            }
//        }
    }

    @Override
    public void onRecyclerItemClick() {
        super.onRecyclerItemClick();
        getClickEvent().postEvent(getProps());
    }

    public static class Factory extends MVVMViewModel.Factory<ItemViewModel> {

        @NonNull
        private final Board mProps;

        public Factory(@NonNull final Application application,
                       @NonNull final Board props) {
            super(ItemViewModel.class, application);
            mProps = Preconditions.requiresNonNull(props, "Props");
        }

        @NonNull
        @Override
        public ItemViewModel create() {
            return new ItemViewModel(mApplication, mProps);
        }
    }
}
