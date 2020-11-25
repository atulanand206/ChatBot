package com.creations.script.ui.information;

import com.creations.mvvm.models.props.Props;
import com.creations.mvvm.ui.menu.MenuContract;
import com.creations.script.ui.contact.ContactContract;

public interface InformationContract {

    interface ViewModel<T extends Props> extends ContactContract.ViewModel<T> {

    }

}
