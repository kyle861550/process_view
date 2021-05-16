package com.view.alienlib.base;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

/**
 * View 建構基本資訊 & 架構 & 設定 & tools
 * @param <T> View 相關訊息
 */
public abstract class BaseView<T extends ViewInfo> extends View {

    private static final String TAG = BaseView.class.getSimpleName();

    private TypedArray typedArray;
    private T viewInfo;

    public BaseView(Context context) {
        this(context, null);
    }

    public BaseView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BaseView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        int[] attrId = styleAttrId();
        if(attrs == null || attrId == null) {
            Log.w(TAG, "Make no attrs " + TAG + " view.");
            return;
        }
        typedArray = context.obtainStyledAttributes(attrs, attrId);

        initView();
    }

    private void initView() {
        initAttrs(typedArray);
        typedArray.recycle();

        initViewTools();

        viewInfo = initViewInfo();
    }

    protected abstract void initAttrs(TypedArray typedArray);

    protected abstract void initViewTools();

    protected abstract T initViewInfo();

    protected abstract int[] styleAttrId();

    private int getResourceId(int styleableId) {
        if(typedArray == null) {
            Log.e(TAG, "TypedArray is null");
            throw new NullPointerException("TypedArray obj is null.");
        }

        int defaultId = 0;
        return typedArray.getResourceId(styleableId, defaultId);
    }

    @Nullable
    protected int[] getIntArrayAttrsFromRefs(int styleableId) {
        int[] result = null;

        int defaultId = getResourceId(styleableId);

        if(defaultId != 0) {
            result = getResources().getIntArray(defaultId);
        }
        return result;
    }

    @Nullable
    protected String[] getStringArrayFromRefs(int styleableId) {
        String[] result = null;

        int defaultId = getResourceId(styleableId);

        if(defaultId != 0) {
            result = getResources().getStringArray(defaultId);
        }

        return result;
    }

    private int getUsefulWidth() {
        int totalWidth = getWidth();

        final int paddingLeft = getPaddingLeft();
        final int paddingRight = getPaddingRight();

        return totalWidth - paddingLeft - paddingRight;
    }

    private int getUsefulHeight() {
        int totalHeight = getHeight();

        final int paddingTop = getPaddingTop();
        final int paddingBottom = getPaddingBottom();

        return totalHeight - paddingBottom - paddingTop;
    }

    protected float getTextHeight(Paint paint) {
        float offset;

        Paint.FontMetrics fm = paint.getFontMetrics();

        offset = (fm.top + fm.bottom);

        return Math.abs(offset);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int usefulWidth = getUsefulWidth();
        int usefulHeight = getUsefulHeight();

        viewInfo.setUsefulSpace(usefulWidth, usefulHeight);

        implDraw(canvas);
    }

    protected abstract void implDraw(Canvas canvas);
}
