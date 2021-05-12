package com.alien.process_view.process_view.path.Full;

import android.graphics.Path;
import android.util.Log;

import androidx.annotation.NonNull;

import com.alien.process_view.base.PathInfo;
import com.alien.process_view.base.TextSpaceInfo;
import com.alien.process_view.process_view.path.BaseArrowPath;

public class FullArrow extends BaseArrowPath {

    private static final String TAG = FullArrow.class.getSimpleName();

    private Path curPath;
    private PathInfo curPathInfo = new PathInfo();

    private PathInfo nextStartPoint = new PathInfo();
    private PathInfo nextEndPoint = new PathInfo();

    private float arrowPointX;
    private float arrowPointY;
    private PathInfo nextArrowPoint = new PathInfo();

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
    protected Path[] getArrowPath(@NonNull Path[] results, TextSpaceInfo[] textSpaceInfo) {

        for(int i = 0; i < results.length; i++) {
            curPath = new Path();

            calcX1();
            calcX2(i);
            calcX3(i);
            calcX4();
            calcX5(i);

            results[i] = curPath;
            textSpaceInfo[i] = new TextSpaceInfo();

            Log.d(TAG, "Make path: " + i);
        }

        nextStartPoint = new PathInfo();

        return results;
    }

}
