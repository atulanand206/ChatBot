package com.creations.inception.di;

import com.creations.blocks.ui.container.ContainerModule;
import com.creations.blogger.ui.blogger.BloggerModule;
import com.creations.blogger.ui.navigation.NavigationBarModule;
import com.creations.mvvm.ui.checkbox.CheckboxModule;
import com.creations.mvvm.ui.contact.ContactModule;
import com.creations.mvvm.ui.drawer.DrawerModule;
import com.creations.mvvm.ui.editable.EditableModule;
import com.creations.mvvm.ui.image.ImageModule;
import com.creations.mvvm.ui.spinner.SpinnerModule;

import dagger.Module;

@Module(includes = {
        EditableModule.class,
        SpinnerModule.class,
        ContactModule.class,
        CheckboxModule.class,
        NavigationBarModule.class,
        ImageModule.class,
        BloggerModule.class,
        DrawerModule.class,
        ContainerModule.class,
})
public class ViewModelModule {

}
