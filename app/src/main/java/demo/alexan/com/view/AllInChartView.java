package demo.alexan.com.view;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.DecelerateInterpolator;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.Scroller;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import demo.alexan.com.myapplication.R;

/**
 * Created by Alex on 2015/6/12.
 */
public class AllInChartView extends LinearLayout {
    
    private ChartHeadView chv;
    private ChartLeftView clv;
    private ChartMainView cmv;
    private LinearLayout vContainer;
    private LinearLayout cb5v;
    private GridView cb6v;

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

    private String[] arrayData;
    private ArrayList<String> choosenData = new ArrayList<String>();
    
    private void generateData() {
        arrayData = new String[35];
        for(int i = 0; i < 35; i++) {
            arrayData[i] = "" + i;
        }
    }

    public AllInChartView(Context context) {
        super(context);
        init();
    }

    public AllInChartView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }
    
    private void init() {
        generateData();
        
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
                //adjustPostion();
                Log.d("ongloballayout", "layout");
            }
        });
        clv = (ChartLeftView) findViewById(R.id.chart_left_side);
        clv.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                Log.d("viewobserver", "" + clv.getMeasuredHeight());
                clv.post(new Runnable() {
                    @Override
                    public void run() {
                        clv.scrollTo(0, clv.getMeasuredHeight() - sideHeight);
                    }
                });
                //clv.invalidate();
            }
        });
        cmv = (ChartMainView) findViewById(R.id.chart_main);
        cmv.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                /*Log.d("viewobserver1", "" + cmv.getMeasuredHeight() + ", " + cmv.getScrollY());
                cmv.scrollTo(0, cmv.getMeasuredHeight() - sideHeight);
                Log.d("viewobserver2", "" + cmv.getMeasuredHeight() + ", " + cmv.getScrollY());*/
                cmv.post(new Runnable() {
                    @Override
                    public void run() {
                        Log.d("viewobserver", "" + cmv.getMeasuredHeight() + ", " + cmv.getScrollY());
                        cmv.scrollTo(0, cmv.getMeasuredHeight() - sideHeight);
                        Log.d("viewobserver", "" + cmv.getMeasuredHeight() + ", " + cmv.getScrollY());
                    }
                });
                chv.post(new Runnable() {
                    @Override
                    public void run() {
                        chv.scrollTo(0, 0);
                    }
                });
            }
        });
        
        cb5v = (LinearLayout) findViewById(R.id.chart_bottom5);
        inflateChoosenBall();
        cb6v = (GridView) findViewById(R.id.chart_bottom6);
        initGridView();
        
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
    
    private void adjustPostion() {
        clv.scrollTo(0, clv.getMeasuredHeight() - sideHeight);
        cmv.scrollTo(0, clv.getMeasuredHeight() - sideHeight);
    }
    
    private ArrayAdapter aa;
    
    private void initGridView() {
        //cb6v.setHorizontalSpacing((int)dm.density);
        //cb6v.setVerticalSpacing((int) dm.density);
        //cb6v.setNumColumns((dm.widthPixels - (int) dm.density * 80) / 30);
        aa = new ArrayAdapter(getContext(), R.layout.gridview_item, choosenData);
        cb6v.setAdapter(aa);
        Log.d("initgridview", "init");
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
        y21 = y12 + sideHeight;
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
            return (x - x21 + chv.getScrollX()) / (30 * (int)dm.density);
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
                        showChangeRangeDialog();
                    } else {
                        int idx = getChooseBall(ev);
                        int idx2 = -1;
                        if(idx > -1 && idx < arrayData.length) {
                            if((idx2 = choosenData.indexOf(arrayData[idx])) != -1) {
                                choosenData.remove(idx2);
                            } else {
                                choosenData.add(arrayData[idx]);    
                            }
                            aa.notifyDataSetChanged();
                            //todo: 这两句好像没啥用
                            //requestLayout();
                            //invalidate();
                            //Toast.makeText(getContext(), "ball " + idx + " got choosen!", Toast.LENGTH_SHORT).show();
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
    
    private String[] rangeData = new String[] {
            "最近30期", "最近50期", "最近100期", "最近200期"
    };
    
    private int[] rangeInt = new int[]{30, 50, 100, 200};

    private void showChangeRangeDialog() {
        new AlertDialog.Builder(getContext())
                .setTitle("选择范围")
                .setItems(rangeData, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        changeRange(which);
                    }
                }).create().show();
    } 
    
    private void changeRange(int which) {
        cmv.setChartDimension(rangeInt[which], 35);
        cmv.requestLayout();
        clv.setDimension(rangeInt[which]);
        clv.requestLayout();
        
        //this.requestLayout();
        
    }
    
    
    public void changeBall(int line, int column) {
        cmv.setChartDimension(line, column);
        clv.setDimension(line);
        chv.setDimension(column);
        cmv.requestLayout();
        clv.requestLayout();
    }
}
