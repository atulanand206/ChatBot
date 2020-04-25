package com.example.application.utils;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.text.Editable;
import android.text.Spanned;
import android.text.style.ReplacementSpan;

import com.creations.condition.Preconditions;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import static com.example.application.constants.ApplicationConstants.Phone.COUNTRY_CODE;

/**
 * A {@link ReplacementSpan} used for spacing in {@link android.widget.EditText}
 * to space things out. Inserts ' ' and '-'s after a certain number of characters.
 * This class is to be used only for displaying the text
 * with spaces and not change the actual value.
 */
public class PhoneSpan extends ReplacementSpan {

    /**
     * This contains the dividing character. Must be a single character.
     */
    @NonNull
    private String mDivider;

    public PhoneSpan(@NonNull final String divider) {
        Preconditions.requiresNonNull(divider, "Divider");
        mDivider = divider;
    }

    @Override
    public int getSize(@NonNull final Paint paint, @NonNull final CharSequence text,
                       final int start, final int end, @Nullable final Paint.FontMetricsInt fm) {
        Preconditions.requiresNonNull(paint, "Paint");
        Preconditions.requiresNonNull(text, "Text");

        float padding = paint.measureText(mDivider, 0, mDivider.length());
        float textSize = paint.measureText(text, start, end);
        return (int) (padding + textSize);
    }

    @Override
    public void draw(@NonNull final Canvas canvas, @NonNull final CharSequence text,
                     final int start, final int end, final float x, final int top, final int y,
                     int bottom, @NonNull Paint paint) {
        Preconditions.requiresNonNull(canvas, "Canvas");
        Preconditions.requiresNonNull(text, "Text");
        Preconditions.requiresNonNull(paint, "Paint");

        canvas.drawText(text.subSequence(start, end) + mDivider, x, y, paint);
    }

    /**
     * Provide the capability for the Android Edit Text to format the
     * phone number in the text view by supplying a factory.
     */
    public static class Factory {

        /**
         * This method adds hyphens in the phone number characters.
         * @param editable charSequence information about the entered text.
         */
        void addSpans(@NonNull final Editable editable) {
            Preconditions.requiresNonNull(editable, "Editable");

            int codeLength = COUNTRY_CODE.length();
            if (editable.length()>codeLength)
                editable.setSpan(new PhoneSpan(" "), codeLength-1,  codeLength, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            if (editable.length()>codeLength+3)
                editable.setSpan(new PhoneSpan("-"), codeLength+2,  codeLength+3, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            if (editable.length()>codeLength+6)
                editable.setSpan(new PhoneSpan("-"), codeLength+5,  codeLength+6, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }

        /**
         * This method is called to insert hyphens and spaces in the phone number.
         * This method does not have a return type as the spans will be set to
         * editable here itself without any need to change the editable externally.
         * @param editable charSequence information about the entered text.
         */
        public String spacedEditable(@NonNull final Editable editable) {
            Preconditions.requiresNonNull(editable, "Editable");

            Object[] paddingSpans = editable.getSpans(0, editable.length(), PhoneSpan.class);
            for (Object span : paddingSpans) {
                editable.removeSpan(span);
            }
            addSpans(editable);
            return editable.toString();
        }
    }

    public static String withPhoneFormat(@Nullable final String phoneNumber) {
        try {
            if (phoneNumber == null)
                return "";
            int codeLength = COUNTRY_CODE.length();
            String firstGroup = getString(phoneNumber, codeLength, codeLength+3);
            String secondGroup = getString(phoneNumber, codeLength+3, codeLength+6);
            String thirdGroup = getString(phoneNumber, codeLength+6, codeLength+10);
            if (firstGroup.length() == 0)
                return "";
            if (secondGroup.length() == 0)
                return String.format("%s %s", COUNTRY_CODE, firstGroup);
            if (thirdGroup.length() == 0)
                return String.format("%s %s-%s", COUNTRY_CODE, firstGroup, secondGroup);
            return String.format("%s %s-%s-%s", COUNTRY_CODE, firstGroup, secondGroup, thirdGroup);
        } catch (Exception e) {
            return "NA";
        }
    }

    @NonNull
    private static String getString(@NonNull final String str, final int start, final int end) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i=start; i<end; i++) {
            if (str.length()<=i)
                break;
            stringBuilder.append(str.charAt(i));
        }
        return stringBuilder.toString();
    }
}