package com.cihankaptan.android.whounfollowedme.util;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

import com.cihankaptan.android.whounfollowedme.R;

/**
 * Created by cnkaptan on 14/08/15.
 */
public class CustomFontUtils {


    public static final String ANDROID_SCHEMA = "http://schemas.android.com/apk/res/android";


    public static void applyCustomFont(TextView customFontTextView, Context context, AttributeSet attrs) {
        TypedArray attributeArray = context.obtainStyledAttributes(
                attrs,
                R.styleable.FontTextView);


        String fontName = attributeArray.getString(R.styleable.FontTextView_font);
        int textStyle = attrs.getAttributeIntValue(ANDROID_SCHEMA, "textStyle", 0);


        Typeface customFont = selectTypeface(context, fontName, textStyle);
        customFontTextView.setTypeface(customFont);


        attributeArray.recycle();
    }


    private static Typeface selectTypeface(Context context, String fontName, int textStyle) {
        if (fontName != null) {
            return FontCache.getTypeface(fontName, context);
        }
        else {
            /*
            information about the TextView textStyle:
            http://developer.android.com/reference/android/R.styleable.html#TextView_textStyle
            */
            switch (textStyle) {
                case 1: // bold
                    return FontCache.getTypeface("Rubik-Bold.ttf", context);


                case 2: // italic
                    return FontCache.getTypeface("Rubik-Italic.ttf", context);


                case 0: // regular
                default:
                    return FontCache.getTypeface("Rubik-Regular.ttf", context);
            }
        }
    }
}