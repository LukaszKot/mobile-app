package com.example.k7.koncowy.projekt.projektkoncowy.customViews;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.os.Debug;
import android.util.Log;
import android.view.View;

public class PreviewText extends View {
    private Paint paint;
    private String text;
    private int mainColor;
    private int edgesColor;
    public Rect rect;
    public PreviewText(Context context, Typeface tp, String text, int mainColor, int edgesColor) {
        super(context);
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.reset();
        paint.setAntiAlias(true);
        paint.setTextSize(100);
        paint.setTypeface(tp);
        this.text = text;
        this.mainColor = mainColor;
        this.edgesColor = edgesColor;
        rect = new Rect();
        paint.getTextBounds(text, 0, text.length(), rect);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(mainColor);
        canvas.drawText(text, 0, 100, paint);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(5);
        paint.setColor(edgesColor);
        canvas.drawText(text, 0, 100, paint);
        rect = new Rect();
        paint.getTextBounds(text, 0, text.length(), rect);
    }
}
