package com.alien.process_view.process_view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.Log;

import androidx.annotation.MainThread;
import androidx.annotation.Nullable;

import com.alien.process_view.process_view.path.BlockPath;
import com.alien.process_view.process_view.path.FullArrowBlockEnd;

public class CubeProcessView extends ProcessView {

    private static final String TAG = CubeProcessView.class.getSimpleName();
    private final BlockPath<ProcessViewInfo> blockPath = new FullArrowBlockEnd();

    private ProcessViewInfo.DrawTools drawTools;
    private ProcessViewInfo.ViewAttr viewAttr;

    private Canvas canvas;

    private Path[] pathResult;

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

        pathResult = blockPath.getPath(viewInfo);

        drawBlock();

        drawBold();

        drawText();
    }

    private void drawBlock() {
        Paint blockPaint = drawTools.blockPaint;
        blockPaint.setShader(viewAttr.getShader());

        for(int i = 0; i < pathResult.length; i++) {
            Path path = pathResult[i];

            if(i > viewAttr.blockProgress - 1) {
                // 未被選擇區塊的顏色
                blockPaint.setColor(viewAttr.blockUnselectedColor);

                Log.d(TAG, "Unselected block index: " + (i + 1));
            }

            canvas.drawPath(path, blockPaint);
        }

        // 把顏色設定回原來的顏色
        blockPaint.setColor(viewAttr.blockSelectedColor);
    }

    private void drawBold() {
        Paint boldPaint = drawTools.bolderPaint;

        for(Path path : pathResult) {
            canvas.drawPath(path, boldPaint);
        }

    }

    private void drawText() {
        Paint textPaint = drawTools.textPaint;

    }

/// 操作方法
    public int getProgress() {
        //TODO: NPE: 尚未建立 viewAttr 物件
//        return viewAttr.blockProgress;
        return 3;
    }

    @MainThread
    public void setProgress(int value) {
        if(value < 0) {
            value = 0;
        } else if(value > viewAttr.blockCount) {
            value = viewAttr.blockCount;
        }

        viewAttr.blockProgress = value;

        postInvalidate();
    }

}
