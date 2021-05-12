package com.alien.process_view.process_view.path;

import android.graphics.Path;
import android.graphics.RectF;

import com.alien.process_view.base.ViewInfo;

public interface BlockPath<T extends ViewInfo> {

    Path[] getArrowPath(T viewInfo);

    RectF[] getTextSpace(T viewInfo);

}
