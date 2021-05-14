package com.view.alienlib.process_view.path.Full;

import com.view.alienlib.base.PathInfo;
import com.view.alienlib.base.TextSpaceInfo;

/**
 *     X1       X2
 *      *********
 *   X6 *       * X3
 *      *********
 *     X5       X4
 */
public class Arrow extends BaseArrow {

    private static final String TAG = Arrow.class.getSimpleName();

    @Override
    protected void calcX1() {
        float x = nextStartPoint.x;
        float y = 0;

        curPath.moveTo(x, y);

        curPathInfo = new PathInfo(x, y);

        curStartPoint = new PathInfo(curPathInfo);

        curTextSpaceInfo = new TextSpaceInfo(x, 0);
    }

    @Override
    protected void calcX2() {
        int blockWidth = blocksWidth[curIndex] * (curIndex + 1);
        float x = blockWidth - unnecessaryLength;
        float y = 0;

        if(isFullEnd()) {
            x = viewAttr.usefulWidth - viewAttr.betweenSpace;
        }

        curPath.lineTo(x, y);

        curPathInfo = new PathInfo(x, y);

        nextStartPoint = new PathInfo(curPathInfo,
                viewAttr.betweenSpace);

        curTextSpaceInfo.endX = x;    // end x point (文字可輸入的範圍)
    }

    @Override
    protected void calcX3() {
        float x = blocksWidth[curIndex] * (curIndex + 1);
        float y = (viewInfo.usefulHeight / 2f);

        if(isFullEnd()) {
            x = viewAttr.usefulWidth - viewAttr.betweenSpace;
        }

        curPath.lineTo(x, y);

        curPathInfo = new PathInfo(x, y);

        curArrowPoint = new PathInfo(x, y);
    }

    @Override
    protected void calcX4() {
        float x = curPathInfo.x - unnecessaryLength;
        float y = viewInfo.usefulHeight;

        if(isFullEnd()) {
            x = viewAttr.usefulWidth - viewAttr.betweenSpace;
        }

        curPath.lineTo(x, y);

        curPathInfo = new PathInfo(x, y);
    }

    @Override
    protected void calcX5() {
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
            curPath.lineTo(x, y);

            curTextSpaceInfo.startX = x;    // start x point (文字可輸入的範圍)
        }

        curPath.lineTo(curStartPoint.x, curStartPoint.y);

        nextEndPoint = new PathInfo(curPathInfo, viewAttr.betweenSpace);

        // 記錄箭頭的 Head 座標
        nextArrowPoint = new PathInfo(curArrowPoint, viewAttr.betweenSpace);

        curPathInfo = new PathInfo(x, y);
    }

    @Override
    protected void calcX6() {

    }

}
