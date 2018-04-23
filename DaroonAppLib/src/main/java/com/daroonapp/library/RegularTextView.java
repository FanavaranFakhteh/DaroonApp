package com.daroonapp.library;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by Android on 8/31/2016.
 */
    public class RegularTextView extends AppCompatTextView {

        public RegularTextView(Context context, AttributeSet attrs, int defStyle) {
            super(context, attrs, defStyle);
            initialize();
        }

        public RegularTextView(Context context, AttributeSet attrs) {
            super(context, attrs);
            initialize();
        }

        public RegularTextView(Context context) {
            super(context);
            initialize();
        }

        private void initialize() {
            if ( !isInEditMode()) {
                Typeface b = Typeface.createFromAsset(getContext().getAssets(), "yekan_regular.ttf");
                setTypeface(b);
            }
        }
    }

