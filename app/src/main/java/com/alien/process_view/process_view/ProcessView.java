package com.alien.process_view.process_view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;

import androidx.annotation.Nullable;

import com.alien.process_view.R;
import com.alien.process_view.base.BaseView;

/**
 * 分析使用者設定 & 打包 Draw 所需工具
 */
public abstract class ProcessView extends BaseView<ProcessViewInfo> {

    private static final String TAG = ProcessView.class.getSimpleName();
    private static final int[] ATTR_RES_IDS = R.styleable.process_view;

/// Attr style
    private int viewCount, betweenSpace;
    private float viewAngle;
    private Direction viewDirection;

    private boolean enableCycleLine;

    private int textColor;
    private int[] boldColors;
    private int[] blockColors;
    private int[] blockPercent;

/// View Tools
    private Paint boldPaint, blockPaint, textPaint;

    public ProcessView(Context context) {
        super(context);
    }

    public ProcessView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ProcessView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public enum Direction {
        DIRECTION_RIGHT(0),
        DIRECTION_LEFT(1);

        int value;
        Direction(int value) {
            this.value = value;
        }

        static Direction getDirection(int id) {
            for(Direction direction : values()) {
                if(direction.value == id) {
                    return direction;
                }
            }
            throw new IllegalArgumentException();
        }
    }

    @Override
    protected void initAttrs(TypedArray typedArray) {
        viewAngle = typedArray.getFloat(R.styleable.process_view_angle, 45f);
        viewCount = typedArray.getInt(R.styleable.process_view_process_count, 3);
        betweenSpace = typedArray.getInt(R.styleable.process_view_between_space, 10);
        enableCycleLine = typedArray.getBoolean(R.styleable.process_view_enable_cycle_line, false);

        int direct = typedArray.getInt(R.styleable.process_view_direction, Direction.DIRECTION_RIGHT.value);
        viewDirection = Direction.getDirection(direct);

        boldColors = getArrayAttrsFromRefs(R.styleable.process_view_bold_colors);
        blockColors = getArrayAttrsFromRefs(R.styleable.process_view_block_colors);
        blockPercent = getArrayAttrsFromRefs(R.styleable.process_view_block_percent);

        textColor = typedArray.getColor(R.styleable.process_view_text_color, Color.BLACK);
    }

    @Override
    protected void initViewTools() {
        boldPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        boldPaint.setAntiAlias(true);
        boldPaint.setStyle(Paint.Style.STROKE);
        boldPaint.setColor(Color.YELLOW);
        boldPaint.setStrokeWidth(1.5f);

        blockPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        blockPaint.setAntiAlias(true);
        blockPaint.setStyle(Paint.Style.FILL);
        blockPaint.setColor(Color.BLUE);

        textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setAntiAlias(true);
        textPaint.setStyle(Paint.Style.FILL);
        textPaint.setColor(Color.BLACK);
    }

    @Override
    protected ProcessViewInfo initViewInfo() {
        ProcessViewInfo processViewInfo = new ProcessViewInfo();

        ProcessViewInfo.DrawTools drawTools = processViewInfo.getDrawTools();
        drawTools.boldPaint = boldPaint;
        drawTools.blockPaint = blockPaint;
        drawTools.textPaint = textPaint;

        ProcessViewInfo.ViewAttr viewAttr = processViewInfo.getViewAttr();
        viewAttr.viewCount = viewCount;
        viewAttr.betweenSpace = betweenSpace;
        viewAttr.viewAngle = viewAngle;
        viewAttr.viewDirection = viewDirection;
        viewAttr.enableCycleLine = enableCycleLine;
        viewAttr.textColor = textColor;
        viewAttr.boldColors = boldColors;
        viewAttr.blockColors = blockColors;
        viewAttr.blockPercent = blockPercent;

        return processViewInfo;
    }

    @Override
    protected int[] styleAttrId() {
        return ATTR_RES_IDS;
    }

}
