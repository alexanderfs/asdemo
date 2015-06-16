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
public class ChartMainView extends View {
    
    //private int minHeight;
    //private int minWidth;
    private int paperColor;
    private Paint p;
    private Paint p2;
    private Scroller mScroller;
    private DisplayMetrics dm;
    private String[][] arrayData;
    
    private int lineNumber = 100;
    private int columnNumber = 35;

    public void setChartDimension(int lineNumber, int columnNumber) {
        this.lineNumber = lineNumber;
        this.columnNumber = columnNumber;
        generateData();
    }

    private void generateData() {
        arrayData = new String[4 + lineNumber][columnNumber];
        for(int i = 0; i < (4 + lineNumber); i++) {
            for(int j = 0; j < columnNumber; j++) {
                arrayData[i][j] = "" + i + j;
            }
        }
    }
    
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
        generateData();
        
        dm = ctx.getResources().getDisplayMetrics();
        //minHeight = (int)dm.density * 30 * 104;
        //minWidth = (int)dm.density * 30 * 35;
        paperColor = ctx.getResources().getColor(android.R.color.holo_red_dark);
        p = new Paint(Paint.ANTI_ALIAS_FLAG);
        p.setColor(ctx.getResources().getColor(android.R.color.holo_blue_dark));
        p.setStrokeJoin(Paint.Join.ROUND);
        p.setStrokeCap(Paint.Cap.ROUND);
        p.setStrokeWidth(3);
        p2 = new Paint(Paint.ANTI_ALIAS_FLAG);
        p2.setColor(ctx.getResources().getColor(android.R.color.holo_red_dark));
        p2.setTextSize(50);
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
        /*int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);*/
        
        if(type == 0) {
            return (int)dm.density * 30 * columnNumber;
        } else {
            return (int)dm.density * 30 * (lineNumber + 4);
        }
    }
    
    public void setMyScrollX(int x) {
        //super.mScrollX;
        scrollTo(x, this.getScrollY());
    }
    
    public void setMyScrollY(int y) {
        scrollTo(this.getScrollX(), y);
    }
    
    private int frameWidth;
    private int frameHeight;
    private int lineCount;
    private int columnCount;
    
    public void setFrameSize(int w, int h) {
        int squareSize = 30 * (int)dm.density;
        frameWidth = w;
        columnCount = frameWidth / squareSize + 2;
        frameHeight = h;
        lineCount = frameHeight / squareSize + 2;
    }
    
    private int myGetScrollX() {
        if(getScrollX() + frameWidth > getMeasuredWidth()) {
            return getMeasuredWidth() - frameWidth;
        } else {
            return getScrollX();
        }
    }
    
    private int myGetScrollY() {
        if(getScrollY() + frameHeight > getMeasuredHeight()) {
            scrollTo(getScrollX(), getMeasuredHeight() - frameHeight);
            return getMeasuredHeight() - frameHeight;
            
        } else {
            return getScrollY();
        }
    }
    
    @Override
    protected void onDraw(Canvas canvas) {
        //canvas.clipRect(getScrollX(), getScrollY(), getScrollX() + 300, getScrollY() + 300 );
        int squareSize = 30 * (int)dm.density;
        int startx = getScrollX() / squareSize;
        int endx = startx + columnCount > columnNumber ? columnNumber : startx + columnCount;
        int startC = startx * squareSize;

        Log.d("ondraw", "" + getMeasuredHeight() + ", " + getScrollY());
        int starty = getScrollY() / squareSize;
        int endy = starty + lineCount > (lineNumber + 4) ? (lineNumber + 4) : starty + lineCount;
        int startR = starty * squareSize;
        
        
        int squarePad = 5;
        int currR = startR - squareSize;
        int currC;
        for(int i = starty; i < endy; i++) {
            currR += squareSize;
            currC = startC - squareSize;
            for(int j = startx; j < endx; j++) {
                currC += squareSize;
                canvas.drawRect(currC + squarePad, currR + squarePad, currC + squareSize - squarePad, currR + squareSize - squarePad, p);
                canvas.drawText(arrayData[i][j], currC + squarePad, currR + squareSize - squarePad * 2, p2);
            }
        }
        
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
