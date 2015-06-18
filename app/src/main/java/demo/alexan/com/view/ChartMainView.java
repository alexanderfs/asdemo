package demo.alexan.com.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Scroller;

import java.util.Random;

import demo.alexan.com.myapplication.R;

/**
 * Created by Alex on 2015/6/10.
 */
public class ChartMainView extends View {
    
    private Paint p1;
    private Paint p2;
    private Paint pCircle;
    private Paint pw1;
    private Paint pw2;
    private Paint pLine;
    private Scroller mScroller;
    private DisplayMetrics dm;
    private String[][] arrayData;
    private int[] awardIdx;
    
    private int lineNumber = 100;
    private int columnNumber = 35;

    public void setChartDimension(int lineNumber, int columnNumber) {
        this.lineNumber = lineNumber;
        this.columnNumber = columnNumber;
        generateData();
    }

    private void generateData() {
        Random r = new Random();
        arrayData = new String[4 + lineNumber][columnNumber];
        for(int i = 0; i < (4 + lineNumber); i++) {
            for(int j = 0; j < columnNumber; j++) {
                arrayData[i][j] = "" + r.nextInt(columnNumber);
            }
        }
        awardIdx = new int[4 + lineNumber];
        for(int i = 0; i < (4 + lineNumber); i++) {
            awardIdx[i] = r.nextInt(columnNumber);
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
        
        p1 = new Paint(Paint.ANTI_ALIAS_FLAG);
        p1.setColor(ctx.getResources().getColor(R.color.line_color_shallow));
        p2 = new Paint(Paint.ANTI_ALIAS_FLAG);
        p2.setColor(ctx.getResources().getColor(R.color.line_color_dark));
        
        pw1 = new Paint(Paint.ANTI_ALIAS_FLAG);
        pw1.setColor(ctx.getResources().getColor(R.color.word_color2));
        pw1.setTextSize(40);

        pw2 = new Paint(Paint.ANTI_ALIAS_FLAG);
        pw2.setColor(ctx.getResources().getColor(R.color.word_color_ball_white));
        pw2.setTextSize(50);
        
        pCircle = new Paint(Paint.ANTI_ALIAS_FLAG);
        pCircle.setColor(ctx.getResources().getColor(R.color.bg_color_red));
        
        pLine = new Paint(Paint.ANTI_ALIAS_FLAG);
        pLine.setColor(ctx.getResources().getColor(R.color.line_color));
    }
    
    public void setScroller(Scroller scroller) {
        this.mScroller = scroller;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int measuredWidth = getMeasureParam(widthMeasureSpec, 0);
        int measuredHeight = getMeasureParam(heightMeasureSpec, 1);
        setMeasuredDimension(measuredWidth, measuredHeight);
    }
    
    private int getMeasureParam(int measureSpec, int type) {
        if(type == 0) {
            return (int)dm.density * 30 * columnNumber;
        } else {
            return (int)dm.density * 30 * (lineNumber + 4);
        }
    }
    
    public void setMyScrollX(int x) {
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
    
    
    @Override
    protected void onDraw(Canvas canvas) {
        int squareSize = 30 * (int)dm.density;
        int startx = getScrollX() / squareSize;
        int endx = startx + columnCount > columnNumber ? columnNumber : startx + columnCount;
        int startC = startx * squareSize;

        //Log.d("ondraw", "" + getMeasuredHeight() + ", " + getScrollY());
        int starty = getScrollY() / squareSize;
        int endy = starty + lineCount > (lineNumber + 4) ? (lineNumber + 4) : starty + lineCount;
        int startR = starty * squareSize;
        
        
        int currR = startR - squareSize;
        int currC;
        for(int i = starty; i < endy; i++) {
            currR += squareSize;
            if(i == lineNumber) {
                pLine.setColor(getContext().getResources().getColor(R.color.line_color_brown));
                canvas.drawLine(getScrollX(), currR, getScrollX() + frameWidth, currR, pLine);
                pLine.setColor(getContext().getResources().getColor(R.color.line_color));
                
            }
            
            if(i % 2 == 0) {
                canvas.drawRect(getScrollX(), currR, getScrollX() + frameWidth, currR + squareSize, p1);
            } else {
                canvas.drawRect(getScrollX(), currR, getScrollX() + frameWidth, currR + squareSize, p2);
            }
            currC = startC - squareSize;
            for(int j = startx; j < endx; j++) {
                currC += squareSize;
                canvas.drawLine(currC + squareSize, currR, currC + squareSize, currR + squareSize, pLine);
                if(i < lineNumber && awardIdx[i] == j) {
                    canvas.drawCircle(currC + squareSize / 2, currR + squareSize / 2, (squareSize - 3 * (int)dm.density) / 2, pCircle);
                    calculateBase("" + j, squareSize, pw2);
                    canvas.drawText("" + j, currC + xbase, currR + ybase, pw2);
                } else {
                    if(i >= lineNumber && i % 2 == 0) {
                        pw1.setColor(getContext().getResources().getColor(R.color.word_color4));
                        calculateBase(arrayData[i][j], squareSize, pw1);
                        canvas.drawText(arrayData[i][j], currC + xbase, currR + ybase, pw1);
                    } else if(i >= lineNumber && i % 2 == 1) {
                        pw1.setColor(getContext().getResources().getColor(R.color.word_color3));
                        calculateBase(arrayData[i][j], squareSize, pw1);
                        canvas.drawText(arrayData[i][j], currC + xbase, currR + ybase, pw1);    
                    }
                    
                }
            }
        }
        
        
        
        super.onDraw(canvas);
    }

    private int xbase;
    private int ybase;

    private void calculateBase(String txt, int size, Paint pw) {
        Paint.FontMetrics fm = pw.getFontMetrics();
        float wordWidth = pw.measureText(txt);
        xbase = (size - (int)wordWidth) / 2;
        ybase = (int)(size / 2 - fm.descent + (fm.bottom - fm.top) / 2);
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
