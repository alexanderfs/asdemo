package demo.alexan.com.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Scroller;
import android.widget.TextView;
import android.widget.Toast;

import demo.alexan.com.myapplication.R;

/**
 * Created by Alex on 2015/6/12.
 */
public class AllInChartView extends LinearLayout {
    
    private ChartHeadView chv;
    private ChartLeftView clv;
    private ChartMainView cmv;
  /*  private ChartHeadView cb1v;
    private ChartHeadView cb2v;
    private ChartHeadView cb3v;
    private ChartHeadView cb4v;*/
    private LinearLayout vContainer;
    private LinearLayout cb5v;
    private LinearLayout cb6v;

    private DisplayMetrics dm;
    private int headWidth;
    private int sideHeight;

    private int preX;
    private int preY;

    private VelocityTracker vt;
    private Scroller mScrollerHead;
    private Scroller mScrollerLeft;

    private int mMaximumVelocity;
    private int mMinimunVelocity;

    private String[] arrayData = {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10",
            "11", "12", "13", "14", "15", "16", "17", "18", "19", "20",
            "21", "22", "23", "24", "25", "26", "27", "28", "29", "30",
            "31", "32", "33", "34", "35"};

    public AllInChartView(Context context) {
        super(context);
        init();
    }

    public AllInChartView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }
    
    private void init() {
        dm = getContext().getResources().getDisplayMetrics();
        
        LayoutInflater li = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        li.inflate(R.layout.hv_slide_chart, this);
        
        chv = (ChartHeadView) findViewById(R.id.chart_head);
        vContainer = (LinearLayout) findViewById(R.id.view_container);
        vContainer.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                sideHeight = vContainer.getHeight();
                cmv.setFrameSize(headWidth, sideHeight);
                initCustomClickRange();
            }
        });
        clv = (ChartLeftView) findViewById(R.id.chart_left_side);
        cmv = (ChartMainView) findViewById(R.id.chart_main);
        /*cb1v = (ChartHeadView) findViewById(R.id.chart_bottom1);
        cb2v = (ChartHeadView) findViewById(R.id.chart_bottom2);
        cb3v = (ChartHeadView) findViewById(R.id.chart_bottom3);
        cb4v = (ChartHeadView) findViewById(R.id.chart_bottom4);*/
        cb5v = (LinearLayout) findViewById(R.id.chart_bottom5);
        inflateChoosenBall();
        cb6v = (LinearLayout) findViewById(R.id.chart_bottom6);

        
        headWidth = (int)(dm.widthPixels - 80 * dm.density);
        //sideHeight = (int)dm.density * cmv.getHeight();

        mScrollerHead = new Scroller(getContext(), new DecelerateInterpolator(2.0f));
        chv.setScroller(mScrollerHead);
        chv.setChoosingView(cb5v);
        chv.setMainView(cmv);
        mScrollerLeft = new Scroller(getContext(), new DecelerateInterpolator(2.0f));
        clv.setScroller(mScrollerLeft);
        clv.setMainView(cmv);

        mMaximumVelocity = ViewConfiguration.get(getContext()).getScaledMaximumFlingVelocity();
        mMinimunVelocity = ViewConfiguration.get(getContext()).getScaledMinimumFlingVelocity();
    }
    
    private void inflateChoosenBall() {
        for(String text: arrayData) {
            Button b = new Button(getContext());
            LinearLayout.LayoutParams lp = new LayoutParams((int)dm.density * 30, (int)dm.density * 30);
            b.setLayoutParams(lp);
            b.setPadding(5, 5, 5, 5);
            b.setText(text);
            b.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getContext(), "this is " + v.getId(), Toast.LENGTH_SHORT).show();
                }
            });
            cb5v.addView(b);
        }
    }
    

    /*@Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                preX = (int)ev.getX();
                preY = (int)ev.getY();
                return true;
            case MotionEvent.ACTION_MOVE:
                //todo:这里改成了return true就接受不到touch事件了
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        return super.dispatchTouchEvent(ev);
    }*/

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

    private int lastEvent;
    private int x11, x12, y11, y12;
    private int x21, x22, y21, y22;

    private void initCustomClickRange() {
        x11 = 0;
        y11 = 0;
        x12 = 80 * (int)dm.density;
        y12 = 30 * (int)dm.density;

        x21 = 80 * (int)dm.density;
        y21 = y12 + sideHeight + y12;
        x22 = vContainer.getWidth();
        y22 = y21 + 30 * (int)dm.density;
    }

    private boolean isValidClick() {
         return lastEvent == MotionEvent.ACTION_DOWN;
    }

    private boolean isChangeRange(MotionEvent ev) {
        int x = (int)ev.getX();
        int y = (int)ev.getY();
        if(x >= x11 && x <= x12 && y >= y11 && y < y12) {
            return true;
        } else {
            return false;
        }
    }

    private int getChooseBall(MotionEvent ev) {
        int x = (int)ev.getX();
        int y = (int)ev.getY();
        if(x >= x21 && x <= x22 && y >= y21 && y <= y22) {
            return (x + chv.getScrollX()) / (30 * (int)dm.density);
        } else {
            return -1;
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        //onTouchEvent真是要你何用啊
        obtainVelocityTracker(ev);
        int currX = (int) ev.getX();
        int currY = (int) ev.getY();
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                preX = (int)ev.getX();
                preY = (int)ev.getY();
                if(!mScrollerHead.isFinished()) {
                    mScrollerHead.abortAnimation();
                }
                if(!mScrollerLeft.isFinished()) {
                    mScrollerLeft.abortAnimation();
                }
                lastEvent = MotionEvent.ACTION_DOWN;
                //这里必须返回true
                return true;
            case MotionEvent.ACTION_MOVE:
                //计算如果要移动的话新的移动位置
                int newHeadX = chv.getScrollX() + preX - currX;
                int newLeftY = clv.getScrollY() + preY - currY;
                boolean xMove = newHeadX > 0 && newHeadX < (chv.getWidth() - headWidth);
                //todo: 需要在已选号码那列高度改变时重新计算这里的实际高度
                boolean yMove = newLeftY > 0 && newLeftY < (clv.getHeight() - sideHeight);
                if(xMove) {
                    chv.scrollBy(preX - currX, 0);
                    cb5v.scrollBy(preX - currX, 0);
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
                lastEvent = MotionEvent.ACTION_MOVE;
                break;
            case MotionEvent.ACTION_UP:
                if(isValidClick()) {
                    if(isChangeRange(ev)) {
                        Toast.makeText(getContext(), "change range!", Toast.LENGTH_SHORT).show();
                    } else {
                        int idx = getChooseBall(ev);
                        if(idx > -1) {
                            Toast.makeText(getContext(), "ball " + idx + " got choosen!", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                vt.computeCurrentVelocity(1000, mMaximumVelocity);
                int xv = (int) vt.getXVelocity();
                int yv = (int) vt.getYVelocity();
                if(Math.abs(xv) > xv || Math.abs(xv) > mMinimunVelocity) {
                    mScrollerHead.fling(chv.getScrollX(), 0, -xv, 0, 0, chv.getWidth() - headWidth, 0, 0);
                } else {
                    mScrollerHead.fling(chv.getScrollX(), 0, xv, 0, 0, chv.getWidth() - headWidth, 0, 0);
                }

                if(Math.abs(yv) > yv || Math.abs(yv) > mMinimunVelocity) {
                    mScrollerLeft.fling(0, clv.getScrollY(), 0, -yv, 0, 0, 0, clv.getHeight() - vContainer.getHeight());
                } else {
                    mScrollerLeft.fling(0, clv.getScrollY(), 0, yv, 0, 0, 0, clv.getHeight() - vContainer.getHeight());
                }
                releaseVelocityTracker();
                lastEvent = MotionEvent.ACTION_UP;
                break;
        }
        return super.onTouchEvent(ev);
    }
}
