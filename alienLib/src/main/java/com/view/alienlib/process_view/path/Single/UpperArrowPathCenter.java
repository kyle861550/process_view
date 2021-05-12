package com.view.alienlib.process_view.path.Single;

import android.graphics.Path;
import android.util.Log;

import androidx.annotation.NonNull;

import com.view.alienlib.base.PathInfo;
import com.view.alienlib.base.TextSpaceInfo;
import com.view.alienlib.process_view.path.BaseArrowPath;

public class UpperArrowPathCenter extends BaseArrowPath {

    private static final String TAG = UpperArrowPathCenter.class.getSimpleName();

    private Path curPath;
    private PathInfo curPathInfo = new PathInfo();

    private PathInfo nextStartPoint = new PathInfo();
    private PathInfo nextEndPoint = new PathInfo();

    private void calcX1() {
        float x = nextStartPoint.x;
        float y = (viewInfo.usefulHeight / 2f);

        curPath.moveTo(x, y);

        curPathInfo = new PathInfo(x, y);
    }

    private void calcX2(int index) {
        int blockWidth = viewAttr.getBlocksWidth()[index];
        float x = curPathInfo.x + blockWidth;
        float y = curPathInfo.y;

        curPath.lineTo(x, y);

        curPathInfo = new PathInfo(x, y);

        nextStartPoint = new PathInfo(curPathInfo,
                viewAttr.betweenSpace);
    }

    private void calcX3() {
        float x = curPathInfo.x;
        float y = 0;

        float height = curPathInfo.y;

        double viewAngle = Math.toRadians(viewAttr.viewAngle);
        double tan = Math.tan(viewAngle);
        double length = height * tan;
        x = (float) (x - length);

        curPath.lineTo(x, y);

        curPathInfo = new PathInfo(x, y);
    }

    private void calcX4(int index) {
        float x = 0;
        float y = 0;

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
    protected Path[] getArrowPath(@NonNull Path[] results, TextSpaceInfo[] textSpaceInfo) {

        for(int i = 0; i < results.length; i++) {
            curPath = new Path();

            calcX1();
            calcX2(i);
            calcX3();
            calcX4(i);

            results[i] = curPath;
            textSpaceInfo[i] = new TextSpaceInfo();

            Log.d(TAG, "Make path: " + i);
        }

        return results;
    }


}