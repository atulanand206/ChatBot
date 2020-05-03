package com.creations.blogger.api;

import com.creations.blogger.model.blogger.Post;
import com.creations.condition.Info;
import com.creations.tools.callback.ListResponseCallback;

import androidx.annotation.NonNull;

public interface IAPIBlogger {

    void getAirspaces(ListResponseCallback<Info> responseCallback);

    void getBlogPosts(@NonNull ListResponseCallback<Post> callback);
}
