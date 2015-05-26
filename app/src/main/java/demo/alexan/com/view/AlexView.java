package demo.alexan.com.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Alex on 2015/4/27.
 */
public class AlexView extends View {
    private Paint paint = new Paint();
    
    public AlexView(Context context) {
        super(context);
    }

    public AlexView(Context context, AttributeSet attrs, Paint paint) {
        super(context, attrs);
        this.paint = paint;
    }
    
    protected void onMesure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(50, 50);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        paint.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
        paint.setColor(Color.RED);
        
        canvas.drawColor(Color.BLUE);
        canvas.drawRect(0, 0, 30, 30, paint);
        canvas.drawText("myview", 10, 40, paint);
    }
}
