package com.creations.chatbot.ui;

import com.creations.chatbot.callbacks.ObjectResponseCallback;
import com.creations.chatbot.model.APIResponse;
import com.creations.chatbot.model.ListItem;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

@RunWith(JUnit4.class)
public class MainPresenterTest {

    @Mock
    MainContract.View view;

    @Mock
    MainRepository repository;

    MainContract.Presenter presenter;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        presenter = new MainPresenter(view, repository);
        presenter.start();
    }

    @Test
    public void onSendClicked() {
        String message = "Welcome to Chatbot!";
        ListItem item = new ListItem(message);
        APIResponse response = new APIResponse(item);
        ArgumentCaptor<ObjectResponseCallback<APIResponse>>
                sendMessageCaptor = ArgumentCaptor.forClass(
                ObjectResponseCallback.class);

        presenter.onSendClicked(message);

        verify(repository).sendMessage(eq(item),eq(sendMessageCaptor.capture()));
        ObjectResponseCallback<APIResponse> responseCallback = sendMessageCaptor.getValue();
        responseCallback.onSuccess(response);

        verify(view).onItemsLoaded();
    }

    @Test
    public void onReplyReceived() {

        String message = "Welcome to Chatbot!";
        ListItem item = new ListItem(message);
        APIResponse response = new APIResponse(item);
        presenter.onReplyReceived(response);

        verify(view).onItemsLoaded();
    }
}