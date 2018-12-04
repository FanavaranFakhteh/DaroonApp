package com.daroonapp.library.Fonts;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by Android on 8/31/2016.
 */
    public class BoldTextView extends TextView {

        public BoldTextView(Context context, AttributeSet attrs, int defStyle) {
            super(context, attrs, defStyle);
            initialize();
        }

        public BoldTextView(Context context, AttributeSet attrs) {
            super(context, attrs);
            initialize();
        }

        public BoldTextView(Context context) {
            super(context);
            initialize();
        }

        private void initialize() {
            if ( !isInEditMode()) {

                Typeface b = Typeface.createFromAsset(getContext().getAssets(), "yekan_bold.ttf");
                setTypeface(b);

            }
        }
    }

