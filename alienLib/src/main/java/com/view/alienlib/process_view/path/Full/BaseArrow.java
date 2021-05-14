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

    protected abstract void calcX2();

    protected abstract void calcX3();

    protected abstract void calcX4();

    protected abstract void calcX5();

    protected abstract void calcX6();

    private boolean isFirstBlock() {
        return curIndex == 0;
    }

    private boolean isLastBlock() {
        return curIndex == viewAttr.blockCount - 1;
    }

    protected boolean isFullStart() {
        return isFirstBlock() && (viewAttr.arrowFullFlag & ArrowTypeManager.START_FULL) > 1;
    }

    protected boolean isFullEnd() {
        return isLastBlock() && (viewAttr.arrowFullFlag & ArrowTypeManager.END_FULL) > 1;
    }

    @Override
    protected Path[] getArrowPath(@NonNull Path[] results, TextSpaceInfo[] textSpaceInfo) {

        for(int i = 0; i < results.length; i++) {
            curIndex = i;
            curPath = new Path();

            calcX1();
            calcX2();
            calcX3();
            calcX4();
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
