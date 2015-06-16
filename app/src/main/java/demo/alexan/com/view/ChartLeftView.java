package demo.alexan.com.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Scroller;

/**
 * Created by Alex on 2015/6/10.
 */
public class ChartLeftView extends View {
    
    private Paint p;
    private Paint p2;
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
            return (int)dm.density * 80;
        } else {
            return (int)dm.density * 30 * (lineNumber + 4);
        }
    }
    
    @Override
    protected void onDraw(Canvas canvas) {
        int width = getMeasuredWidth();
        int height = getMeasuredHeight() / arrayData.length;
        //int squareCount = arrayData.length;
        int squarePad = 5;
        int currY = -height;
        for(int i = 0; i < arrayData.length; i++) {
            currY += height;
            canvas.drawRect(squarePad, currY + squarePad, width - squarePad, currY + height - squarePad, p);
            canvas.drawText(arrayData[i], squarePad * 2, currY + height - squarePad * 2, p2);
        }
        super.onDraw(canvas);
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
