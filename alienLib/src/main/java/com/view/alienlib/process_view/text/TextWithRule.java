package com.view.alienlib.process_view.text;

import android.graphics.RectF;

import com.view.alienlib.base.TextInfo;
import com.view.alienlib.process_view.base.ProcessViewInfo;

import java.util.ArrayList;
import java.util.List;

public class TextWithRule implements BlockText {

    private static final List<TextInfo> result = new ArrayList<>(10);

    private ProcessViewInfo.ViewAttr viewAttr;

    private String[] preCheck() throws TextProcessException {
        if(result.size() != 0) {
            result.clear();
        }

        String[] texts = viewAttr.getTextContexts();
        if(texts == null) {
            throw new TextProcessException();
        }

        return texts;
    }

    private float calcStartY(RectF rectF, float textHeight, int count) {
        float result;

        float totalHeight = rectF.bottom - rectF.top;

        result = (totalHeight - (count * textHeight)) / 2;

        if(result < 0) {
            // TODO:  change text size
            result = 0;
        }

        return result;
    }

    private void splitText(String text, RectF rectF, float startX, float textHeight) throws TextProcessException {

        String textSplitKey = viewAttr.textSplitKey;

        if(textSplitKey == null || textSplitKey.isEmpty()) {
            throw new TextProcessException();
        } else if(text == null) {
            text = "";
        }

        String[] words = text.split(textSplitKey);

        float startY = calcStartY(rectF, textHeight, words.length);

        for(int i = 0; i < words.length; i++) {
            TextInfo textInfo = new TextInfo();

            textInfo.context = words[i];
            textInfo.startX = startX;
            textInfo.startY = startY + ((i + 1) * textHeight) - viewAttr.textPaddingTopBottomDp;

            result.add(textInfo);
        }

    }

    @Override
    public TextInfo[] getTextSpaceInfo(ProcessViewInfo.ViewAttr viewAttr, float textHeight, RectF[] rectFS) throws TextProcessException {
        this.viewAttr = viewAttr;

        String[] texts = preCheck();

        textHeight += (viewAttr.textPaddingTopBottomDp * 2);

        int length = rectFS.length;

        for(int i = 0; i < length; i++) {

            RectF rectF = rectFS[i];

            float startX = (rectF.left + rectF.right) / 2;

            splitText(texts[i], rectF, startX, textHeight);

        }

        int size = result.size();

        return result.toArray(new TextInfo[size]);
    }
}
