package com.view.alienlib.process_view.text;

import android.graphics.RectF;

import com.view.alienlib.base.TextInfo;
import com.view.alienlib.process_view.base.ProcessViewInfo;

public interface BlockText {

    TextInfo[] getTextSpaceInfo(ProcessViewInfo.ViewAttr viewAttr, float textHeight, RectF[] rectFS) throws TextProcessException;

}
