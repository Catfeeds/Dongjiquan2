package com.dongjiquan.dongjiquan.utils;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.Scroller;

import com.dongjiquan.dongjiquan.R;


/**
 * Created by user on 2016/6/1.
 */
public class ScaleView extends View implements Animator.AnimatorListener {
    private Paint bPaint;
    private Paint lPaint;
    private Paint tPaint;
    private Paint sPaint;
    private RectF borderRectF;
    private VelocityTracker velocityTracker;
    private Scroller scroller;
    private Interpolator mInterpolator;
    private int mWidth;
    private int mHeight;
    private int startY;
    private int sumMoveX;
    private int downX;
    private int lastMoveX;
    private int halfWidth;
    private int sumPixel;
    private int selectItem;
    private boolean isAnim;
    private boolean isLeft;
    private boolean isDrag;
    private boolean flag;
    private float borderWidth;
    private float lineWidth;
    private long animTime;
    private int borderColor;
    private int lineColor;
    private int trigonSize;
    private int pixel;
    private int step;
    private int lineHeight;
    private int lineToText;
    private int begin;
    private int end;
    private int minVelocity;
    private int indicateHeight;
    private boolean isRect;
    private boolean isTop;
    private ScaleView.RulerValue onRulerValueChangeListener;
    private ScaleView.SelectItem onSelectItem;

    public ScaleView(Context context) {
        this(context, (AttributeSet)null);
    }

