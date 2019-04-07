package com.example.k7.koncowy.projekt.projektkoncowy.activities;

import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.k7.koncowy.projekt.projektkoncowy.R;
import com.example.k7.koncowy.projekt.projektkoncowy.customViews.ColorPicker;
import com.example.k7.koncowy.projekt.projektkoncowy.customViews.PreviewText;

import java.io.IOException;

public class LettersActivity extends AppCompatActivity {

    private LinearLayout fonts;
    private RelativeLayout preview;
    private TextWatcher textWatcher;
    private EditText input;
    private Typeface tf;
    private ImageView accept;
    private String fontName;
    private RelativeLayout all;
    private ImageView inside;
    private ImageView edges;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_letters);
        fonts = findViewById(R.id.fonts);
        preview = findViewById(R.id.result);
        input = findViewById(R.id.input);
        tf = Typeface.createFromAsset(getAssets(),"fonts/Pacifico.ttf");
        fontName = "Pacifico.ttf";
        textWatcher = new TextWatcher(){

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                preview.removeAllViews();
                preview.addView(new PreviewText(LettersActivity.this, tf, input.getText().toString()));
            }
        };

        String[] list = {};
        AssetManager assetManager = getAssets();
        try
        {
            list = assetManager.list("fonts");
        }
        catch (IOException e)
        {
        }

        for (final String font :
                list) {
            final Typeface typeface=Typeface.createFromAsset(getAssets(),"fonts/"+font);
            final TextView textView = new TextView(LettersActivity.this);
            textView.setText("zazółć gęślą jaźń");
            textView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 150));
            textView.setTextColor(Color.parseColor("#aaaaaa"));
            textView.setTypeface(typeface);
            textView.setTextSize(50);
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    fontName = font;
                    tf = typeface;
                    preview.removeAllViews();
                    preview.addView(new PreviewText(LettersActivity.this, tf, input.getText().toString()));
                }
            });
            preview.addView(new PreviewText(LettersActivity.this, tf, ""));
            fonts.addView(textView);
            input.addTextChangedListener(textWatcher);
        }
        accept = findViewById(R.id.accept);
        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.putExtra("text", input.getText().toString());
                intent.putExtra("font", fontName);
                setResult(300, intent);
                finish();
            }
        });
        all = findViewById(R.id.all);
        inside = findViewById(R.id.inside);
        edges = findViewById(R.id.edges);
        inside.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("xxx", "click");
                ColorPicker colorPicker = new ColorPicker(LettersActivity.this);
                all.addView(colorPicker);
            }
        });
        edges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("xxx", "click");
                ColorPicker colorPicker = new ColorPicker(LettersActivity.this);
                all.addView(colorPicker);
            }
        });
    }
}
