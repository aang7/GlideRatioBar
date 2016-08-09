package com.aang.glideratiogauge;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.RectF;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by abel on 5/08/16.
 */


/* This is the real Gauge :)*/
    /* UNDER CONSTRUCTION */
public class GlideRatioBar extends View {

    private Paint mPaint;

    private int mStrokeColor;
    private int mStrokeWidth;
    private int mStartValue;
    private int mEndValue;
    private int mLimit;

    private float mWidth;
    private float mHeight;
    private RectF mRect;
    private Paint mRectPaint;
    private Paint textPaint;

    TypedArray a;

    private float value = 10;


    public GlideRatioBar(Context context, AttributeSet attrs){
        super(context, attrs);

        a = context.obtainStyledAttributes(attrs, R.styleable.GlideRatioBar, 0, 0);

        mStrokeColor = a.getColor(R.styleable.GlideRatioBar_StrokeColor, ContextCompat.getColor(context, android.R.color.holo_red_dark));
        mStrokeWidth = a.getInteger(R.styleable.GlideRatioBar_StrokeWidth, 2);
        mStartValue = a.getInteger(R.styleable.GlideRatioBar_StartValue, -100);
        mEndValue = a.getInteger(R.styleable.GlideRatioBar_EndValue, 100);

        init();
        a.recycle();
    }

    public void init(){
        mPaint = new Paint();
        mPaint.setColor(mStrokeColor);
        mPaint.setStrokeWidth(2);
        mPaint.setAntiAlias(true);

        mRect = new RectF();
        mRectPaint = new Paint();
        mRectPaint.setColor(Color.GREEN);
        mRectPaint.setStrokeWidth(mStrokeWidth);
        mRectPaint.setAntiAlias(true);

        textPaint = new Paint();
        textPaint.setColor(Color.RED);
        textPaint.setStrokeWidth(2);
        textPaint.setAntiAlias(true);


    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        /*if (mDstBitmap != null)
            mDstBitmap.recycle();

        if (linesBitmap != null)
            linesBitmap.recycle();*/

        mWidth = w;
        mHeight = h;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawColor(Color.BLACK);
        //Let's set up the measuere with the paddings
        float paddingLeft = getPaddingLeft();
        float paddingRight= getPaddingRight();
        float paddingTop = getPaddingTop();
        float paddingBottom = getPaddingBottom();

        //View measures are:
        //mWidth = getWidth() - (paddingLeft+paddingRight);
        //mHeight = getHeight() - (paddingTop+paddingBottom);

        float radius = (mWidth > mHeight ? mWidth/2 : mHeight/2);

        //NOT USING THIS FOR THE MOMENT
        float rectLeft = mWidth/2 - radius + paddingLeft;
        float rectTop = mHeight/2 - radius + paddingTop;
        float rectRight = mWidth/2 - radius + paddingLeft + mWidth;
        float rectBottom = mHeight/2 - radius + paddingTop + mHeight;

        //mRect.set(mWidth/2, 0,mWidth/2 + (value),mHeight);
        mRect.set(mWidth/2, 0,mWidth/2 + (value*((mWidth/2)/mEndValue)),mHeight);
        canvas.drawRect(mRect, mRectPaint);

        //Lets draw the MARKLINES
        //(int) (mWidth/2)

        //The i+=20 it's the mark divisionStep
        for(int i = 0; i <= mWidth; i+=20) {

            Log.d("TAG1", " " + i);
          /*  if (i % 10 == 0) {
                //canvas.drawLine((mWidth/2) + i, 0, (mWidth/2) + i, mHeight, mPaint);
                //canvas.drawLine(((mWidth/2)/3)+i, 0, ((mWidth/2)/3)+i, mHeight, mPaint);
                //canvas.drawLine(((i*10)*((mWidth/2)/mEndValue)), 0, ((i*10)*((mWidth/2)/mEndValue)), mHeight, mPaint);
                canvas.drawLine((((i - mWidth / 2) * ((mWidth / 2) / (mEndValue))) - mWidth / 2), 0, (((i - mWidth / 2) * ((mWidth / 2) / (mEndValue))) - mWidth / 2), mHeight, mPaint);
            }*/

            canvas.drawLine(mWidth/2 + (i*((mWidth/2)/mEndValue)), 0, mWidth/2 + (i*((mWidth/2)/mEndValue)), mHeight, mPaint); //WORKING!

            //canvas.drawLine((((i)*((mWidth/2)/(mEndValue))) - mWidth/2), 0, (((i)*((mWidth/2)/(mEndValue))) - mWidth/2), mHeight, mPaint);

            //canvas.drawLine((value*((mWidth/2)/mEndValue)), 0, (value*((mWidth/2)/mEndValue)), mHeight, mPaint);

            //Asi agarro la mitad de la barra
            //canvas.drawLine(((i)*((mWidth/2)/mEndValue)), 0, ((i)*((mWidth/2)/mEndValue)), mHeight, mPaint);

        }

    }


    public void setValue(float valor){
        value = valor;
        invalidate();
    }
}
