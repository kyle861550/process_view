package com.alien.process_view.process_view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;

import androidx.annotation.Nullable;

import com.alien.process_view.process_view.path.FullArrow;
import com.alien.process_view.process_view.path.FullArrowBlockEnd;
import com.alien.process_view.process_view.path.UpperArrowPathCenter;
import com.alien.process_view.process_view.path.BlockPath;

public class CubeProcessView extends ProcessView {

    private final BlockPath<ProcessViewInfo> blockPath = new FullArrowBlockEnd();

    private ProcessViewInfo.DrawTools drawTools;
    private ProcessViewInfo.ViewAttr viewAttr;

    private Canvas canvas;
    private int usefulHeight, usefulWidth;

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

        usefulHeight = viewInfo.usefulHeight;
        usefulWidth = viewInfo.usefulWidth;

        drawBlock(viewInfo);

        drawBold();

        drawText();
    }

    private void drawBlock(ProcessViewInfo viewInfo) {
        Paint blockPaint = drawTools.blockPaint;

        pathResult = blockPath.getPath(viewInfo);

        for(Path path : pathResult) {
            canvas.drawPath(path, blockPaint);
        }

    }

    private void drawBold() {
        Paint boldPaint = drawTools.boldPaint;

        for(Path path : pathResult) {
            canvas.drawPath(path, boldPaint);
        }

    }

    private void drawText() {
        Paint textPaint = drawTools.textPaint;

    }
}
