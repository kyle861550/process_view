package com.view.alienlib.base;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;

/**
 * View 建構基本資訊 & 架構 & 設定
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

    protected float textHeight(Paint paint) {
        float offset;

        Paint.FontMetrics fm = paint.getFontMetrics();

        offset = (fm.descent + fm.ascent) / 2;

        return offset;
    }

    protected float textCenterY(Paint paint, float baseLine) {
        return baseLine - textHeight(paint);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int usefulWidth = getUsefulWidth();
        int usefulHeight = getUsefulHeight();

        viewInfo.setUsefulSpace(usefulWidth, usefulHeight);

        if(viewInfo == null) {
            throw new NullPointerException("ViewInfo is null, cannot draw view.");
        }

        implDraw(canvas, viewInfo);
    }

    protected abstract void implDraw(Canvas canvas, T viewInfo);
    
    
// TODO: 暫時寫...要研究


    int HINT_START = 0x0001;
    int HINT_TOP = 0x0010;
    int HINT_END = 0x0100;
    int HINT_BOTTOM = 0x1000;

    enum ManualSet{
        NONE, SET_HEIGHT, SET_WIDTH, BOTH
    }

    enum MeasureType{
        HEIGHT, WIDTH
    }
    
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = 300;
        int height = 300;

        switch (UserSet()){
            default:
            case NONE:
                width = getViewSize(width, widthMeasureSpec, MeasureType.WIDTH);
                height = getViewSize(height, heightMeasureSpec, MeasureType.HEIGHT);
                break;

            case SET_WIDTH:
                height = getViewSize(height, heightMeasureSpec, MeasureType.HEIGHT);
                break;

            case SET_HEIGHT:
                width = getViewSize(width, widthMeasureSpec, MeasureType.WIDTH);
                break;

            case BOTH:   // User Do not Set, use Preset Value
                break;
        }

        setMeasuredDimension(width, height);
    }

    public ManualSet UserSet(){
        if (getLayoutParams().width == ViewGroup.LayoutParams.WRAP_CONTENT && 
                getLayoutParams().height == ViewGroup.LayoutParams.WRAP_CONTENT) {
            return ManualSet.BOTH;
        } else if (getLayoutParams().width == ViewGroup.LayoutParams.WRAP_CONTENT) {
            return ManualSet.SET_WIDTH;
        } else if (getLayoutParams().height == ViewGroup.LayoutParams.WRAP_CONTENT) {
            return ManualSet.SET_HEIGHT;
        } else {
            return ManualSet.NONE;
        }
    }
    
    public int getViewSize(int defaultSize, int measureSpec, MeasureType type){
        int mode = MeasureSpec.getMode(measureSpec);
        int size = MeasureSpec.getSize(measureSpec);

        switch (mode){
            default:
            case MeasureSpec.UNSPECIFIED:
                return defaultSize;

            case MeasureSpec.AT_MOST:
            case MeasureSpec.EXACTLY:
                return size;
        }
    }
}
