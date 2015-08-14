package com.cihankaptan.android.whounfollowedme.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

import com.cihankaptan.android.whounfollowedme.util.CustomFontUtils;

/**
 * Created by cnkaptan on 14/08/15.
 */
public class FontTextView extends TextView {


    public static final String ANDROID_SCHEMA = "http://schemas.android.com/apk/res/android";


    public FontTextView(Context context) {
        super(context);


        CustomFontUtils.applyCustomFont(this, context, null);
    }


    public FontTextView(Context context, AttributeSet attrs) {
        super(context, attrs);


        CustomFontUtils.applyCustomFont(this, context, attrs);
    }


    public FontTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);


        CustomFontUtils.applyCustomFont(this,context,attrs);
    }




}