    public ScaleView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ScaleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mInterpolator = new AccelerateDecelerateInterpolator();
        this.sumMoveX = 0;
        this.downX = 0;
        this.lastMoveX = 0;
        this.isAnim = false;
        this.isDrag = true;
        this.flag = true;
        this.init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.ScaleView);
        this.borderWidth = ta.getDimension(R.styleable.ScaleView_rBorderWidth, 0.0F);
        this.lineWidth = ta.getDimension(R.styleable.ScaleView_rLineWidth, 2.0F);
        this.borderColor = ta.getColor(R.styleable.ScaleView_rBorderColor, 17676961);
        this.lineColor = ta.getColor(R.styleable.ScaleView_rLineColor, -1);
        this.trigonSize = (int)ta.getDimension(R.styleable.ScaleView_rTrigonSize, 20.0F);
        this.pixel = ta.getInt(R.styleable.ScaleView_rPixel, 15);
        this.step = ta.getInt(R.styleable.ScaleView_rStep, 1);
        int textSize = (int)ta.getDimension(R.styleable.ScaleView_rTextSize, 30.0F);
        int textColor = ta.getColor(R.styleable.ScaleView_rTextColor, -1);
        this.lineHeight = (int)ta.getDimension(R.styleable.ScaleView_rLineHeight, 25.0F);
        this.lineToText = (int)ta.getDimension(R.styleable.ScaleView_rLineToText, 40.0F);
        this.begin = ta.getInt(R.styleable.ScaleView_rBegin, 0);
        this.selectItem = this.begin;
        this.end = ta.getInt(R.styleable.ScaleView_rEnd, 1000);
        this.minVelocity = ta.getInt(R.styleable.ScaleView_rMinVelocity, 500);
        this.animTime = (long)ta.getInt(R.styleable.ScaleView_rAnimTime, 300);
        this.indicateHeight = (int)ta.getDimension(R.styleable.ScaleView_rIndicateHeight, 0.0F);
        this.isRect = ta.getBoolean(R.styleable.ScaleView_rIsRect, true);
        this.isTop = ta.getBoolean(R.styleable.ScaleView_rIsTop, true);
        ta.recycle();
        this.scroller = new Scroller(context);
        this.setOverScrollMode(0);
        this.lPaint = new Paint();
        this.lPaint.setAntiAlias(true);
        this.lPaint.setColor(this.lineColor);
        this.lPaint.setStrokeWidth(this.lineWidth);
        this.bPaint = new Paint();
        this.bPaint.setAntiAlias(true);
        this.bPaint.setStyle(Paint.Style.STROKE);
        this.bPaint.setStrokeWidth(this.borderWidth);
        this.bPaint.setColor(this.borderColor);
        this.tPaint = new Paint();
        this.tPaint.setAntiAlias(true);
        this.tPaint.setTextAlign(Paint.Align.CENTER);
        this.tPaint.setColor(textColor);
        this.tPaint.setTextSize((float)textSize);
        this.tPaint.setStyle(Paint.Style.FILL);
        this.sPaint = new Paint();
        this.sPaint.setAntiAlias(true);
        this.sPaint.setStyle(Paint.Style.FILL);
        this.sPaint.setStrokeWidth(this.borderWidth);
        this.sPaint.setColor(this.borderColor);
        this.sumPixel = (this.end - this.begin) / this.step * this.pixel;
        this.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            public void onGlobalLayout() {
                if(null != ScaleView.this.onSelectItem) {
                    ScaleView.this.selectItem = ScaleView.this.onSelectItem.setSelectItem();
                }

                ScaleView.this.selectItem();
                ScaleView.this.getViewTreeObserver().removeGlobalOnLayoutListener(this);
            }
        });
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        this.mWidth = MeasureSpec.getSize(widthMeasureSpec);
        this.mHeight = MeasureSpec.getSize(heightMeasureSpec);
        this.startY = 0;
        this.borderRectF = new RectF(0.0F, 0.0F, (float)this.mWidth, (float)this.mHeight);
        this.halfWidth = this.mWidth / 2;
    }

    protected void onDraw(Canvas canvas) {
        this.drawScale(canvas);
        if(this.isRect) {
            this.drawBoarder(canvas);
        } else {
            this.drawLineIndicate(canvas);
        }

    }

    private void drawScale(Canvas canvas) {
        this.lPaint.setColor(this.lineColor);
        this.lPaint.setStrokeWidth(this.lineWidth);
        if(this.onRulerValueChangeListener != null && this.sumMoveX <= this.halfWidth && -this.sumMoveX <= this.sumPixel - this.halfWidth) {
            this.onRulerValueChangeListener.value((-this.sumMoveX + this.halfWidth) / this.pixel * this.step + this.begin);
        }

        for(int x = 0; x < this.mWidth; ++x) {
            int y = this.startY + this.lineHeight;
            boolean isDrawText = false;
            if((-this.sumMoveX + x) % (this.pixel * 5) == 0) {
                y += this.lineHeight;
            }

            if((-this.sumMoveX + x) % (this.pixel * 10) == 0) {
                y += this.lineHeight;
                this.indicateHeight=y;
                isDrawText = true;
            }

            int text = (-this.sumMoveX + x) / this.pixel * this.step + this.begin;
            if(text >= this.begin && text <= this.end && (-this.sumMoveX + x) % this.pixel == 0) {
                canvas.drawLine((float)x, this.isTop?(float)this.startY:(float)this.mHeight, (float)x, this.isTop?(float)y:(float)(this.mHeight - y), this.lPaint);
            }

            if(isDrawText && text >= this.begin && text <= this.end) {
                canvas.drawText(String.valueOf(text), (float)x, this.isTop?(float)(y + this.lineToText):(float)(this.mHeight - y - this.lineToText), this.tPaint);
            }
        }

    }

    private void drawBoarder(Canvas canvas) {
        canvas.drawRect(this.borderRectF, this.bPaint);
        Path path = new Path();
        path.moveTo((float)(this.halfWidth - this.trigonSize / 2), this.isTop?0.0F:(float)this.mHeight);
        path.lineTo((float)(this.halfWidth + this.trigonSize / 2), this.isTop?0.0F:(float)this.mHeight);
        path.lineTo((float)this.halfWidth, this.isTop?(float)(this.trigonSize / 2):(float)(this.mHeight - this.trigonSize / 2));
        path.close();
        canvas.drawPath(path, this.sPaint);
    }

    private void drawLineIndicate(Canvas canvas) {
        this.lPaint.setColor(this.lineColor);
        this.lPaint.setStrokeWidth(this.lineWidth);
        canvas.drawLine(0.0F, this.isTop?0.0F:(float)this.mHeight, (float)this.mWidth, this.isTop?0.0F:(float)this.mHeight, this.lPaint);

        this.lPaint.setColor(this.borderColor);
        this.lPaint.setStrokeWidth(this.lineWidth);
        Path path = new Path();
        path.moveTo((float)(this.halfWidth - this.trigonSize / 2), this.isTop?0.0F:(float)this.mHeight);
        path.lineTo((float)(this.halfWidth + this.trigonSize / 2), this.isTop?0.0F:(float)this.mHeight);
        path.lineTo((float)this.halfWidth, this.isTop?(float)(this.trigonSize / 2):(float)(this.mHeight - this.trigonSize / 2));
        path.close();
        canvas.drawPath(path,   this.lPaint);
//        this.indicateHeight=this.startY+this.indicateHeight;(float)this.indicateHeight
        canvas.drawLine((float)this.halfWidth, this.isTop?0.0F:(float)this.mHeight, (float)this.halfWidth, this.isTop?(float)this.indicateHeight:(float)(this.mHeight - this.indicateHeight), this.lPaint);
    }

    public boolean onTouchEvent(MotionEvent event) {
        if(this.velocityTracker == null) {
            this.velocityTracker = VelocityTracker.obtain();
        }

        this.velocityTracker.addMovement(event);
        switch(event.getAction()) {
            case 0:
                this.downX = (int)event.getX();
                break;
            case 1:
                this.velocityTracker.computeCurrentVelocity(1000);
                float xVelocity = this.velocityTracker.getXVelocity();
                if(Math.abs(xVelocity) < (float)this.minVelocity) {
                    this.correct();
                    this.isDrag = true;
                    return true;
                }

                this.isDrag = false;
                int startx = this.pixel * 10;
                int velocityX = (int)(-((double)Math.abs(xVelocity) + 0.5D));
                int maxX = this.end * this.pixel - this.mWidth;
                this.scroller.fling(startx, 0, velocityX, 0, 0, maxX, 0, 0);
                this.recycleVelocityTracker();
                break;
            case 2:
                this.isDrag = true;
                int moveX = (int)(event.getX() - (float)this.downX);
                if(this.lastMoveX == moveX) {
                    return true;
                }

                this.sumMoveX += moveX;
                if(moveX < 0) {
                    this.isLeft = true;
                    if(-this.sumMoveX > this.sumPixel) {
                        this.correct();
                        return true;
                    }
                } else {
                    this.isLeft = false;
                    if(this.sumMoveX >= this.mWidth) {
                        this.correct();
                        return true;
                    }
                }

                this.lastMoveX = moveX;
                this.downX = (int)event.getX();
                this.invalidate();
        }

        return true;
    }

    public void computeScroll() {
        boolean offset = this.scroller.computeScrollOffset();
        if(offset) {
            int currX = this.scroller.getCurrX();
            if(this.sumMoveX >= this.mWidth) {
                this.scroller.abortAnimation();
                this.correct();
                return;
            }

            if(-this.sumMoveX >= this.sumPixel) {
                this.scroller.abortAnimation();
                this.correct();
                return;
            }

            this.flag = true;
            if(this.isLeft) {
                this.sumMoveX -= currX;
            } else {
                this.sumMoveX += currX;
            }

            this.invalidate();
        } else if(!this.isDrag && (this.sumMoveX > this.halfWidth || -this.sumMoveX + this.halfWidth > this.sumPixel)) {
            this.scroller.abortAnimation();
            this.correct();
        }

        if(!offset && this.flag) {
            this.flag = false;
            this.checkOutData();
        }

    }

    private void correct() {
        if(this.sumMoveX > this.halfWidth) {
            if(this.isAnim) {
                return;
            }

            ValueAnimator diff = ValueAnimator.ofInt(new int[]{this.sumMoveX, this.halfWidth});
            diff.setDuration(this.animTime * (long)(this.sumMoveX - this.halfWidth) / (long)this.halfWidth);
            diff.setInterpolator(this.mInterpolator);
            diff.addListener(this);
            diff.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                public void onAnimationUpdate(ValueAnimator animation) {
                    ScaleView.this.sumMoveX = ((Integer)animation.getAnimatedValue()).intValue();
                    ScaleView.this.invalidate();
                }
            });
            diff.start();
        }

        if(-this.sumMoveX + this.halfWidth > this.sumPixel) {
            if(this.isAnim) {
                return;
            }

            float diff1 = (float)(-this.sumMoveX + this.halfWidth - this.sumPixel);
            float time = diff1 / (float)this.halfWidth;
            ValueAnimator valueAnimator = ValueAnimator.ofInt(new int[]{-this.sumMoveX + this.halfWidth, this.sumPixel - this.halfWidth});
            valueAnimator.setDuration((long)((float)this.animTime * time));
            valueAnimator.addListener(this);
            valueAnimator.setInterpolator(this.mInterpolator);
            valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                public void onAnimationUpdate(ValueAnimator animation) {
                    ScaleView.this.sumMoveX = -((Integer)animation.getAnimatedValue()).intValue();
                    ScaleView.this.invalidate();
                }
            });
            valueAnimator.start();
        }

        this.checkOutData();
    }

    private void recycleVelocityTracker() {
        if(this.velocityTracker != null) {
            this.velocityTracker.recycle();
            this.velocityTracker = null;
        }

    }

    private void selectItem() {
        if(this.selectItem <= this.end && this.selectItem >= this.begin) {
            this.sumMoveX = -((this.selectItem - this.begin) / this.step * this.pixel - this.halfWidth);
            this.invalidate();
        } else {
            throw new RuntimeException("设置所选值超出范围");
        }
    }

    public void onAnimationStart(Animator animation) {
        this.isAnim = true;
    }

    public void onAnimationEnd(Animator animation) {
        this.isAnim = false;
    }

    public void onAnimationCancel(Animator animation) {
        this.isAnim = false;
    }

    public void onAnimationRepeat(Animator animation) {
        this.isAnim = true;
    }

    public void setOnRulerValueChangeListener(ScaleView.RulerValue onRulerValueChangeListener) {
        this.onRulerValueChangeListener = onRulerValueChangeListener;
    }

    private void checkOutData() {
        if(this.sumMoveX < this.halfWidth || -this.sumMoveX + this.halfWidth < this.sumPixel) {
            int initData = -this.sumMoveX + this.halfWidth;
            int checkAfterData = initData;
            int dValue = initData % this.pixel;
            if(dValue != 0) {
                if(dValue > this.pixel / 2) {
                    checkAfterData = initData + (this.pixel - dValue);
                } else {
                    checkAfterData = initData - dValue;
                }
            }

            this.sumMoveX = -(checkAfterData - this.halfWidth);
            this.postInvalidate();
        }

    }

    public void setOnSelectItem(ScaleView.SelectItem onSelectItem) {
        this.onSelectItem = onSelectItem;
    }

    public interface SelectItem {
        int setSelectItem();
    }

    public interface RulerValue {
        void value(int var1);
    }
}
