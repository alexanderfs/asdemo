package demo.alexan.com.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Scroller;

import demo.alexan.com.myapplication.R;

/**
 * Created by Alex on 2015/6/10.
 */
public class ChartLeftView extends View {
    
    private Paint p;
    private Paint p2;
    private Paint pw;
    private Paint pl;
    private Scroller mScroller;
    DisplayMetrics dm;
    private ChartMainView mainView;
    private int lineNumber = 100;
    private String[] arrayData;
    
    private void generateArrayData() {
        arrayData = new String[4 + lineNumber];
        for(int i = 0; i < lineNumber; i++) {
            arrayData[i] = "第" + i + "期";
        }
        arrayData[lineNumber] = "出现次数";
        arrayData[lineNumber + 1] = "平均遗漏";
        arrayData[lineNumber + 2] = "最大遗漏";
        arrayData[lineNumber + 3] = "最大连出";
    }
    
    public void setDimension(int lineNumber) {
        this.lineNumber = lineNumber;
        generateArrayData();
    }
    
    public ChartLeftView(Context context) {
        super(context);
        init(context);
    }

    public ChartLeftView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ChartLeftView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }
    
    private void init(Context ctx) {
        generateArrayData();
        
        dm = ctx.getResources().getDisplayMetrics();
        p = new Paint(Paint.ANTI_ALIAS_FLAG);
        p.setColor(ctx.getResources().getColor(R.color.line_color_shallow));
        p2 = new Paint(Paint.ANTI_ALIAS_FLAG);
        p2.setColor(ctx.getResources().getColor(R.color.line_color_dark));
        pw = new Paint(Paint.ANTI_ALIAS_FLAG);
        pw.setColor(ctx.getResources().getColor(R.color.word_color2));
        pw.setTextSize(40);
        pl = new Paint(Paint.ANTI_ALIAS_FLAG);
        pl.setColor(ctx.getResources().getColor(R.color.line_color_brown));
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
        /*int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);*/
        
        if(type == 0) {
            return (int)dm.density * 55;
        } else {
            return (int)dm.density * 30 * (lineNumber + 4);
        }
    }
    
    @Override
    protected void onDraw(Canvas canvas) {
        int width = getMeasuredWidth();
        int height = getMeasuredHeight() / arrayData.length;
        int currY = -height;
        for(int i = 0; i < arrayData.length - 4; i++) {
            currY += height;
            if(i % 2 == 0) {
                canvas.drawRect(0, currY, width, currY + height, p);    
            } else {
                canvas.drawRect(0, currY, width, currY + height, p2);
            }
            calculateBase(arrayData[i], height);
            canvas.drawText(arrayData[i], xbase, currY + ybase, pw);
        }
        currY += height;
        canvas.drawLine(0, currY, width, currY, pl);
        
        for(int i = 0; i < 4; i++) {
            if(i % 2 == 0) {
                canvas.drawRect(0, currY, width, currY + height, p);
                pw.setColor(getContext().getResources().getColor(R.color.word_color3));
            } else {
                canvas.drawRect(0, currY, width, currY + height, p2);
                pw.setColor(getContext().getResources().getColor(R.color.word_color4));
            }
            calculateBase(arrayData[i + lineNumber], height);
            canvas.drawText(arrayData[i + lineNumber], xbase, currY + ybase, pw);
            currY += height;
        }
        pl.setColor(getContext().getResources().getColor(R.color.line_color));
        canvas.drawLine(width, 0, width, getMeasuredHeight(), pl);
        super.onDraw(canvas);
    }

    private int xbase;
    private int ybase;

    private void calculateBase(String txt, int height) {
        Paint.FontMetrics fm = pw.getFontMetrics();
        float wordWidth = pw.measureText(txt);
        xbase = (getMeasuredWidth() - (int)wordWidth) / 2;
        ybase = (int)(height / 2 - fm.descent + (fm.bottom - fm.top) / 2);
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if(mScroller != null && mScroller.computeScrollOffset()) {
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            if(mainView != null) {
                mainView.setMyScrollY(mScroller.getCurrY());
            }
            postInvalidate();
        }
    }
}
