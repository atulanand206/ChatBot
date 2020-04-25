package com.creations.mvvm.form.navigation;

import android.graphics.drawable.Drawable;

import com.creations.mvvm.form.IFormViewModelBase;
import com.creations.mvvm.models.navigation.NavigationBarProps;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.RecyclerView;

public interface NavigationBarContract {

    interface ViewModel extends IFormViewModelBase {

        /**
         * @return liveData corresponding to the prompt text.
         */
        @NonNull
        String getTitle(final int position);

        Drawable getDrawable(final int position);

        void setProps(@NonNull final NavigationBarProps props);
        Integer getHeaderVisibility();

        /**
         * @return the recycler adapter being used by the teams list.
         */
        @NonNull
        LiveData<RecyclerView.Adapter> getAdapter();

        Integer getLineVisibility(final int position);

        /**
         * @return the list of certificates to be added on the recycler view.
         */
        @NonNull
        NavigationBarProps getProps();

    }

}
