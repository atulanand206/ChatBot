package com.creations.blocks.ui.container;

import com.creations.blocks.api.IAPIBlocks;
import com.creations.blocks.models.ContainerProps;
import com.creations.blocks.ui.add.AddModule;
import com.creations.blocks.ui.add.AddViewModel;
import com.creations.blocks.ui.board.BoardModule;
import com.creations.blocks.ui.board.BoardViewModel;
import com.creations.blocks.ui.done.DoneModule;
import com.creations.blocks.ui.done.DoneViewModel;
import com.creations.blocks.ui.home.HomeModule;
import com.creations.blocks.ui.home.HomeViewModel;
import com.creations.blocks.ui.preset.PresetModule;
import com.creations.blocks.ui.preset.PresetViewModel;
import com.creations.blocks.ui.score.ScoreModule;
import com.creations.blocks.ui.score.ScoreViewModel;
import com.creations.blocks.ui.scoreList.ScoreListModule;
import com.creations.blocks.ui.scoreList.ScoreListViewModel;
import com.creations.condition.Preconditions;
import com.creations.mvvm.ui.menu.MenuModule;
import com.creations.mvvm.viewmodel.MVVMModule;
import com.creations.tools.utils.JsonConvertor;
import com.example.application.messages.IMessageManager;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProviders;
import dagger.Binds;
import dagger.Module;
import dagger.Provides;

@Module(includes = {
        BoardModule.class,
        AddModule.class,
        ScoreModule.class,
        DoneModule.class,
        PresetModule.class,
        ScoreListModule.class,
        HomeModule.class
})
public interface ContainerModule extends MenuModule {

    @Module(includes = {
            BoardModule.InjectViewModelFactory.class,
            AddModule.InjectViewModelFactory.class,
            ScoreModule.InjectViewModelFactory.class,
            DoneModule.InjectViewModelFactory.class,
            PresetModule.InjectViewModelFactory.class,
            ScoreListModule.InjectViewModelFactory.class,
            HomeModule.InjectViewModelFactory.class
    })
    abstract class InjectViewModelFactory {
        @Provides
        @NonNull
        public static ContainerViewModel.Factory provideViewModelFactory(
                @NonNull final FragmentActivity activity,
                @NonNull final BoardViewModel.Factory boardFactory,
                @NonNull final AddViewModel.Factory addFactory,
                @NonNull final ScoreViewModel.Factory scoreFactory,
                @NonNull final DoneViewModel.Factory doneFactory,
                @NonNull final PresetViewModel.Factory presetFactory,
                @NonNull final ScoreListViewModel.Factory scoreListFactory,
                @NonNull final HomeViewModel.Factory homeFactory,
                @NonNull final IAPIBlocks iapiBlocks,
                @NonNull final ContainerProps props,
                @NonNull final JsonConvertor jsonConvertor,
                @NonNull final IMessageManager messageManager) {
            Preconditions.requiresNonNull(activity, "FragmentActivity");
            Preconditions.requiresNonNull(props, "Props");

            return new ContainerViewModel.Factory(activity.getApplication(),
                    addFactory, boardFactory, scoreFactory, doneFactory,
                    presetFactory, scoreListFactory, homeFactory,
                    iapiBlocks, jsonConvertor, props);
        }
    }

    @Module(includes = InjectViewModelFactory.class)
    abstract class InjectViewModel extends MVVMModule.InjectViewModel<ContainerContract.ViewModel,
            ContainerViewModel> {

        @Provides
        @NonNull
        static ContainerViewModel provideViewModel(
                @NonNull final ContainerViewModel.Factory factory,
                @NonNull final FragmentActivity application) {
            Preconditions.requiresNonNull(factory, "ViewModelFactory");
            Preconditions.requiresNonNull(application, "FragmentActivity");

            ContainerViewModel viewModel = ViewModelProviders.of(application, factory).get(ContainerViewModel.class);
            return Preconditions.requiresNonNull(viewModel, "ViewModel");
        }

        @Binds
        @NonNull
        abstract ContainerContract.ViewModel bindViewModel(@NonNull final ContainerViewModel viewModel);
    }

}