package com.aang.glideratiogauge;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.Typeface;
import android.renderscript.Type;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by abel on 5/08/16.
 */


/* This is the real Gauge :)*/
    /* UNDER CONSTRUCTION */
public class GlideRatioBar extends View {

    private Paint mPaint;
    private Paint mRectPaint;
    private Paint textPaint;

    private int mStrokeWidth;
    private int mStartValue;
    private int mEndValue;
    private int mDividerStep;

    private int mRectColor;
    private int mPositiveColor;
    private int mNegativeColor;
    private int backgroundColor;
    private int mTextColor;
    private int mTextSize;

    private float mWidth;
    private float mHeight;

    private RectF mRect;
    private PointF textCenter;

    private TypedArray a;

    private float value = 0; //for testing


    public GlideRatioBar(Context context, AttributeSet attrs){
        super(context, attrs);

        a = context.obtainStyledAttributes(attrs, R.styleable.GlideRatioBar, 0, 0);

        mStrokeWidth = a.getInteger(R.styleable.GlideRatioBar_StrokeWidth, 2);
        mStartValue = a.getInteger(R.styleable.GlideRatioBar_MinValue, -20);//min value
        mEndValue = a.getInteger(R.styleable.GlideRatioBar_MaxValue, 20);//max value
        mDividerStep = a.getInteger(R.styleable.GlideRatioBar_DividerStep, 5); //Cada cuanto quiero una marca o division

        backgroundColor = a.getColor(R.styleable.GlideRatioBar_BackgroundColor, ContextCompat.getColor(context, android.R.color.black));
        mNegativeColor = a.getColor(R.styleable.GlideRatioBar_NegativeColor, ContextCompat.getColor(context, android.R.color.holo_red_dark));
        mPositiveColor = a.getColor(R.styleable.GlideRatioBar_PositiveColor, ContextCompat.getColor(context, android.R.color.holo_green_dark));
        mTextColor = a.getColor(R.styleable.GlideRatioBar_TextColor, ContextCompat.getColor(context, android.R.color.black));
        mTextSize = a.getInteger(R.styleable.GlideRatioBar_TextSize, 17);

        a.recycle();
        init();
    }

    public void init(){
        mPaint = new Paint();
        mPaint.setColor(backgroundColor);
        mPaint.setStrokeWidth(mStrokeWidth);
        mPaint.setAntiAlias(true);

        mRectPaint = new Paint();
        mRectPaint.setColor(mRectColor);
        mRectPaint.setStrokeWidth(1);
        mRectPaint.setAntiAlias(true);
        //mRectPaint.setShader(new LinearGradient(0, 0, mWidth, 200,  Color.RED,Color.BLUE, Shader.TileMode.CLAMP));//only for see how it works

        textPaint = new Paint();
        textPaint.setColor(mTextColor);
        textPaint.setStrokeWidth(2);
        textPaint.setAntiAlias(true);
        textPaint.setTextSize(mTextSize);
        Typeface sample = Typeface.create(Typeface.SANS_SERIF, Typeface.ITALIC);
        Typeface leadCot = Typeface.createFromAsset(getContext().getAssets(), "lead_coat/leadcoat.ttf");
        //textPaint.setTypeface(leadCot);
        mRect = new RectF();

    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        mWidth = w;
        mHeight = h;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawColor(backgroundColor); //Background and divisionSteps mark with the same color

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


        mRectPaint.setColor(mRectColor);


        //mRect.set(mWidth / 2, 0, mWidth / 2 + (value * ((mWidth / 2) / mEndValue)), mHeight); //above zero
        //if (mRect.left > mRect.right) /* due to RectF method not support create a rect from left to right: left must be <= right */
            //mRect.set(mWidth/2 + (value*((mWidth/2)/-mStartValue)), 0,mWidth/2,mHeight); //below zero


        mRect.set(mWidth / 2, 0, mWidth / 2 + (value * ((mWidth / 2) / mEndValue)), mHeight/1.5f); //above zero
        if (mRect.left > mRect.right) /* due to RectF method not support create a rect from left to right: left must be <= right */
            mRect.set(mWidth/2 + (value*((mWidth/2)/-mStartValue)), 0,mWidth/2,mHeight/1.5f); //below zero


        canvas.drawRect(mRect, mRectPaint);


        //Lets mark the lines and valuesDividerStep
        //The i+=20 it's the mark divisionStep
        for(int i = 0; i <= mWidth; i+=mDividerStep) {

            //Mark Lines
            canvas.drawLine((i*((mWidth/2)/mEndValue)), 0, (i*((mWidth/2)/mEndValue)), mHeight/1.5f, mPaint); //apparently WORKING!

            String rollString = String.valueOf(mStartValue + i);
            float rollStringWidth = textPaint.measureText(rollString);

            //Drawing the number values below the bar
            if ((i*((mWidth/2)/mEndValue)) > mWidth) {
                break;
            }
            else if ((((i)*(mWidth/2)/mEndValue)-rollStringWidth/2) < 0 ) {
                canvas.drawText(rollString, ((i) * (mWidth / 2) / mEndValue), mHeight / 1.1f, textPaint);
            }
            else if ((((i)*(mWidth/2)/mEndValue)-rollStringWidth/4) > mWidth) {
                canvas.drawText(rollString, mWidth - rollStringWidth, mHeight / 1.1f, textPaint);
            }
            else {
                if (rollString.equals(Integer.toString(mEndValue))) //Falta optimizar esto
                    canvas.drawText(rollString, mWidth - rollStringWidth, mHeight / 1.1f, textPaint);
                else
                    canvas.drawText(rollString, ((i) * (mWidth / 2) / mEndValue) - rollStringWidth / 2, mHeight / 1.1f, textPaint);
            }

        }



        /* Let's draw showValue Text */
        String string = String.format("%.2f", value);
        float valueStringWidth = textPaint.measureText(string);
        //textCenter = new PointF(mWidth/2 - valueStringWidth, mHeight/2);

        /* show Value following the bar - WORKING */
        /*if ((mWidth/2 + (value * ((mWidth / 2) / mEndValue))) <= mWidth && (mWidth/2 + (value * ((mWidth / 2) /-mStartValue))) >= 0) {
            if (value > 0)
                canvas.drawText(String.format("%.2f", value), mWidth / 2 + (value * ((mWidth / 2) / mEndValue)) - valueStringWidth, mHeight / 2, textPaint);
            else
                canvas.drawText(String.format("%.2f", value), mWidth / 2 + (value * ((mWidth / 2) / -mStartValue)), mHeight / 2, textPaint);
        }else {
            if (value > 0)
                canvas.drawText(Float.toString(value), mWidth / 2 + (mEndValue * ((mWidth / 2) / mEndValue)) - valueStringWidth, mHeight / 2, textPaint);
            else
                canvas.drawText(Float.toString(value), mWidth / 2 + (mStartValue * ((mWidth / 2) / -mStartValue)), mHeight / 2, textPaint);
        }*/

        /*Showing value in the middle*/
        //canvas.drawText(String.format("%.2f", value), mWidth/2, mHeight/2, textPaint);

    }


    public void setValue(float valor){
        value = valor;
        mRectColor = (valor < 0 ) ? mNegativeColor : mPositiveColor;
        invalidate();
    }

}