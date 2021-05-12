package com.alien.process_view.process_view.base;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.TypedValue;

import androidx.annotation.Nullable;

import com.alien.process_view.R;
import com.alien.process_view.base.BaseView;
import com.alien.process_view.process_view.path.ArrowTypeManager;
import com.alien.process_view.process_view.path.BlockPath;

import static android.util.TypedValue.COMPLEX_UNIT_SP;

/**
 * 分析使用者設定 & 打包 Draw 所需工具
 */
public abstract class ProcessView extends BaseView<ProcessViewInfo> {

    private static final String TAG = ProcessView.class.getSimpleName();
    private static final int[] ATTR_RES_IDS = R.styleable.process_view;

/// Attr style
    private int betweenSpace;
    private float viewAngle;
    private Direction viewDirection;

    private boolean enableCycleLine;

    private int bolderColor;
    private float bolderWidth;

    private int blockCount, blockProgress;
    private int blockSelectedColor;
    private int blockUnselectedColor;
    private int[] blockColors;
    private int[] blockPercent;

    private float textPxSize;
    private int textColor;
    private int[] textsRef;
    private String[] textsString;
    private Paint.Align textAlign;

    private BlockPath<ProcessViewInfo> arrowBlockPath;

/// View Tools
    private Paint bolderPaint, blockPaint, textPaint;

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
        viewAngle = typedArray.getFloat(R.styleable.process_view_angle, 25f);
        betweenSpace = typedArray.getInt(R.styleable.process_view_between_space, 10);
        enableCycleLine = typedArray.getBoolean(R.styleable.process_view_enable_cycle_line, false);

        int direct = typedArray.getInt(R.styleable.process_view_direction, Direction.DIRECTION_RIGHT.value);
        viewDirection = Direction.getDirection(direct);

        initBlockAttr(typedArray);

        initTextAttr(typedArray);

        int arrowType = typedArray.getInt(R.styleable.process_view_arrow_type, ArrowTypeManager.FULL_ARROW_END);
        arrowBlockPath = ArrowTypeManager.getBlockPath(arrowType);
    }

    private void initTextAttr(TypedArray typedArray) {
        int textSp = typedArray.getInt(R.styleable.process_view_text_size_sp, 16);
        textPxSize = TypedValue.applyDimension(COMPLEX_UNIT_SP, textSp, Resources.getSystem().getDisplayMetrics());
        textsRef = getIntArrayAttrsFromRefs(R.styleable.process_view_texts_ref);
        textsString = getStringArrayFromRefs(R.styleable.process_view_texts_string);
        textColor = typedArray.getColor(R.styleable.process_view_text_color, Color.BLACK);
        int align = typedArray.getInt(R.styleable.process_view_text_align, Paint.Align.CENTER.ordinal());
        textAlign = Paint.Align.values()[align];
    }

    private void initBlockAttr(TypedArray typedArray) {
        bolderColor = typedArray.getColor(R.styleable.process_view_bolder_color, Color.YELLOW);
        bolderWidth = typedArray.getFloat(R.styleable.process_view_bolder_width, 1.5f);
        blockCount = typedArray.getInt(R.styleable.process_view_block_count, 3);
        blockProgress = typedArray.getInt(R.styleable.process_view_block_progress, 1);
        blockSelectedColor = typedArray.getColor(R.styleable.process_view_block_selected_color, Color.RED);
        blockUnselectedColor = typedArray.getColor(R.styleable.process_view_block_unselected_color, Color.GRAY);
        blockColors = getIntArrayAttrsFromRefs(R.styleable.process_view_block_selected_colors);
        blockPercent = getIntArrayAttrsFromRefs(R.styleable.process_view_block_percent);
    }

    @Override
    protected void initViewTools() {
        bolderPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        bolderPaint.setAntiAlias(true);
        bolderPaint.setStyle(Paint.Style.STROKE);
        bolderPaint.setColor(bolderColor);
        bolderPaint.setStrokeWidth(bolderWidth);

        blockPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        blockPaint.setAntiAlias(true);
        blockPaint.setStyle(Paint.Style.FILL);
        blockPaint.setColor(blockSelectedColor);

        textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setAntiAlias(true);
        textPaint.setStyle(Paint.Style.FILL);
        textPaint.setColor(textColor);
        textPaint.setTextSize(textPxSize);
        textPaint.setTextAlign(textAlign);
    }

    @Override
    protected ProcessViewInfo initViewInfo() {
        ProcessViewInfo processViewInfo = new ProcessViewInfo();

        ProcessViewInfo.DrawTools drawTools = processViewInfo.getDrawTools();
        drawTools.bolderPaint = bolderPaint;
        drawTools.blockPaint = blockPaint;
        drawTools.textPaint = textPaint;

        ProcessViewInfo.ViewAttr viewAttr = processViewInfo.getViewAttr();
        viewAttr.blockCount = blockCount;
        viewAttr.blockProgress = blockProgress;
        viewAttr.betweenSpace = betweenSpace;
        viewAttr.viewAngle = viewAngle;
        viewAttr.viewDirection = viewDirection;
        viewAttr.enableCycleLine = enableCycleLine;
        viewAttr.textColor = textColor;
        viewAttr.blockPercent = blockPercent;
        viewAttr.blockSelectedColor = blockSelectedColor;
        viewAttr.blockUnselectedColor = blockUnselectedColor;
        viewAttr.blockColors = blockColors;
        viewAttr.textsString = textsString;
        viewAttr.textsRef = textsRef;

        return processViewInfo;
    }

    @Override
    protected int[] styleAttrId() {
        return ATTR_RES_IDS;
    }

    protected BlockPath<ProcessViewInfo> getArrowBlockPath() {
        return arrowBlockPath;
    }
}
