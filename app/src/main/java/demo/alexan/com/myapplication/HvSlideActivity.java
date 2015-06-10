package demo.alexan.com.myapplication;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewTreeObserver;
import android.view.animation.DecelerateInterpolator;
import android.widget.Scroller;
import android.widget.TextView;

import demo.alexan.com.view.ChartHeadView;

/**
 * Created by Alex on 2015/6/10.
 */
public class HvSlideActivity extends Activity {
    
    //private TextView chartHead;
    private ChartHeadView chHead;
    private ChartHeadView chLeft;
    private ChartHeadView chMain;
    private int headWidth;
    private int sideHeight;
    private int preX;
    private int preY;
    private VelocityTracker vt;
    private Scroller mScroller;
    private int mMaximumVelocity;
    private int mMinimumVelocity;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hv_slide_chart);
        //chartHead = (TextView) findViewById(R.id.chart_head_text);
        chHead = (ChartHeadView) findViewById(R.id.chart_head);
        chLeft = (ChartHeadView) findViewById(R.id.chart_left_side);
        chMain = (ChartHeadView) findViewById(R.id.chart_main);
        init();
    }
    
    private void init() {
        DisplayMetrics dm = this.getResources().getDisplayMetrics();
        headWidth = (int)(dm.widthPixels - 50 * dm.density);
        sideHeight = (int)dm.density * 400;
        mScroller = new Scroller(this, new DecelerateInterpolator(2.0f));
        mMaximumVelocity = ViewConfiguration.get(this).getScaledMaximumFlingVelocity();
        mMinimumVelocity = ViewConfiguration.get(this).getScaledMinimumFlingVelocity();
        chMain.setScroller(mScroller);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                preX = (int)ev.getX();
                preY = (int)ev.getY();
                break;
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
    public boolean onTouchEvent(MotionEvent event) {
        obtainVelocityTracker(event);
        int currX = (int) event.getX();
        int currY = (int) event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if(!mScroller.isFinished()) {
                    mScroller.abortAnimation();
                }
                break;
            case MotionEvent.ACTION_MOVE:
                
                int newHeadX = chHead.getScrollX() + preX - currX;
                if(newHeadX > 0 && newHeadX < (chHead.getWidth() - headWidth)) {
                    chHead.scrollBy(preX - currX, 0);    
                }
                int newLeftY = chLeft.getScrollY() + preY - currY;
                if(newLeftY > 0 && newLeftY < (chLeft.getHeight() - sideHeight)) {
                    chLeft.scrollBy(0, preY - currY);    
                }
                if(newHeadX > 0 && newHeadX < (chMain.getWidth() - headWidth)
                    && newLeftY > 0 && newLeftY < (chMain.getHeight() - sideHeight)) {
                    chMain.scrollBy(preX - currX, preY - currY);    
                }
                preX = currX;
                preY = currY;
                break;
            case MotionEvent.ACTION_UP:
                vt.computeCurrentVelocity(1000, mMaximumVelocity);
                int initialVelocitx = (int) vt.getXVelocity();
                int initialVelocity = (int) vt.getYVelocity();
                if (Math.abs(initialVelocitx) > initialVelocitx || Math.abs(initialVelocity) > mMinimumVelocity) {
                    mScroller.fling(currX, currY, -initialVelocitx, -initialVelocity, 0, chMain.getWidth() - headWidth, 0, chMain.getHeight() - sideHeight);
                } else {
                    mScroller.fling(currX, currY, initialVelocitx, initialVelocity, 0, chMain.getWidth() - headWidth, 0, chMain.getHeight() - sideHeight);
                }
                
                releaseVelocityTracker();
                break;
        }
        return super.onTouchEvent(event);
    }

    /*private void fling(int velocityX, int velocityY) {
        
        if (getChildCount() > 0) {
            int width = getWidth() - getPaddingRight() - getPaddingLeft();
            int right = getChildAt(0).getWidth();

            int height = getHeight() - getPaddingBottom() - getPaddingTop();
            int bottom = getChildAt(0).getHeight();

            mScroller.fling(chMain.getScrollX(), chMain.getScrollY(), velocityX, velocityY,
                    0, Math.max(0, right - width),
                    0, Math.max(0, bottom - height));

            invalidate();
        }
    }*/

}
