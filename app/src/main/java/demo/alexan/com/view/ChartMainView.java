package demo.alexan.com.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Scroller;

/**
 * Created by Alex on 2015/6/10.
 */
public class ChartMainView extends View {
    
    private int minHeight;
    private int minWidth;
    private int paperColor;
    private Paint p;
    private Scroller mScroller;
    private DisplayMetrics dm;
    
    public ChartMainView(Context context) {
        super(context);
        init(context);
    }

    public ChartMainView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ChartMainView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }
    
    private void init(Context ctx) {
        dm = ctx.getResources().getDisplayMetrics();
        minHeight = (int)dm.density * 50;
        minWidth = (int)dm.density * 30 * 30;
        paperColor = ctx.getResources().getColor(android.R.color.holo_red_dark);
        p = new Paint(Paint.ANTI_ALIAS_FLAG);
        p.setColor(ctx.getResources().getColor(android.R.color.holo_blue_dark));
        p.setStrokeJoin(Paint.Join.ROUND);
        p.setStrokeCap(Paint.Cap.ROUND);
        p.setStrokeWidth(3);
    }
    
    public void setScroller(Scroller scroller) {
        this.mScroller = scroller;
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
    
    public void setMyScrollX(int x) {
        //super.mScrollX;
        scrollTo(x, this.getScrollY());
    }
    
    public void setMyScrollY(int y) {
        scrollTo(this.getScrollX(), y);
    }
    
    @Override
    protected void onDraw(Canvas canvas) {
        int width = getMeasuredWidth();
        int height = getMeasuredHeight();
        int squareSize = 50 * (int)dm.density;
        int lineCount = 12;
        int columnCount = 18;
        int squarePad = 5;
        int currX = -squareSize;
        int currY;
        for(int i = 0; i < lineCount; i++) {
            currX += squareSize;
            currY = -squareSize;
            for(int j = 0; j < columnCount; j++) {
                currY += squareSize;
                canvas.drawRect(currX + squarePad, currY + squarePad, currX + squareSize - squarePad, currY + squareSize - squarePad, p);
            }
        }
        //canvas.drawLine(0, 0, width, height, p);
        super.onDraw(canvas);
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if(mScroller != null && mScroller.computeScrollOffset()) {
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            postInvalidate();
        }
    }
}
