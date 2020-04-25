package com.creations.inception.di;

import com.creations.mvvm.form.checkbox.CheckboxModule;
import com.creations.mvvm.form.contact.ContactModule;
import com.creations.mvvm.form.editable.EditableModule;
import com.creations.mvvm.form.image.ImageModule;
import com.creations.mvvm.form.navigation.NavigationBarModule;
import com.creations.mvvm.form.spinner.SpinnerModule;

import dagger.Module;

@Module(includes = {
        EditableModule.class,
        SpinnerModule.class,
        ContactModule.class,
        CheckboxModule.class,
        NavigationBarModule.class,
        ImageModule.class
})
public class ViewModelModule {

}
