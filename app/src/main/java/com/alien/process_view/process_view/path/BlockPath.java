package com.alien.process_view.process_view.path;

import android.graphics.Path;

import com.alien.process_view.base.ViewInfo;

public interface BlockPath<T extends ViewInfo> {

    Path[] getPath(T viewInfo);

}
