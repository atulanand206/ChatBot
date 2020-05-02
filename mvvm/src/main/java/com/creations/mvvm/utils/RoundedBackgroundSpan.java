package com.creations.mvvm.utils;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.text.style.ReplacementSpan;

public class RoundedBackgroundSpan extends ReplacementSpan
{
    private final int _padding = 20;
    private int _backgroundColor;
    private int _textColor;
    private int lineSpacing = 20;

    public RoundedBackgroundSpan(int backgroundColor, int textColor) {
        super();
        _backgroundColor = backgroundColor;
        _textColor = textColor;
    }

    @Override
    public int getSize(Paint paint, CharSequence text, int start, int end, Paint.FontMetricsInt fm) {
        paint = new Paint(paint); // make a copy for not editing the referenced paint
        return getTagWidth(text, start, end, paint);
    }

    private int getTagWidth(CharSequence text, int start, int end, Paint paint) {
        return Math.round(_padding + paint.measureText(text.subSequence(start, end).toString()) + _padding);
    }

    @Override
    public void draw(Canvas canvas, CharSequence text, int start, int end, float x, int top, int y, int bottom, Paint paint)
    {
        paint = new Paint(paint); // make a copy for not editing the referenced paint


//        // Draw the rounded background
//        paint.setColor(_backgroundColor);
//        float textHeightWrapping = 20;
//        float tagBottom = top + textHeightWrapping + _padding + 15 + _padding + textHeightWrapping;
//        float tagRight = x + getTagWidth(text, start, end, paint);
//        RectF rect = new RectF(x, top, tagRight, tagBottom);
//        canvas.drawRoundRect(rect, 20, 20, paint);
//
//        // Draw the text
//        paint.setColor(_textColor);
//        canvas.drawText(text, start, end, x + _padding, tagBottom - _padding - textHeightWrapping - 20, paint);

//        float width = paint.measureText(text.subSequence(start, end).toString());
//        float right = x + paint.measureText(text, start, end) + 2 * _padding;
////        Bitmap bitmap = Bitmap.createBitmap((int) width, bottom-top+4*_padding, ALPHA_8);
////        bitmap.setDensity(DisplayMetrics.DENSITY_DEFAULT);
////        canvas = new Canvas(bitmap);
//        RectF back = new RectF(x, top+4*_padding , right, bottom + 8*_padding);
//        paint.setColor(_textColor);
//        canvas.drawRoundRect(back, 20, 20 ,paint);
//        RectF rect = new RectF(x,
//                top + 6*_padding,
//                right,
//                bottom+6*_padding);
//        paint.setColor(_backgroundColor);
//        canvas.drawRoundRect(rect, 20, 20, paint);
//        paint.setColor(_textColor);
//        canvas.drawText(text, start, end, x+_padding, y+_padding*2, paint);

    }
}