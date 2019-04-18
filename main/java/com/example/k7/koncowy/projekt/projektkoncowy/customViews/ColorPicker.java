package com.example.k7.koncowy.projekt.projektkoncowy.customViews;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.k7.koncowy.projekt.projektkoncowy.domain.ICallback;
import com.example.k7.koncowy.projekt.projektkoncowy.domain.IColorPickerCallback;

public class ColorPicker extends RelativeLayout {
    private RelativeLayout preview;
    private ImageView pattern;
    private ImageView accept;
    private ImageView decline;
    private IColorPickerCallback callback;
    private int color;
    public ColorPicker(Context context, int startingColor, IColorPickerCallback callback) {
        super(context);
        this.callback = callback;
        this.color = startingColor;
        this.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        this.setBackgroundColor(0x88000000);
        this.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });
        addPreviewLayout(startingColor);
        addPattern();
        addAccept();
        addDecline();
    }

    private void addPreviewLayout(int startingColor)
    {
        preview = new RelativeLayout(getContext());
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                200);
        preview.setLayoutParams(params);
        preview.setBackgroundColor(startingColor);
        this.addView(preview);
    }

    @SuppressLint("ResourceType")
    private void addPattern()
    {
        pattern = new ImageView(getContext());
        pattern.setId(156);
        pattern.setImageDrawable(getImageFromResource("@drawable/rgb_pattern"));
        pattern.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        pattern.setDrawingCacheEnabled(true);
        pattern.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction())
                {
                    case MotionEvent.ACTION_MOVE:
                        Bitmap bmp = pattern.getDrawingCache();
                        color = bmp.getPixel((int)motionEvent.getX(), (int)motionEvent.getY());
                        preview.setBackgroundColor(color);
                        break;
                }
                return true;
            }
        });
        this.addView(pattern);
    }

    private void addAccept()
    {
        accept = new ImageView(getContext());
        accept.setImageDrawable(getImageFromResource("@drawable/baseline_done_black_48dp"));
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(150,150);
        params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        accept.setLayoutParams(params);
        accept.setBackgroundColor(0xffffffff);
        accept.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                callback.whenProcessDone(color);
                View theView = (View)decline.getParent();
                ((ViewGroup)theView.getParent()).removeView(theView);
            }
        });
        this.addView(accept);
    }

    private void addDecline() {
        decline = new ImageView(getContext());
        decline.setImageDrawable(getImageFromResource("@drawable/baseline_clear_black_48dp"));
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(150,150);
        params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        decline.setLayoutParams(params);
        decline.setBackgroundColor(0xffffffff);
        decline.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                View theView = (View)decline.getParent();
                ((ViewGroup)theView.getParent()).removeView(theView);
            }
        });
        this.addView(decline);
    }

    private Drawable getImageFromResource(String name)
    {
        int imageResource = getContext().getResources()
                .getIdentifier(name, null,
                        getContext().getPackageName());
        Drawable res = getContext().getResources().getDrawable(imageResource);
        return res;
    }
}
