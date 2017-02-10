package com.example.shuiai.defineviewpagegroup;

import android.content.Context;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Scroller;

/**
 * @author shuiai@dianjia.io
 * @Company 杭州木瓜科技有限公司
 * @date 2017/2/9
 */

public class DefineViewGroup extends ViewGroup {
    /**
     * group的宽
     */
    private int width;
    /**
     * group的高
     */
    private int height;
    /**
     * 滑动类
     */
    private Scroller scroller;
    /**
     * down的x坐标
     */
    private int downx;
    /**
     * down的y坐标
     */
    private int downy;

    /**
     * 当前页数
     */
    private int currentPage;
    /**
     * 滑动的距离
     */
    private int dx;
    /**
     * 手势滑动的对象
     */
    GestureDetector gestureDetector;

    public DefineViewGroup(Context context) {
        super(context);
        init();
    }

    public DefineViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public DefineViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        scroller = new Scroller(getContext());
        addGestureDetector();
    }

    /**
     * 添加一个手势滑动的监听
     */
    private void addGestureDetector() {
        gestureDetector = new GestureDetector(new GestureDetector.OnGestureListener() {
            @Override
            public boolean onDown(MotionEvent motionEvent) {
                return false;
            }

            @Override
            public void onShowPress(MotionEvent motionEvent) {

            }

            @Override
            public boolean onSingleTapUp(MotionEvent motionEvent) {
                return false;
            }

            /**
             * 拦截掉onscroll事件，并做判断，当在第一页时若继续向右滑动，则不拦截，当在最后一页时若继续向左滑动则不拦截
             * @param motionEvent
             * @param motionEvent1
             * @param v
             * @param v1
             * @return
             */
            @Override
            public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
                if (currentPage == 0 && v < 0) {
                    return false;
                }
                if (currentPage == getChildCount() - 1 && v > 0)
                    return false;
                scrollBy((int) v, 0);
                return true;
            }

            @Override
            public void onLongPress(MotionEvent motionEvent) {

            }

            @Override
            public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
                return false;
            }
        });
    }

    @Override
    protected void onLayout(boolean b, int i, int i1, int i2, int i3) {
        for (int j = 0; j < getChildCount(); j++) {
            View view = getChildAt(j);
            view.layout(getWidth() * j, 0, getWidth() * j + getWidth(), getHeight());
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        if (widthMode == MeasureSpec.EXACTLY) {
            width = widthSize;
        } else {
            throw new IllegalArgumentException("请固定宽和高");
        }
        if (heightMode == MeasureSpec.EXACTLY) {
            height = heightSize;
        } else {
            throw new IllegalArgumentException("请固定宽和高");
        }
        setMeasuredDimension(width, height);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        gestureDetector.onTouchEvent(event);
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downx = (int) event.getX();
                downy = (int) event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:
                int upx = (int) event.getX();
                int upy = (int) event.getY();
                if (upx - downx > getWidth() / 3) {
                    currentPage--;
                } else if (upx - downx < -getWidth() / 3) {
                    currentPage++;
                }
                uodate(currentPage);
                break;
        }
        return true;
    }

    /**
     * 更新滑动的距离
     * 通过滑动页数控制显示的图片
     *
     * @param index
     */
    private void uodate(int index) {
        if (index <= 0) {
            currentPage = 0;
        } else if (index >= getChildCount() - 1) {
            currentPage = getChildCount() - 1;
        }
        dx = getWidth() * currentPage - getScrollX();
        scroller.startScroll(getScrollX(), 0, dx, 0);
        invalidate();
    }

    /**
     * scroller调用startScroll时必须配合computeScroll一起使用
     */
    @Override
    public void computeScroll() {
        if (scroller.computeScrollOffset()) {
            scrollTo(scroller.getCurrX(), 0);
            invalidate();
        }
    }
}
