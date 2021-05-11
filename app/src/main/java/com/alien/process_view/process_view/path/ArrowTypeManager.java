package com.alien.process_view.process_view.path;

import androidx.annotation.IntDef;

import com.alien.process_view.process_view.base.ProcessViewInfo;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class ArrowTypeManager {

    public static final int FULL_ARROW = 0x0000;
    public static final int FULL_ARROW_END = 0x0001;
    public static final int UPPER_ARROW_CENTER = 0x0002;
    public static final int UPPER_ARROW_SIDE = 0x0003;

    @IntDef({
            FULL_ARROW,
            FULL_ARROW_END,
            UPPER_ARROW_CENTER,
            UPPER_ARROW_SIDE
    })
    @Retention(RetentionPolicy.SOURCE)
    public @interface ArrowType {}

    public static BlockPath<ProcessViewInfo> getBlockPath(@ArrowType int type) {
        BlockPath<ProcessViewInfo> result;

        switch (type) {
            case FULL_ARROW:
                result = new FullArrow();
                break;

            case FULL_ARROW_END:
                result = new FullArrowBlockEnd();
                break;

            case UPPER_ARROW_CENTER:
                result = new UpperArrowPathCenter();
                break;

            case UPPER_ARROW_SIDE:
                result = new UpperArrowPathSide();
                break;

            default:
                throw new IllegalStateException("Unexpected value: " + type);
        }

        return result;
    }

}
