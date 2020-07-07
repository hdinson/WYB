package com.zjta.wyb.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Paint;

import android.graphics.PorterDuff.Mode;

import android.graphics.PorterDuffXfermode;

import android.graphics.Rect;

import android.graphics.drawable.BitmapDrawable;

import android.graphics.drawable.Drawable;

import android.graphics.drawable.NinePatchDrawable;

import android.util.AttributeSet;


import androidx.appcompat.widget.AppCompatImageView;

import com.zjta.wyb.R;

/**
 * normal
 */

public class NormalImageView extends AppCompatImageView {
    public NormalImageView(Context context) {
        super(context);
    }

    public NormalImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public NormalImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }
}