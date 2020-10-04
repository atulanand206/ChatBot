package com.example.application.messages;

import android.content.Context;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.application.R;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Objects;

import androidx.annotation.IntDef;
import androidx.annotation.NonNull;

import static android.widget.Toast.LENGTH_LONG;
import static android.widget.Toast.LENGTH_SHORT;

public class ToastUtils {

    @IntDef({LENGTH_SHORT, LENGTH_LONG})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Duration {}

    private final Context context;

    public ToastUtils(@NonNull Context context) {
        Objects.requireNonNull(context, "Context");

        this.context = context;
    }

    public void showToast(int resId, @NonNull MessageType messageType,
                          @Duration int messageLength, int snackBarHeight) {
        Objects.requireNonNull(messageType, "MessageType");
        showToast(context.getString(resId), messageType, messageLength, snackBarHeight);
    }

    public void showToast(@NonNull String message, @NonNull MessageType messageType,
                          @Duration int messageLength, int snackbarHeight) {
        Objects.requireNonNull(message, "Message");
        Objects.requireNonNull(messageType, "MessageType");

        Toast toast = new Toast(context);
        toast.setDuration(messageLength);

        LinearLayout layout = new LinearLayout(context);

        ImageView imageView = new ImageView(context);
        imageView.setPadding(10, 0, 10, 0);

        TextView textView = new TextView(context);
        textView.setText(message);
        textView.setTextColor(context.getColor(R.color.white));

        switch (messageType) {
            case FAILURE:
                imageView.setImageResource(R.drawable.ic_error_white_17dp);
                layout.setBackground(context.getDrawable(R.drawable.toast_rounded_red));
                break;
            case SUCCESS:
                imageView.setImageResource(R.drawable.ic_check_white_17dp);
                layout.setBackground(context.getDrawable(R.drawable.toast_rounded_green));
                break;
            case PROGRESS:
                imageView.setImageResource(R.drawable.ic_sync_white_17dp);
                layout.setBackground(context.getDrawable(R.drawable.toast_rounded_progress));
                break;
            case INFO:
                imageView.setImageResource(R.drawable.ic_check_white_17dp);
                layout.setBackground(context.getDrawable(R.drawable.toast_rounded_blue));
                break;
        }

        layout.addView(imageView);
        layout.addView(textView);

        int minimumSpace = (int)context.getResources().getDimension(R.dimen.view_minimum_space);
        toast.setGravity(Gravity.BOTTOM,0, snackbarHeight + minimumSpace);

        toast.setView(layout);

        toast.show();
    }

}
