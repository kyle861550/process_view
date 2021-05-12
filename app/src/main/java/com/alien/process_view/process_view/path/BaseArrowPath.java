package com.alien.process_view.process_view.path;

import android.graphics.Path;
import android.graphics.RectF;

import androidx.annotation.NonNull;

import com.alien.process_view.process_view.base.ProcessViewInfo;

public abstract class BaseArrowPath implements BlockPath<ProcessViewInfo> {

    protected ProcessViewInfo viewInfo;
    protected ProcessViewInfo.ViewAttr viewAttr;

    protected int[] blocksWidth;
    protected float unnecessaryLength;

    private void prepareTools(ProcessViewInfo viewInfo) {
        this.viewInfo = viewInfo;
        this.viewAttr = viewInfo.getViewAttr();

        blocksWidth = viewAttr.getBlocksWidth();

        unnecessaryLength = calcLength();
    }

    private float calcLength() {
        double height = (viewInfo.usefulHeight / 2f);

        double viewAngle = Math.toRadians(viewAttr.viewAngle);
        double tan = Math.tan(viewAngle);
        double length = height * tan;

        return (float) length;
    }

    @Override
    public Path[] getArrowPath(ProcessViewInfo viewInfo) {
        prepareTools(viewInfo);

        Path[] results = new Path[viewAttr.blockCount];

        return getArrowPath(results);
    }

    protected abstract Path[] getArrowPath(@NonNull Path[] results);

    @Override
    public RectF[] getTextSpace(ProcessViewInfo viewInfo) {
        prepareTools(viewInfo);

        RectF[] results = new RectF[viewAttr.blockCount];

        return getTextSpace(results);
    }

    protected abstract RectF[] getTextSpace(@NonNull RectF[] rect);

}
