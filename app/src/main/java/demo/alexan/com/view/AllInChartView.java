package demo.alexan.com.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.LinearLayout;
import android.widget.Scroller;

import demo.alexan.com.myapplication.R;

/**
 * Created by Alex on 2015/6/12.
 */
public class AllInChartView extends LinearLayout {
    
    private ChartHeadView chv;
    private ChartLeftView clv;
    private ChartMainView cmv;

    private int headWidth;
    private int sideHeight;

    private int preX;
    private int preY;

    private VelocityTracker vt;
    private Scroller mScrollerHead;
    private Scroller mScrollerLeft;

    private int mMaximumVelocity;
    private int mMinimunVelocity;

    public AllInChartView(Context context) {
        super(context);
        init();
    }

    public AllInChartView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }
    
    private void init() {
        LayoutInflater li = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        li.inflate(R.layout.hv_slide_chart, this);
        chv = (ChartHeadView) findViewById(R.id.chart_head);
        clv = (ChartLeftView) findViewById(R.id.chart_left_side);
        cmv = (ChartMainView) findViewById(R.id.chart_main);

        DisplayMetrics dm = getContext().getResources().getDisplayMetrics();
        headWidth = (int)(dm.widthPixels - 50 * dm.density);
        sideHeight = (int)dm.density * 400;

        mScrollerHead = new Scroller(getContext(), new DecelerateInterpolator(2.0f));
        chv.setScroller(mScrollerHead);
        chv.setMainView(cmv);
        mScrollerLeft = new Scroller(getContext(), new DecelerateInterpolator(2.0f));
        clv.setScroller(mScrollerLeft);
        clv.setMainView(cmv);

        mMaximumVelocity = ViewConfiguration.get(getContext()).getScaledMaximumFlingVelocity();
        mMinimunVelocity = ViewConfiguration.get(getContext()).getScaledMinimumFlingVelocity();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                preX = (int)ev.getX();
                preY = (int)ev.getY();
                return true;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        return super.dispatchTouchEvent(ev);
    }

    private void obtainVelocityTracker(MotionEvent ev) {
        if(vt == null) {
            vt = VelocityTracker.obtain();
        }
        vt.addMovement(ev);
    }

    private void releaseVelocityTracker() {
        if(vt != null) {
            vt.clear();
            vt.recycle();
            vt = null;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        obtainVelocityTracker(ev);
        int currX = (int) ev.getX();
        int currY = (int) ev.getY();
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if(!mScrollerHead.isFinished()) {
                    mScrollerHead.abortAnimation();
                }
                if(!mScrollerLeft.isFinished()) {
                    mScrollerLeft.abortAnimation();
                }
                break;
            case MotionEvent.ACTION_MOVE:
                //计算如果要移动的话新的移动位置
                int newHeadX = chv.getScrollX() + preX - currX;
                int newLeftY = clv.getScrollY() + preY - currY;
                boolean xMove = newHeadX > 0 && newHeadX < (chv.getWidth() - headWidth);
                boolean yMove = newLeftY > 0 && newLeftY < (clv.getHeight() - sideHeight);
                if(xMove) {
                    chv.scrollBy(preX - currX, 0);
                    if(yMove) {
                        cmv.scrollBy(preX - currX, preY - currY);
                    } else {
                        cmv.scrollBy(preX - currX, 0);
                    }
                }
                if(yMove) {
                    clv.scrollBy(0, preY - currY);
                    if(!xMove) {
                        cmv.scrollBy(0, preY - currY);
                    }
                }
                preX = currX;
                preY = currY;
                return true;
            case MotionEvent.ACTION_UP:
                vt.computeCurrentVelocity(1000, mMaximumVelocity);
                int xv = (int) vt.getXVelocity();
                int yv = (int) vt.getYVelocity();
                if(Math.abs(xv) > xv || Math.abs(xv) > mMinimunVelocity) {
                    mScrollerHead.fling(chv.getScrollX(), 0, -xv, 0, 0, chv.getWidth() - headWidth, 0, 0);
                } else {
                    mScrollerHead.fling(chv.getScrollX(), 0, xv, 0, 0, chv.getWidth() - headWidth, 0, 0);
                }

                if(Math.abs(yv) > yv || Math.abs(yv) > mMinimunVelocity) {
                    mScrollerLeft.fling(0, clv.getScrollY(), 0, -yv, 0, 0, 0, clv.getHeight() - sideHeight);
                } else {
                    mScrollerLeft.fling(0, clv.getScrollY(), 0, yv, 0, 0, 0, clv.getHeight() - sideHeight);
                }
                releaseVelocityTracker();
                break;
        }
        return super.onTouchEvent(ev);
    }
}
