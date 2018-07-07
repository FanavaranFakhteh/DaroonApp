package com.daroonapp.library.Fonts;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.EditText;

/**
 * Created by Android on 8/31/2016.
 */
    public class DigitalTextView extends EditText {

        public DigitalTextView(Context context, AttributeSet attrs, int defStyle) {
            super(context, attrs, defStyle);
            initialize();
        }

        public DigitalTextView(Context context, AttributeSet attrs) {
            super(context, attrs);
            initialize();
        }

        public DigitalTextView(Context context) {
            super(context);
            initialize();
        }

        private void initialize() {
            if ( !isInEditMode()) {

                Typeface b = Typeface.createFromAsset(getContext().getAssets(), "shabnamdigit.ttf");
                setTypeface(b);

            }
        }
    }

