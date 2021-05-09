package com.alien.process_view.process_view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;

import androidx.annotation.Nullable;

public class CubeProcessView extends ProcessView {

    private ProcessViewInfo.DrawTools drawTools;
    private ProcessViewInfo.ViewAttr viewAttr;

    private Canvas canvas;
    private int usefulHeight, usefulWidth;

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

        usefulHeight = viewInfo.usefulHeight;
        usefulWidth = viewInfo.usefulWidth;

        drawBlock();

        drawBold();

        drawText();
    }

    private void drawBlock() {
        Paint blockPaint = drawTools.blockPaint;

        // 每個區塊的寬度 (高度都一樣)
        int[] blocksWidth = viewAttr.getBlocksWidth();
        // 每個區塊中間的間隔
        int space = viewAttr.betweenSpace / 2;

        int startX = 0, endX;
        for(int i = 0; i < blocksWidth.length; i++) {
            int width = blocksWidth[i];

            // TODO: 最後一個會有空位
            endX = startX + width - space;
            Rect rect = new Rect(startX, 0, endX, usefulHeight);
            startX = endX + space;

            // TODO: 添加顏色
//            blockPaint.setColor(viewAttr.getBoldColors()[i]);
            canvas.drawRect(rect, blockPaint);
        }

    }

    private void drawBold() {
        Paint boldPaint = drawTools.boldPaint;

    }

    private void drawText() {
        Paint textPaint = drawTools.textPaint;

    }
}
