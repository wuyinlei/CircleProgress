package com.example.ruolan.circledemo;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by ruolan on 2015/11/24.
 */
public class CustomView extends View {

    private Paint mPaintBackCircle; //定义一个画大圆的画笔
    private Paint mPaintFrontCircle; //定义一个画小圆的画笔
    private Paint mPaintText;  //定义一个画文字的画笔


    private float mStrokeWidth = 50;
    private float mhalfStrokeWidth = mStrokeWidth / 2;
    private float mX = 200 + mhalfStrokeWidth;
    private float mY = 200 + mhalfStrokeWidth;

    //进度
    private int mProgress = 0;
    //圆形的半径
    private float mRadius = 200;
    //最大值
    private int mMax = 100;
    //目标进度
    private int mTargetProgess = 70;

    //画弧度
    private RectF mRectF;

    //定义宽度
    private int width;
    //定义高度
    private int height;


    private static final int DEFAULT_RADIUS = 200;
    private static final int DEFAULT_STROKE_WIDHT = 50;
    public CustomView(Context context) {
        this(context, null);
        init();
    }

    public CustomView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
        init();
    }

    public CustomView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        if (attrs != null){

            //获取到自定义的属性值
            TypedArray array = context.obtainStyledAttributes(attrs,R.styleable.CustomView);
            mRadius = array.getDimensionPixelSize(R.styleable.CustomView_radius,DEFAULT_RADIUS);
            mStrokeWidth = array.getDimensionPixelSize(R.styleable.CustomView_stroke_width,DEFAULT_STROKE_WIDHT);

            //获取到属性值后记得回收
            array.recycle();
        }
        init();
    }

    private void init() {
        //定义外部圆画笔的属性
        mPaintBackCircle = new Paint();
        mPaintBackCircle.setColor(Color.WHITE);
        mPaintBackCircle.setAntiAlias(true);
        mPaintBackCircle.setStyle(Paint.Style.STROKE);
        mPaintBackCircle.setStrokeWidth(mStrokeWidth);

        //定义内部圆画笔的属性
        mPaintFrontCircle = new Paint();
        mPaintFrontCircle.setColor(0xFF66C796);
        mPaintFrontCircle.setAntiAlias(true);
        mPaintFrontCircle.setStyle(Paint.Style.STROKE);
        mPaintFrontCircle.setStrokeWidth(mStrokeWidth);

        //定义文字画笔的属性
        mPaintText = new Paint();
        mPaintText.setColor(0xFF66C796);
        mPaintText.setAntiAlias(true);
        mPaintText.setTextSize(50);
        mPaintText.setStrokeWidth(mStrokeWidth);


    }

    @Override
    protected void onDraw(Canvas canvas) {
        //super.onDraw(canvas);

        //在onDraw（）方法调用前调用这个方法，以初始化弧形
        initRect();

        //角度
        float angle = mProgress / (float) mMax * 360;

        //绘制圆
        canvas.drawCircle(width / 2, height / 2, mRadius, mPaintBackCircle);

        //画弧
        canvas.drawArc(mRectF, -90, angle, false, mPaintFrontCircle);

        //绘制文字
        canvas.drawText(mProgress + "%", width / 2, height / 2, mPaintText);
        if (mProgress < mMax) {
            mProgress += 2;
            //调用一下的方法来强制的绘制界面
            invalidate();
        }
    }

    private void initRect() {
        if (mRectF == null) {
            mRectF = new RectF();
            int viewSize = (int) (mRadius * 2);
            int left = (width - viewSize) / 2;
            int top = (height - viewSize) / 2;
            int right = left + viewSize;
            int bottom = top + viewSize;

            mRectF.set(left, top, right, bottom);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        //获取到宽度
        width = getRealSize(widthMeasureSpec);
        //获取到高度
        height = getRealSize(heightMeasureSpec);

        //把或到的宽高保存起来
        setMeasuredDimension(width, height);
    }

    //获取具体的尺寸
    public int getRealSize(int measureSpec) {
        int result = -1;
        int mode = MeasureSpec.getMode(measureSpec);
        int size = MeasureSpec.getSize(measureSpec);

        if (mode == MeasureSpec.AT_MOST || mode == MeasureSpec.UNSPECIFIED) {
            //自己计算
            result = (int) (mRadius * 2 + mStrokeWidth);

        } else {
            result = size;
        }
        return result;
    }
}
