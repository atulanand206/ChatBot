package com.creations.mvvm.utils;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import com.creations.blogger.callback.EmptyResponseCallback;
import com.creations.mvvm.models.props.ImageData;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class ImageLoadTask extends AsyncTask<Void, Void, Bitmap> {

    private ImageData imageData;
    @SuppressLint("StaticFieldLeak")
    private ImageView imageView;

    public ImageLoadTask(ImageView imageView, ImageData imageData) {
        this.imageData = imageData;
        this.imageView = imageView;
    }

    @Override
    protected Bitmap doInBackground(Void... params) {
        try {
            URL urlConnection = new URL(imageData.getUrl());
            HttpURLConnection connection = (HttpURLConnection) urlConnection
                    .openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            return BitmapFactory.decodeStream(input);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Bitmap result) {
        super.onPostExecute(result);
        imageView.setImageBitmap(result);
        if (imageData == null)
            return;
        EmptyResponseCallback imageLoadCallback = imageData.getImageLoadCallback();
        if (imageLoadCallback == null)
            return;
        imageLoadCallback.onSuccess();
    }

}