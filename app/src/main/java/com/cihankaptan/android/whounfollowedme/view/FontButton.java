package com.cihankaptan.android.whounfollowedme.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Button;

import com.cihankaptan.android.whounfollowedme.util.CustomFontUtils;

/**
 * Created by cnkaptan on 14/08/15.
 */
public class FontButton extends Button {


    public FontButton(Context context) {
        super(context);


        CustomFontUtils.applyCustomFont(this, context, null);
    }


    public FontButton(Context context, AttributeSet attrs) {
        super(context, attrs);


        CustomFontUtils.applyCustomFont(this, context, attrs);
    }


    public FontButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);


        CustomFontUtils.applyCustomFont(this, context, attrs);
    }
}