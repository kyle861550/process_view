package com.alien.process_view.process_view.base;

import android.content.Context;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;

import com.alien.process_view.base.ViewInfo;

public class ProcessViewInfo implements ViewInfo {

    public static final class ViewAttr {
        public int usefulHeight;
        public int usefulWidth;

        public int blockCount, blockProgress, betweenSpace;
        public float viewAngle;
        public ProcessView.Direction viewDirection;

        public boolean enableCycleLine;

        public int textColor;
        public int[] blockPercent;
        public int blockSelectedColor;
        public int blockUnselectedColor;
        public int[] blockColors;

        public int[] textsRef;
        public String[] textsString;

        private Shader shader;

        public Shader getShader() {
            if(shader != null) {
                return shader;
            }

            int middlePointX = usefulWidth / 2;

            shader = new LinearGradient(
                    middlePointX,
                    0,
                    middlePointX,
                    usefulHeight,
                    blockColors,
                    null,
                    Shader.TileMode.CLAMP
            );


            return shader;
        }

        /// 字串以 ref 為主 (e.g @sting/hello_world)，string 為輔
        public String[] getTextContexts(Context context) {
            String[] result;

            int[] textRef = textsRef;
            if(textRef != null) {
                result = new String[textRef.length];

                for(int i = 0; i < textRef.length; i++) {
                    result[i] = context.getString(textRef[i]);
                }
            } else {
                result = textsString;
            }

            result = textCountFitBlockCount(result);

            return result;
        }

        private String[] textCountFitBlockCount(String[] strings) {
            if(strings == null || strings.length == blockCount) {
                return strings;
            }
            String[] result = new String[blockCount];

            int copyCount = Math.min(strings.length, blockCount);

            System.arraycopy(strings, 0, result, 0, copyCount);

            for(int i = 0; i < result.length; i++) {
                if(result[i] == null) {
                    result[i] = "";
                }
            }

            return result;
        }

        private void fixWeightCountToFitBlockCount() {
            if(blockPercent.length == blockCount) {
                return;
            }

            int[] fixBlockPercent = new int[blockCount];

            System.arraycopy(blockPercent,
                    0,
                    fixBlockPercent,
                    0,
                    Math.min(blockPercent.length, blockCount));

            blockPercent = fixBlockPercent;

            for(int i = 0; i < blockPercent.length; i++) {
                if(blockPercent[i] == 0) {
                    blockPercent[i] = 1;
                }
            }

        }

        // 計算總分配
        private int getTotalPercentWeight() {
            int total = 0;

            fixWeightCountToFitBlockCount();

            for(int item : blockPercent) {
                total += item;
            }
            return total == 0 ? 1 : total;
        }

        public int[] getBlocksWidth() {
            if(blockPercent == null || blockPercent.length == 0) {
                blockPercent = new int[1];
            }

            int[] result = new int[blockCount];
            int totalPercentWeight = getTotalPercentWeight();

            int everyBlockWidth = usefulWidth / totalPercentWeight;

            for(int i = 0; i < blockCount; i++) {
                result[i] = blockPercent[i] * everyBlockWidth;
            }

            return result;
        }
    }

    public static final class DrawTools {
        private ProcessViewInfo viewInfo;
        public Paint bolderPaint, blockPaint, textPaint;
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
        drawTools.viewInfo = this;

        usefulWidth = viewAttr.usefulWidth = width;
        usefulHeight = viewAttr.usefulHeight = height;
    }

}
