package com.mohamed.yatproject;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public class EmojiView extends View {

    private final static int DEFAULT_FACE_COLOR = Color.YELLOW;
    private final static int DEFAULT_EYES_COLOR = Color.BLACK;
    private final static int DEFAULT_MOUTH_COLOR = Color.BLACK;
    private final static int DEFAULT_BORDER_COLOR = Color.BLACK;
    private final static float DEFAULT_BORDER_WIDTH = 4.0f;
    public final static long HAPPY = 0;
    public final static long SAD = 1;

    private Paint paint;
    private int faceColor = DEFAULT_FACE_COLOR;
    private int eyesColor = DEFAULT_EYES_COLOR;
    private int mouthColor = DEFAULT_MOUTH_COLOR;
    private int borderColor = DEFAULT_BORDER_COLOR;
    private long happinessState = HAPPY;

    private float borderWidth = DEFAULT_BORDER_WIDTH;

    private int size = 320;

    private Path mouthPath;

    public EmojiView(Context context) {
        super(context);
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mouthPath = new Path();
    }

    public EmojiView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mouthPath = new Path();
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);

        TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs,
                R.styleable.EmojiView, 0, 0);

        faceColor = typedArray.getColor(R.styleable.EmojiView_faceColor, DEFAULT_FACE_COLOR);
        eyesColor = typedArray.getColor(R.styleable.EmojiView_eyesColor, DEFAULT_EYES_COLOR);
        mouthColor = typedArray.getColor(R.styleable.EmojiView_mouthColor, DEFAULT_MOUTH_COLOR);
        borderColor = typedArray.getColor(R.styleable.EmojiView_borderColor, DEFAULT_BORDER_COLOR);
        borderWidth = typedArray.getDimension(R.styleable.EmojiView_borderWidth, DEFAULT_BORDER_WIDTH);
        setHappinessState((long) typedArray.getInt(R.styleable.EmojiView_state, (int) HAPPY));
        typedArray.recycle();
    }

    public void setHappinessState(long state) {
        this.happinessState = state;
        invalidate();
    }

    public long getHappinessState() {
        return this.happinessState;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        drawFaceBackground(canvas);

        drawEyes(canvas);

        drawMouth(canvas);

    }

    private void drawFaceBackground(Canvas canvas) {
        paint.setColor(faceColor);
        paint.setStyle(Paint.Style.FILL);

        float radius = size / 2f;

        canvas.drawCircle(size / 2f, size / 2f, radius, paint);

        paint.setColor(borderColor);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(borderWidth);

        canvas.drawCircle(size / 2f, size / 2f, radius - borderWidth / 2f, paint);
    }

    private void drawEyes(Canvas canvas) {
        paint.setColor(eyesColor);
        paint.setStyle(Paint.Style.FILL);

        RectF leftEye = new RectF(size * 0.32f, size * 0.23f, size * 0.43f, size * 0.50f);

        canvas.drawOval(leftEye, paint);

        RectF rightEye = new RectF(size * 0.57f, size * 0.23f, size * 0.68f, size * 0.50f);

        canvas.drawOval(rightEye, paint);
    }

    private void drawMouth(Canvas canvas) {

        mouthPath.reset();

        mouthPath.moveTo(size * 0.22f, size * 0.7f);

        if (happinessState == HAPPY) {
            mouthPath.quadTo(size * 0.50f, size * 0.80f, size * 0.78f, size * 0.70f);
            mouthPath.quadTo(size * 0.50f, size * 0.90f, size * 0.22f, size * 0.70f);
        } else {
            mouthPath.quadTo(size * 0.50f, size * 0.50f, size * 0.78f, size * 0.70f);
            mouthPath.quadTo(size * 0.50f, size * 0.60f, size * 0.22f, size * 0.70f);
        }

        paint.setColor(mouthColor);
        paint.setStyle(Paint.Style.FILL);

        canvas.drawPath(mouthPath, paint);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        size = Math.min(getMeasuredWidth(), getMeasuredHeight());

        setMeasuredDimension(size, size);
    }
}
