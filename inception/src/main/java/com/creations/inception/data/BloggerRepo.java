package com.creations.inception.data;

import com.creations.mvvm.constants.IAPIChat;

public class BloggerRepo {

    private static final String TAG = BloggerRepo.class.getSimpleName();

    private IAPIChat mApiChat;

    public BloggerRepo(IAPIChat apiChat) {
        mApiChat = apiChat;
    }

    public void getPosts() {
    }
}
