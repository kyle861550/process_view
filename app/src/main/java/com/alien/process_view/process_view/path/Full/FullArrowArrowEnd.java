package com.alien.process_view.process_view.path.Full;

import android.graphics.Path;
import android.graphics.RectF;
import android.util.Log;

import androidx.annotation.NonNull;

import com.alien.process_view.base.PathInfo;
import com.alien.process_view.process_view.path.BaseArrowPath;

/**
 * 最後的箭頭要補滿
 */
public class FullArrowArrowEnd extends BaseArrowPath {

    private static final String TAG = FullArrowArrowEnd.class.getSimpleName();

    private int curIndex;

    private Path curPath;
    private PathInfo curPathInfo = new PathInfo();
    private PathInfo curStartPoint = new PathInfo();

    private PathInfo nextStartPoint = new PathInfo();
    private PathInfo nextEndPoint = new PathInfo();

    private float arrowPointX;
    private float arrowPointY;
    private PathInfo nextArrowPoint = new PathInfo();

    private boolean isLastBlock() {
        return curIndex == viewAttr.blockCount - 1;
    }

    private void calcX1() {
        float x = nextStartPoint.x;
        float y = 0;

        curPath.moveTo(x, y);

        curPathInfo = new PathInfo(x, y);

        curStartPoint = new PathInfo(curPathInfo);
    }

    private void calcX2() {
        int blockWidth = blocksWidth[curIndex] * (curIndex + 1);
        float x = blockWidth - unnecessaryLength;
        float y = 0;

        if(isLastBlock()) {
            x = viewAttr.usefulWidth - viewAttr.betweenSpace;
        }

        curPath.lineTo(x, y);

        curPathInfo = new PathInfo(x, y);

        nextStartPoint = new PathInfo(curPathInfo,
                viewAttr.betweenSpace);
    }

    private void calcX3() {
        float x = blocksWidth[curIndex] * (curIndex + 1);
        float y = (viewInfo.usefulHeight / 2f);

        if(isLastBlock()) {
            x = viewAttr.usefulWidth - viewAttr.betweenSpace;
        }

        curPath.lineTo(x, y);

        curPathInfo = new PathInfo(x, y);

        arrowPointX = x;
        arrowPointY = y;
    }

    private void calcX4() {
        float x = curPathInfo.x - unnecessaryLength;
        float y = viewInfo.usefulHeight;

        if(isLastBlock()) {
            x = viewAttr.usefulWidth - viewAttr.betweenSpace;
        }

        curPath.lineTo(x, y);

        curPathInfo = new PathInfo(x, y);
    }

    private void calcX5() {
        float x = 0;
        float y = viewInfo.usefulHeight;

        if(curIndex != 0) {
            x = nextEndPoint.x;
            y = nextEndPoint.y;
        }
        curPath.lineTo(x, y);

        if(curIndex != 0) {
            x = nextArrowPoint.x;
            y = nextArrowPoint.y;
        }
        curPath.lineTo(x, y);

        // TODO: 其他類型的也要修正掉
//        curPath.close();
        curPath.lineTo(curStartPoint.x, curStartPoint.y);

        nextEndPoint = new PathInfo(curPathInfo, viewAttr.betweenSpace);
        nextArrowPoint = new PathInfo(
                arrowPointX + viewAttr.betweenSpace,
                arrowPointY
        );

        curPathInfo = new PathInfo(x, y);
    }

    @Override
    protected Path[] getArrowPath(@NonNull Path[] results) {

        for(int i = 0; i < results.length; i++) {
            curIndex = i;
            curPath = new Path();

            calcX1();
            calcX2();
            calcX3();
            calcX4();
            calcX5();

            results[i] = curPath;

            Log.d(TAG, "Make path: " + i);
        }

        nextStartPoint = new PathInfo();

        return results;
    }

    @Override
    protected RectF[] getTextSpace(@NonNull RectF[] rect) {
        return rect;
    }

}
