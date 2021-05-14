package com.view.alienlib.process_view.path.Full;

import android.graphics.Path;
import android.util.Log;

import androidx.annotation.NonNull;

import com.view.alienlib.base.PathInfo;
import com.view.alienlib.base.TextSpaceInfo;
import com.view.alienlib.process_view.path.ArrowTypeManager;
import com.view.alienlib.process_view.path.BaseArrowPath;

public abstract class BaseArrow extends BaseArrowPath {

    private static final String TAG = BaseArrow.class.getSimpleName();

    protected int curIndex;

    protected Path curPath;
    protected PathInfo curPathInfo = new PathInfo();
    protected PathInfo curStartPoint = new PathInfo();

    protected PathInfo nextStartPoint = new PathInfo();
    protected PathInfo nextEndPoint = new PathInfo();

    protected PathInfo curArrowPoint = new PathInfo();
    protected PathInfo nextArrowPoint = new PathInfo();

    protected TextSpaceInfo curTextSpaceInfo;

    protected abstract void calcX1();

    protected abstract void calcX2(float triangleLen);

    protected abstract void calcX3();

    protected abstract void calcX4(float triangleLen);

    protected abstract void calcX5();

    protected abstract void calcX6();

    private boolean isFirstBlock() {
        return curIndex == 0;
    }

    private boolean isLastBlock() {
        return curIndex == viewAttr.blockCount - 1;
    }

    private boolean isFullStart() {
        return (viewAttr.arrowFullFlag & ArrowTypeManager.START_FULL) == ArrowTypeManager.START_FULL;
    }

    private boolean isFullEnd() {
        return (viewAttr.arrowFullFlag & ArrowTypeManager.END_FULL) == ArrowTypeManager.END_FULL;
    }

    private float getTriangleLen(boolean isStartPart) {
        float result = unnecessaryLength;

        if(isStartPart) {
            if(isFullEnd() && isLastBlock()) {
                result = viewAttr.usefulWidth - viewAttr.betweenSpace;
            }
        } else {
            if(isFullStart() && isFirstBlock()) {
                result = viewAttr.usefulWidth - viewAttr.betweenSpace;
            }
        }

        return result;
    }

    @Override
    protected Path[] getArrowPath(@NonNull Path[] results, TextSpaceInfo[] textSpaceInfo) {

        for(int i = 0; i < results.length; i++) {
            curIndex = i;
            curPath = new Path();

            calcX1();
            calcX2(getTriangleLen(true));

            calcX3();

            calcX4(getTriangleLen(false));
            calcX5();

            calcX6();

            results[i] = curPath;
            textSpaceInfo[i] = curTextSpaceInfo;

            Log.d(TAG, "Make path: " + i);
        }

        nextStartPoint = new PathInfo();

        return results;
    }


}
