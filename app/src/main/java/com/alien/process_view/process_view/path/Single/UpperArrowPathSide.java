package com.alien.process_view.process_view.path.Single;

import android.graphics.Path;
import android.graphics.RectF;
import android.util.Log;

import androidx.annotation.NonNull;

import com.alien.process_view.base.PathInfo;
import com.alien.process_view.process_view.path.BaseArrowPath;

public class UpperArrowPathSide extends BaseArrowPath {

    private static final String TAG = UpperArrowPathSide.class.getSimpleName();

    private Path curPath;
    private PathInfo curPathInfo = new PathInfo();

    private PathInfo nextStartPoint = new PathInfo();
    private PathInfo nextEndPoint = new PathInfo();

    private void calcX1() {
        float x = nextStartPoint.x;
        float y = 0;

        curPath.moveTo(x, y);

        curPathInfo = new PathInfo(x, y);
    }

    private void calcX2(int index) {
        int blockWidth = viewAttr.getBlocksWidth()[index];
        float x = curPathInfo.x + blockWidth;
        float y = 0;

        curPath.lineTo(x, y);

        curPathInfo = new PathInfo(x, y);

        nextStartPoint = new PathInfo(curPathInfo,
                viewAttr.betweenSpace);
    }

    private void calcX3() {
        float x = curPathInfo.x;
        float y = (viewInfo.usefulHeight / 2f);

        double viewAngle = Math.toRadians(viewAttr.viewAngle);
        double tan = Math.tan(viewAngle);
        double length = y * tan;
        x = (float) (x + length);

        //TODO: 超出邊界
//        if(x > viewInfo.usefulWidth) {
//            x = viewInfo.usefulWidth - viewAttr.betweenSpace;
//            y = y - viewAttr.betweenSpace;
//        }

        curPath.lineTo(x, y);

        curPathInfo = new PathInfo(x, y);
    }

    private void calcX4(int index) {
        float x = 0;
        float y = curPathInfo.y;

        if(index != 0) {
            x = nextEndPoint.x;
            y = nextEndPoint.y;
        }

        curPath.lineTo(x, y);
        curPath.close();

        nextEndPoint = new PathInfo(curPathInfo, viewAttr.betweenSpace);

        curPathInfo = new PathInfo(x, y);
    }

    @Override
    protected Path[] getArrowPath(@NonNull Path[] results) {

        for(int i = 0; i < results.length; i++) {
            curPath = new Path();

            calcX1();
            calcX2(i);
            calcX3();
            calcX4(i);

            results[i] = curPath;

            Log.d(TAG, "Make path: " + i);
        }

        return results;
    }

    @Override
    protected RectF[] getTextSpace(@NonNull RectF[] rect) {
        return rect;
    }

}
