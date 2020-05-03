package com.creations.blocks.di;

import com.creations.blocks.models.Add;
import com.creations.blocks.models.Board;
import com.creations.blocks.models.Cell;
import com.creations.blocks.models.ContainerProps;
import com.creations.blocks.models.Done;
import com.creations.blocks.models.Home;
import com.creations.blocks.models.Options;
import com.creations.blocks.models.Row;
import com.creations.blocks.models.Score;
import com.creations.blocks.models.ScoreItem;
import com.creations.blocks.models.Scores;
import com.creations.blocks.models.Word;
import com.creations.blocks.utils.BoardUtils;
import com.creations.blogger.model.navigation.NavigationBarProps;
import com.creations.blogger.model.navigation.NavigationItem;
import com.creations.blogger.model.navigation.NavigationLabel;
import com.creations.blogger.model.navigation.NavigationState;
import com.creations.mvvm.models.ImageData;
import com.creations.mvvm.models.props.ButtonProps;
import com.creations.mvvm.models.props.DateRangeProps;
import com.creations.mvvm.models.props.DrawerProps;
import com.creations.mvvm.models.props.EditableProps;
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

    @Provides
    @NonNull
    public static Cell provideCell() {
        return new Cell('a');
    }

    @Provides
    @NonNull
    public static Row provideRow() {
        return new Row();
    }

    @Provides
    @NonNull
    public static Word provideWord() {
        return new Word();
    }

    @Provides
    @NonNull
    public static Board provideBoard() {
        return new Board();
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

    @Provides
    @NonNull
    public static ScoreItem provideScoreItem() {
        return new ScoreItem();
    }

    @Provides
    @NonNull
    public static Scores provideScores() {
        return new Scores();
    }

    @Provides
    @NonNull
    public static Done provideDone() {
        return new Done();
    }

    @Provides
    @NonNull
    public static Options provideOptions() {
        return new Options();
    }

    @Provides
    @NonNull
    public static Home provideHome() {
        return new Home();
    }

}
