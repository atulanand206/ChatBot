package com.creations.blogger;

import com.creations.blogger.callback.ListResponseCallback;
import com.creations.blogger.model.blogger.Post;
import com.creations.condition.Info;

import androidx.annotation.NonNull;

public interface IAPIChat {

    void getAirspaces(ListResponseCallback<Info> responseCallback);

    void getBlogPosts(@NonNull ListResponseCallback<Post> callback);
}
