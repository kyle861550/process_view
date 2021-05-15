package com.view.alienlib.process_view.path.full;

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

        // 紀錄下次要開始畫的點
        curStartPoint = new PathInfo(curPathInfo);

        // 紀錄文字物件 - 初始化
        curTextSpaceInfo = new TextSpaceInfo(x, 0);
    }

    @Override
    protected void calcX2(float triangleLen) {
        float x = getCurBlockWidth() - triangleLen;
        float y = 0;

        curPath.lineTo(x, y);

        curPathInfo = new PathInfo(x, y);

        nextStartPoint = new PathInfo(curPathInfo,
                viewAttr.betweenSpace);

        curTextSpaceInfo.endX = x;    // end x point (文字可輸入的範圍)
    }

    @Override
    protected void calcX3() {
        float x = getCurBlockWidth();
        float y = (viewInfo.usefulHeight / 2f);

        curPath.lineTo(x, y);

        curPathInfo = new PathInfo(x, y);
    }

    @Override
    protected void calcX4(float triangleLen) {
        float x = curPathInfo.x - triangleLen;
        float y = viewInfo.usefulHeight;

        curPath.lineTo(x, y);

        curPathInfo = new PathInfo(x, y);
    }

    @Override
    protected void calcX5() {
        float x = 0;
        float y = viewInfo.usefulHeight;

        if(!isFirstBlock()) {
            x = nextEndPoint.x;
            y = nextEndPoint.y;
        }
        curPath.lineTo(x, y);

        nextEndPoint = new PathInfo(curPathInfo, viewAttr.betweenSpace);

        curPathInfo = new PathInfo(x, y);
    }

    @Override
    protected void calcX6(float triangleLen) {
        float x = curStartPoint.x + triangleLen;
        float y = curPathInfo.y / 2;

        curPath.lineTo(x, y);

        curTextSpaceInfo.startX = x;

        curPath.lineTo(curStartPoint.x, curStartPoint.y);   // Close Block, Don't use close
    }

}
