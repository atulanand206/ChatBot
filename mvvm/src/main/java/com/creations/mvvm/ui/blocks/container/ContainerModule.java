package com.creations.mvvm.ui.blocks.container;

import com.creations.condition.Preconditions;
import com.creations.mvvm.constants.IAPIChat;
import com.creations.mvvm.models.blocks.ContainerProps;
import com.creations.mvvm.ui.blocks.add.AddModule;
import com.creations.mvvm.ui.blocks.add.AddViewModel;
import com.creations.mvvm.ui.blocks.board.BoardModule;
import com.creations.mvvm.ui.blocks.board.BoardViewModel;
import com.creations.mvvm.ui.blocks.done.DoneModule;
import com.creations.mvvm.ui.blocks.done.DoneViewModel;
import com.creations.mvvm.ui.blocks.preset.PresetModule;
import com.creations.mvvm.ui.blocks.preset.PresetViewModel;
import com.creations.mvvm.ui.blocks.score.ScoreModule;
import com.creations.mvvm.ui.blocks.score.ScoreViewModel;
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
        PresetModule.class
})
public interface ContainerModule extends MenuModule {

    @Module(includes = {
            BoardModule.InjectViewModelFactory.class,
            AddModule.InjectViewModelFactory.class,
            ScoreModule.InjectViewModelFactory.class,
            DoneModule.InjectViewModelFactory.class,
            PresetModule.InjectViewModelFactory.class
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
                @NonNull final IAPIChat iapiChat,
                @NonNull final ContainerProps props,
                @NonNull final JsonConvertor jsonConvertor,
                @NonNull final IMessageManager messageManager) {
            Preconditions.requiresNonNull(activity, "FragmentActivity");
            Preconditions.requiresNonNull(props, "Props");

            return new ContainerViewModel.Factory(activity.getApplication(),
                    addFactory, boardFactory, scoreFactory, doneFactory,
                    presetFactory, iapiChat, jsonConvertor, props);
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