package com.creations.inception.models.convertor;

import com.creations.blogger.model.blogger.Post;
import com.creations.mvvm.models.props.ImageData;
import com.example.application.utils.DateFormatter;

import java.util.Calendar;

import androidx.annotation.NonNull;

import static com.example.application.constants.ApplicationConstants.FAKE_URL;

public class BloggerConverter {

    public static ImageData imageData(@NonNull final Post post) {
        DateFormatter formatter = DateFormatter.getFromAvailableFormatters(post.getUpdated());
        if (formatter == null)
            return new ImageData(FAKE_URL, post.getTitle(), DateFormatter.INVITATION_DATE.getDateForUi(Calendar.getInstance().getTime()));
        return new ImageData(FAKE_URL, post.getTitle(), DateFormatter.START_END_ADVISORY.getDateForUi(formatter.getDateFromString(post.getUpdated())));
    }
}
