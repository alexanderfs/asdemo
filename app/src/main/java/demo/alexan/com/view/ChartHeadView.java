package demo.alexan.com.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Scroller;

import org.w3c.dom.Text;

import demo.alexan.com.myapplication.R;

/**
 * Created by Alex on 2015/6/10.
 */
public class ChartHeadView extends View {
    
    private Paint p;
    private Paint p2;
    private Paint pb;
    private Scroller mScroller;
    private ChartMainView mainView;
    private LinearLayout choosingView;
    private String[] arrayData;
    private int columnNumber = 35;
    private DisplayMetrics dm;
    
    public void setDimension(int columnNumber) {
        this.columnNumber = columnNumber;
        generateData();
    }
    
    private void generateData() {
        arrayData = new String[columnNumber];
        for(int i = 0; i < columnNumber; i++) {
            arrayData[i] = "" + i;
        }
    }
    
    public ChartHeadView(Context context) {
        super(context);
        init(context);
    }

    public ChartHeadView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ChartHeadView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }
    
    private void init(Context ctx) {
        generateData();
        dm = ctx.getResources().getDisplayMetrics();
        p = new Paint(Paint.ANTI_ALIAS_FLAG);
        p.setColor(ctx.getResources().getColor(R.color.line_color));
        /*p.setStrokeJoin(Paint.Join.ROUND);
        p.setStrokeCap(Paint.Cap.ROUND);
        p.setStrokeWidth(3);*/
        p2 = new Paint(Paint.ANTI_ALIAS_FLAG);
        p2.setColor(ctx.getResources().getColor(R.color.word_color));
        p2.setTextSize(50);
        pb = new Paint(Paint.ANTI_ALIAS_FLAG);
        pb.setColor((ctx.getResources().getColor(R.color.bg_color)));
    }
    
    public void setScroller(Scroller scroller) {
        this.mScroller = scroller;
    }

    public void setMainView(ChartMainView mainView) {
        this.mainView = mainView;
    }

    public void setArrayData(String[] arrayData) {
        this.arrayData = arrayData;
    }

    public void setChoosingView(LinearLayout choosingView) {
        this.choosingView = choosingView;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int measuredWidth = getMeasureParam(widthMeasureSpec, 0);
        int measuredHeight = getMeasureParam(heightMeasureSpec, 1);
        setMeasuredDimension(measuredWidth, measuredHeight);
        //super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
    
    private int getMeasureParam(int measureSpec, int type) {
        
        if(type == 0) {
            return (int)dm.density * columnNumber * 30;
        } else {
            return (int)dm.density * 30;
        }
    }
    
    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawColor(getContext().getResources().getColor(R.color.bg_color));
        int height = getMeasuredHeight();
        int currX = -height;
        for(int i = 0; i < arrayData.length; i++) {
            currX += height;
            canvas.drawLine(currX + height, 0, currX + height, height, p);
            calculateBase(arrayData[i]);
            canvas.drawText(arrayData[i], currX + xbase, ybase, p2);
        }
        super.onDraw(canvas);
    }
    
    private int xbase;
    private int ybase;
    
    private void calculateBase(String txt) {
        Paint.FontMetrics fm = p2.getFontMetrics();
        float wordWidth = p2.measureText(txt);
        xbase = (getMeasuredHeight() - (int)wordWidth) / 2;
        ybase = (int)(getMeasuredHeight() / 2 - fm.descent + (fm.bottom - fm.top) / 2);
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if(mScroller != null && mScroller.computeScrollOffset()) {
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            if(mainView != null) {
                mainView.setMyScrollX(mScroller.getCurrX());
            }
            if(choosingView != null) {
                choosingView.scrollTo(mScroller.getCurrX(), choosingView.getScrollY());
            }
            postInvalidate();
        }
    }
}
