package com.example.k7.koncowy.projekt.projektkoncowy.customViews;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class ColorPicker extends RelativeLayout {
    private RelativeLayout preview;
    private ImageView pattern;
    private ImageView accept;
    private ImageView decline;
    public ColorPicker(Context context) {
        super(context);
        this.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        this.setBackgroundColor(0x88000000);
        this.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });
        addPreviewLayout();

    }

    private void addPreviewLayout()
    {
        preview = new RelativeLayout(getContext());
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                200);
        //params.addRule(;);
        //preview.setLayoutParams();
        preview.setBackgroundColor(Color.parseColor("#ff00ff"));
        this.addView(preview);
    }
}
