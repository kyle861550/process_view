package com.alien.process_view.process_view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;

import androidx.annotation.IntRange;
import androidx.annotation.MainThread;
import androidx.annotation.Nullable;

import com.alien.process_view.process_view.base.ProcessView;
import com.alien.process_view.process_view.base.ProcessViewInfo;
import com.alien.process_view.process_view.path.ArrowTypeManager;
import com.alien.process_view.process_view.path.BlockPath;

public class CubeProcessView extends ProcessView {

    private static final String TAG = CubeProcessView.class.getSimpleName();
    private BlockPath<ProcessViewInfo> blockPath;

    private ProcessViewInfo.DrawTools drawTools;
    private ProcessViewInfo.ViewAttr viewAttr;

    private Canvas canvas;

    private Path[] pathResult;
    private RectF[] textSpace;

    public CubeProcessView(Context context) {
        super(context);
    }

    public CubeProcessView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CubeProcessView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void implDraw(Canvas canvas, ProcessViewInfo viewInfo) {
        this.canvas = canvas;
        this.drawTools = viewInfo.getDrawTools();
        this.viewAttr = viewInfo.getViewAttr();

        blockPath = blockPath == null ? getArrowBlockPath() : blockPath;

        pathResult = blockPath.getArrowPath(viewInfo);
        textSpace = blockPath.getTextSpace(viewInfo);

        drawBlock();

        drawBold();

        drawText();
    }

    private void drawBlock() {
        Paint blockPaint = drawTools.blockPaint;
        blockPaint.setShader(viewAttr.getShader());

        for(int i = 0; i < pathResult.length; i++) {
            Path path = pathResult[i];
            int targetColor = viewAttr.blockSelectedColor;

            if(i > viewAttr.blockProgress - 1) {
                // 未被選擇區塊的顏色
                targetColor = viewAttr.blockUnselectedColor;

                Log.d(TAG, "Unselected block index: " + (i + 1));
            }

            blockPaint.setColor(targetColor);

            canvas.drawPath(path, blockPaint);
        }

    }

    private void drawBold() {
        Paint boldPaint = drawTools.bolderPaint;

        for(Path path : pathResult) {
            canvas.drawPath(path, boldPaint);
        }

    }

    private void drawText() {
        Paint textPaint = drawTools.textPaint;
        int blockCount = viewAttr.blockCount;

        String[] texts = viewAttr.getTextContexts(getContext());
        if(texts == null) {
            return;
        }

        for(int i = 0; i < blockCount; i++) {
            RectF rectF = textSpace[i];
            String text = texts[i];

            float x = (rectF.left + rectF.right) / 2;
            float y =  rectF.bottom / 2;
            y = textCenterY(textPaint, y);

            implDrawText(textPaint, text, x, y, rectF.right - rectF.left);
//            canvas.drawText(text, x, y, textPaint);
        }

    }

    private void implDrawText(Paint paint, String text, float x, float y, float maxWidth) {
        float width = paint.measureText(text);

        if(width > maxWidth) {
            // TODO: 換行
        }

        canvas.drawText(text, x, y, paint);
    }


/// TODO: 操作方法 ( 抽出
    public int getProgress() {
        //TODO: NPE: 尚未建立 viewAttr 物件
//        return viewAttr.blockProgress;
        return 3;
    }

    public int getCount() {
        //TODO: NPE: 尚未建立 viewAttr 物件
//        return viewAttr.blockCount;
        return 3;
    }

    private int checkValue(int value) {
        int result = value;

        if(value < 0) {
            result = 0;
        } else if(value > viewAttr.blockCount) {
            result = viewAttr.blockCount;
        }

        return result;
    }

    @MainThread
    public void setProgress(int value) {
        value = checkValue(value);

        viewAttr.blockProgress = value;

        invalidate();
    }

    @MainThread
    public void setCount(int value) {
        if(value < 0) {
            value = 0;
        }

        viewAttr.blockCount = value;

        invalidate();
    }

    @MainThread
    public void setArrowType(@ArrowTypeManager.ArrowType int type) {
        blockPath = ArrowTypeManager.getBlockPath(type);

        invalidate();
    }

    @MainThread
    public void setAngle(@IntRange(from = 1, to = 179) int value) {
        viewAttr.viewAngle = value;

        invalidate();
    }

}
