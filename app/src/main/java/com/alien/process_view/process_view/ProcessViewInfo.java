package com.alien.process_view.process_view;

import android.graphics.Paint;

import com.alien.process_view.base.ViewInfo;

public class ProcessViewInfo implements ViewInfo {

    public static final class ViewAttr {
        public int usefulWidth;

        public int viewCount, betweenSpace;
        public float viewAngle;
        public ProcessView.Direction viewDirection;

        public boolean enableCycleLine;

        public int textColor;
        public int[] boldColors;
        public int[] blockColors;
        public int[] blockPercent;

        public int[] getBoldColors() {
            int[] result = boldColors;
            if(result == null) {
                result = new int[viewCount];
            }

            if(viewCount != result.length) {
                result = new int[viewCount];
                System.arraycopy(blockColors, 0, result, 0, blockColors.length);
            }
            return result;
        }

        // 計算總分配
        private int getTotalPercent() {
            int total = 0;
            for(int item : blockPercent) {
                total += item;
            }
            return total == 0 ? 1 : total;
        }

        public int[] getBlocksWidth() {
            if(blockPercent == null || blockPercent.length == 0) {
                blockPercent = new int[1];
            }

            int[] result = new int[blockPercent.length];

            int everyBlockWidth = usefulWidth / getTotalPercent();

            for(int i = 0; i < blockPercent.length; i++) {
                result[i] = blockPercent[i] * everyBlockWidth;
            }

            return result;
        }
    }

    public static final class DrawTools {
        public Paint boldPaint, blockPaint, textPaint;
    }

    public int usefulWidth;
    public int usefulHeight;

    private final ViewAttr viewAttr;
    private final DrawTools drawTools;

    public ProcessViewInfo() {
        viewAttr = new ViewAttr();
        drawTools = new DrawTools();
    }

    public ViewAttr getViewAttr() {
        return viewAttr;
    }

    public DrawTools getDrawTools() {
        return drawTools;
    }

    @Override
    public void setUsefulSpace(int width, int height) {
        usefulWidth = viewAttr.usefulWidth = width;
        usefulHeight = height;
    }

}
