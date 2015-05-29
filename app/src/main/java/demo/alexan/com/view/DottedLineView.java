package demo.alexan.com.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.PathEffect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import demo.alexan.com.myapplication.R;


/**
 * Created by Alex on 2015/5/29.
 */
public class DottedLineView extends View {
    
    private Paint p;
    private int lineColor;
    
    private void init(Context ctx, AttributeSet attrs) {
        if(attrs != null) {
            int count = attrs.getAttributeCount();
            /*for(int i = 0; i < count; i++) {
                Log.d("dotted", attrs.getAttributeName(i) + ": " + attrs.getAttributeValue(i));
            }*/
            TypedArray ta = ctx.obtainStyledAttributes(attrs, new int[]{R.attr.my_color});
            lineColor = ta.getColor(0, 0x000000);
            ta.recycle();
        }
        p = new Paint();
        p.setAlpha(255);
        p.setStrokeWidth(2);
        p.setColor(lineColor);
        p.setStyle(Paint.Style.FILL_AND_STROKE);
        p.setPathEffect(new DashPathEffect(new float[]{12, 4}, 50));
        
    }
    
    public DottedLineView(Context context) {
        super(context);
        init(context, null);
    }

    public DottedLineView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public DottedLineView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        /*Paint p = new Paint(Paint.ANTI_ALIAS_FLAG);
        p.setStrokeWidth(1);
        p.setColor(Color.BLACK);
        p.setStyle(Paint.Style.STROKE);
        PathEffect pe = new DashPathEffect(new float[]{1, 2, 4, 8}, 1);
        p.setPathEffect(pe);*/
        canvas.drawLine(0, 0, getWidth(), 0, p);
    }
}
