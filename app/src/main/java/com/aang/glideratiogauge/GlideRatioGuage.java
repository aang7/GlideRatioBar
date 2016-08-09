package com.aang.glideratiogauge;


import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.hardware.SensorEventListener;
import android.util.AttributeSet;
import android.view.View;
/**
 * Created by abel on 4/08/16.
 */
public class GlideRatioGuage extends View {


    private int positiveCol;
    private int negativeCol;
    private int divisions;
    private int v = 0;
    private int var;
    private float pX;
    private float pY;
    private int aux = 0;

    private Integer scale;
    private Paint line;
    private Paint rectPos;
    private Paint rectNeg;
    private Paint num;

    protected float mRectLeft;
    protected float mRectTop;
    protected float mRectRight;
    protected float mRectBottom;
    protected RectF mRect;


    public GlideRatioGuage(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.GlideRatioGuage, 0, 0);
        try {
            positiveCol = a.getColor(R.styleable.GlideRatioGuage_PositiveColor, 0);
            negativeCol = a.getColor(R.styleable.GlideRatioGuage_NegativeColor, 0);
            divisions = a.getInteger(R.styleable.GlideRatioGuage_Divisions, 0)*2;
            scale = a.getInteger(R.styleable.GlideRatioGuage_Scale, 0);
        } finally {
            a.recycle();
        }
    }

    @Override
    public void onDraw(Canvas canvas){
        super.onDraw(canvas);

        rectPos = new Paint();
        rectNeg = new Paint();
        line = new Paint();
        num = new Paint();

        pX = getWidth() / 2;
        pY = getHeight()/2;

        float paddingLeft = getPaddingLeft();
        float paddingRight = getPaddingRight();
        float paddingTop = getPaddingTop();
        float paddingBottom = getPaddingBottom();
        float width = getWidth() - (paddingLeft+paddingRight);
        float height = getHeight() - (paddingTop+paddingBottom);

        canvas.drawColor(Color.BLACK);

        rectPos.setColor(positiveCol);
        //rectPos.setAlpha(95);
        rectPos.setDither(true);
        rectNeg.setColor(negativeCol);
        //rectNeg.setAlpha(95);
        rectNeg.setDither(true);
        line.setColor(Color.BLACK);
        num.setColor(Color.WHITE);
        num.setTextSize(22);
        //num.setFontFeatureSettings();

        v=v+360;
        //
//        mRectLeft = pX;
//        mRectTop = pY;
//        mRectRight = pX - paddingLeft;
//        mRectBottom = pY - paddingTop;
//
//        mRect.set(mRectLeft, mRectTop, mRectRight, mRectBottom);
//        canvas.drawRect(mRect, rectPos);

        canvas.drawRect(pX, pY, pX + v, pY + 80, rectPos);
        canvas.drawRect(pX + v, pY, pX, pY + 80, rectNeg);

        var = (getWidth()-10)  / (divisions);

        for (float i = pX, j = pX, k = 0; i < getWidth(); i = i + var, j = j - var, ++k) {

            aux = (int) (scale * k);
            canvas.drawRect(i, pY, 5 + i, pY + 100, line);
            canvas.drawRect(j, pY, 5 + j, pY + 100, line);
            canvas.drawText(String.valueOf(aux), i - 10, (pY * 2), num);
            canvas.drawText(String.valueOf(aux), j - 10, (pY * 2), num);
        }
    }

    public void setValue(int value){
        this.v = value;
        invalidate();
        requestLayout();
    }
}
