package com.alien.process_view.process_view.path;

import android.graphics.Path;
import android.util.Log;

import com.alien.process_view.base.PathInfo;
import com.alien.process_view.process_view.ProcessViewInfo;

public class FullArrow implements BlockPath<ProcessViewInfo> {

    private static final String TAG = FullArrow.class.getSimpleName();

    private ProcessViewInfo viewInfo;
    private ProcessViewInfo.ViewAttr viewAttr;

    private int[] blocksWidth;
    private float unnecessaryLength;

    private Path curPath;
    private PathInfo curPathInfo = new PathInfo();

    private PathInfo nextStartPoint = new PathInfo();
    private PathInfo nextEndPoint = new PathInfo();

    private float arrowPointX;
    private float arrowPointY;
    private PathInfo nextArrowPoint = new PathInfo();

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

    private void calcX1() {
        float x = nextStartPoint.x;
        float y = 0;

        curPath.moveTo(x, y);

        curPathInfo = new PathInfo(x, y);
    }

    private void calcX2(int index) {
        int blockWidth = blocksWidth[index] * (index + 1);
        float x = blockWidth - unnecessaryLength;
        float y = 0;

        curPath.lineTo(x, y);

        curPathInfo = new PathInfo(x, y);

        nextStartPoint = new PathInfo(curPathInfo,
                viewAttr.betweenSpace);
    }

    private void calcX3(int index) {
        float x = blocksWidth[index] * (index + 1);
        float y = (viewInfo.usefulHeight / 2f);

        curPath.lineTo(x, y);

        curPathInfo = new PathInfo(x, y);

        arrowPointX = x;
        arrowPointY = y;
    }

    private void calcX4() {
        float x = curPathInfo.x - unnecessaryLength;
        float y = viewInfo.usefulHeight;

        curPath.lineTo(x, y);

        curPathInfo = new PathInfo(x, y);
    }

    private void calcX5(int index) {
        float x = 0;
        float y = viewInfo.usefulHeight;

        if(index != 0) {
            x = nextEndPoint.x;
            y = nextEndPoint.y;
        }
        curPath.lineTo(x, y);

        if(index != 0) {
            x = nextArrowPoint.x;
            y = nextArrowPoint.y;
        }
        curPath.lineTo(x, y);

        curPath.close();

        nextEndPoint = new PathInfo(curPathInfo, viewAttr.betweenSpace);
        nextArrowPoint = new PathInfo(
                arrowPointX + viewAttr.betweenSpace,
                arrowPointY
        );

        curPathInfo = new PathInfo(x, y);
    }

    @Override
    public Path[] getPath(ProcessViewInfo viewInfo) {

        prepareTools(viewInfo);

        Path[] results = new Path[viewAttr.blockCount];

        for(int i = 0; i < results.length; i++) {
            curPath = new Path();

            calcX1();
            calcX2(i);
            calcX3(i);
            calcX4();
            calcX5(i);

            results[i] = curPath;

            Log.d(TAG, "Make path: " + i);
        }

        return results;
    }

}
