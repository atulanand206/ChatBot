package com.creations.inception.models.convertor;

import com.creations.blogger.model.blogger.Post;
import com.creations.mvvm.models.props.ImageData;

import androidx.annotation.NonNull;

public class BloggerConverter {

    public static ImageData imageData(@NonNull final Post post) {
        return new ImageData(post.getUrl(), post.getTitle(), post.getContent());
    }
}
