package com.example.k7.koncowy.projekt.projektkoncowy.customViews;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.view.View;

public class PreviewText extends View {
    private Paint paint;
    private String text;
    public PreviewText(Context context, Typeface tp, String text) {
        super(context);
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.reset();
        paint.setAntiAlias(true);
        paint.setTextSize(100);
        paint.setTypeface(tp);
        this.text = text;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.RED);
        canvas.drawText(text, 0, 100, paint);
    }
}
