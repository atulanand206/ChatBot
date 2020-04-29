package com.creations.mvvm.ui;

import com.creations.mvvm.models.blocks.Add;
import com.creations.mvvm.models.blocks.Board;
import com.creations.mvvm.models.blocks.Cell;
import com.creations.mvvm.models.blocks.ContainerProps;
import com.creations.mvvm.models.blocks.Row;
import com.creations.mvvm.models.blocks.Score;
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
import com.creations.mvvm.utils.BoardUtils;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import dagger.Module;
import dagger.Provides;

import static com.creations.mvvm.utils.BoardUtils.testBoard;

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

    @Provides
    @NonNull
    public static Cell provideCell() {
        return BoardUtils.randomCell();
    }

    @Provides
    @NonNull
    public static Row provideRow() {
        return BoardUtils.randomRow(20);
    }

    @Provides
    @NonNull
    public static Board provideBoard() {
        return testBoard();
    }

    @Provides
    @NonNull
    public static ContainerProps provideContainer(@NonNull final Board board) {
        return new ContainerProps(board);
    }

    @Provides
    @NonNull
    public static Add provideAdd() {
        return BoardUtils.newRow();
    }

    @Provides
    @NonNull
    public static Score provideScore() {
        return new Score();
    }
}
