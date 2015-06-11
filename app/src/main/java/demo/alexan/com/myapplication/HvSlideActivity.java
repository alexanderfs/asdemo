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
import demo.alexan.com.view.ChartLeftView;
import demo.alexan.com.view.ChartMainView;

/**
 * Created by Alex on 2015/6/10.
 */
public class HvSlideActivity extends Activity {
    
    //private TextView chartHead;
    private ChartHeadView chHead;
    private ChartLeftView chLeft;
    private ChartMainView chMain;
    private int headWidth;
    private int sideHeight;
    private int preX;
    private int preY;
    private VelocityTracker vt;
    private Scroller mScrollerHead;
    private Scroller mScrollerLeft;
    //private Scroller mScrollerMain;
    private int mMaximumVelocity;
    private int mMinimumVelocity;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hv_slide_chart);
        chHead = (ChartHeadView) findViewById(R.id.chart_head);
        chLeft = (ChartLeftView) findViewById(R.id.chart_left_side);
        chMain = (ChartMainView) findViewById(R.id.chart_main);
        init();
    }
    
    
    private void init() {
        DisplayMetrics dm = this.getResources().getDisplayMetrics();
        headWidth = (int)(dm.widthPixels - 50 * dm.density);
        sideHeight = (int)dm.density * 400;
        
        mScrollerHead = new Scroller(this, new DecelerateInterpolator(2.0f));
        chHead.setScroller(mScrollerHead);
        chHead.setMainView(chMain);
        mScrollerLeft = new Scroller(this, new DecelerateInterpolator(2.0f));
        chLeft.setScroller(mScrollerLeft);
        chLeft.setMainView(chMain);
        //mScrollerMain = new Scroller(this, new DecelerateInterpolator(2.0f));
        //chMain.setScroller(mScrollerMain);
        mMaximumVelocity = ViewConfiguration.get(this).getScaledMaximumFlingVelocity();
        mMinimumVelocity = ViewConfiguration.get(this).getScaledMinimumFlingVelocity();
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
                if(!mScrollerLeft.isFinished()) {
                    mScrollerLeft.abortAnimation();
                }
                if(!mScrollerHead.isFinished()) {
                    mScrollerHead.abortAnimation();
                }
                /*if(!mScrollerMain.isFinished()) {
                    mScrollerMain.abortAnimation();
                }*/
                break;
            case MotionEvent.ACTION_MOVE:
                
                int newHeadX = chHead.getScrollX() + preX - currX;
                int newLeftY = chLeft.getScrollY() + preY - currY;
                boolean xMove = newHeadX > 0 && newHeadX < (chHead.getWidth() - headWidth);
                boolean yMove = newLeftY > 0 && newLeftY < (chLeft.getHeight() - sideHeight); 
                if(xMove) {
                    chHead.scrollBy(preX - currX, 0);
                    //chMain.scrollBy(preX - currX, 0);
                    if(yMove) {
                        chMain.scrollBy(preX - currX, preY - currY);    
                    } else {
                        chMain.scrollBy(preX - currX, 0);
                    }
                }
                if(yMove) {
                    chLeft.scrollBy(0, preY - currY);
                    //chMain.scrollBy(0, preY - currY);
                    if(!xMove) {
                        chMain.scrollBy(0, preY - currY);
                    }
                }
                
                preX = currX;
                preY = currY;
                break;
            case MotionEvent.ACTION_UP:
                vt.computeCurrentVelocity(1000, mMaximumVelocity);
                int xv = (int) vt.getXVelocity();
                int yv = (int) vt.getYVelocity();
                if (Math.abs(xv) > xv || Math.abs(xv) > mMinimumVelocity) {
                    mScrollerHead.fling(chHead.getScrollX(), 0, -xv, 0, 0, chHead.getWidth() - headWidth, 0, 0);
                } else {
                    mScrollerHead.fling(chHead.getScrollX(), 0, xv, 0, 0, chHead.getWidth() - headWidth, 0, 0);
                }
                
                if(Math.abs(yv) > yv || Math.abs(yv) > mMinimumVelocity) {
                    mScrollerLeft.fling(0, chLeft.getScrollY(), 0, -yv, 0, 0, 0, chLeft.getHeight() - sideHeight);
                } else {
                    mScrollerLeft.fling(0, chLeft.getScrollY(), 0, yv, 0, 0, 0, chLeft.getHeight() - sideHeight);
                }
                
                /*if(() && ()) {
                    mScrollerMain.fling(chMain.getScrollX(), chMain.getScrollY(),
                            -xv, -yv, 0, chMain.getWidth() - headWidth, 0, chMain.getHeight() - sideHeight);
                }*/
                
                releaseVelocityTracker();
                break;
        }
        return super.onTouchEvent(event);
    }

}
