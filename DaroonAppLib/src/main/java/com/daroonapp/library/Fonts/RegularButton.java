package com.daroonapp.library.Fonts;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by Android on 8/31/2016.
 */
    public class RegularButton extends Button {

        public RegularButton(Context context, AttributeSet attrs, int defStyle) {
            super(context, attrs, defStyle);
            initialize();
        }

        public RegularButton(Context context, AttributeSet attrs) {
            super(context, attrs);
            initialize();
        }

        public RegularButton(Context context) {
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

