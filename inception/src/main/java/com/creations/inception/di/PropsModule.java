package com.creations.inception.di;

import com.creations.mvvm.models.navigation.NavigationBarProps;
import com.creations.mvvm.models.navigation.NavigationItem;
import com.creations.mvvm.models.navigation.NavigationLabel;
import com.creations.mvvm.models.navigation.NavigationState;
import com.creations.mvvm.models.props.ButtonProps;
import com.creations.mvvm.models.props.DateRangeProps;
import com.creations.mvvm.models.props.DrawerProps;
import com.creations.mvvm.models.props.EditableProps;
import com.creations.mvvm.models.props.ImageData;
import com.creations.mvvm.models.props.SpinnerProps;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import dagger.Module;
import dagger.Provides;

@Module
public class PropsModule {

    @Provides
    @NonNull
    public static DateRangeProps provideDateRangeProps() {
        return new DateRangeProps(false);
    }

    @Provides
    @NonNull
    public static EditableProps provideEditableProps() {
        return new EditableProps(false);
    }

    @Provides
    @NonNull
    public static SpinnerProps provideSpinnerProps() {
        return new SpinnerProps(false);
    }

    @Provides
    @NonNull
    public static ButtonProps provideButtonProps() {
        return new ButtonProps("");
    }

    @Provides
    @NonNull
    public static ImageData provideImageData() {
        return new ImageData();
    }

    @Provides
    @NonNull
    public static DrawerProps provideDrawableProps() {
        return new DrawerProps(new ArrayList<>());
    }

    @Provides
    @NonNull
    public static NavigationBarProps provideNavigationProps() {
        return new NavigationBarProps.Builder()
                .withNOVICE(NavigationState.COMPLETED)
                .withINTERMEDIATE(NavigationState.CURRENT)
                .withADVANCED(NavigationState.NOT_YET_OPENED)
                .build();
    }

    @Provides
    @NonNull
    public static NavigationItem provideNavigationItem() {
        return new NavigationItem(NavigationLabel.ADVANCED, NavigationState.COMPLETED);
    }
}
