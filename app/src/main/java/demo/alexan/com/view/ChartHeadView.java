package demo.alexan.com.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Scroller;

/**
 * Created by Alex on 2015/6/10.
 */
public class ChartHeadView extends View {
    
    private int minHeight;
    private int minWidth;
    private int paperColor;
    private Paint p;
    private Paint p2;
    private Scroller mScroller;
    private ChartMainView mainView;
    
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
        DisplayMetrics dm = ctx.getResources().getDisplayMetrics();
        minHeight = (int)dm.density * 50;
        minWidth = (int)dm.density * 30 * 30;
        paperColor = ctx.getResources().getColor(android.R.color.holo_red_dark);
        p = new Paint(Paint.ANTI_ALIAS_FLAG);
        p.setColor(ctx.getResources().getColor(android.R.color.holo_blue_dark));
        p.setStrokeJoin(Paint.Join.ROUND);
        p.setStrokeCap(Paint.Cap.ROUND);
        p.setStrokeWidth(3);
        p2 = new Paint(Paint.ANTI_ALIAS_FLAG);
        p.setColor(ctx.getResources().getColor(android.R.color.holo_red_dark));
        //p2.setStrokeJoin(Paint.Join.ROUND);
        //p2.setStrokeCap(Paint.Cap.ROUND);
        //p2.setStrokeWidth(3);
        p2.setTextSize(100);
    }
    
    public void setScroller(Scroller scroller) {
        this.mScroller = scroller;
    }

    public void setMainView(ChartMainView mainView) {
        this.mainView = mainView;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int measuredWidth = getMeasureParam(widthMeasureSpec, 0);
        int measuredHeight = getMeasureParam(heightMeasureSpec, 1);
        setMeasuredDimension(measuredWidth, measuredHeight);
        //super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
    
    private int getMeasureParam(int measureSpec, int type) {
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);
        
        if(type == 0) {
            if(specMode == MeasureSpec.EXACTLY) {
                return specSize;
            } else {
                return minWidth;    
            }
        } else {
            if(specMode == MeasureSpec.EXACTLY) {
                return specSize;
            } else {
                return minHeight;
            }
        }
    }
    
    @Override
    protected void onDraw(Canvas canvas) {
        int width = getMeasuredWidth();
        int height = getMeasuredHeight();
        int squareCount = 18;
        int squarePad = 5;
        int currX = -height;
        for(int i = 0; i < squareCount; i++) {
            currX += height;
            canvas.drawRect(currX + squarePad, squarePad, currX + height - squarePad, height - squarePad, p);
            canvas.drawText("" + i, currX + squarePad, height - squarePad, p2);
        }
        super.onDraw(canvas);
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if(mScroller != null && mScroller.computeScrollOffset()) {
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            if(mainView != null) {
                mainView.setMyScrollX(mScroller.getCurrX());
            }
            postInvalidate();
        }
    }
}
