package demo.alexan.com.view;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import demo.alexan.com.myapplication.R;

/**
 * Created by Alex on 2015/4/27.
 */
public class AlexViewGroup extends ViewGroup {
    
    private static String TAG = "MyViewGroup";
    private Context mContext;
    
    public AlexViewGroup(Context context) {
        super(context);
        mContext = context;
        init();
    }

    public AlexViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        init();
    }
    
    private void init() {
        Button btn = new Button(mContext);
        btn.setText("i am a button");
        this.addView(btn);

        ImageView iv = new ImageView(mContext);
        iv.setBackgroundResource(R.mipmap.ic_launcher);
        this.addView(iv);

        AlexView alv = new AlexView(mContext);
        this.addView(alv);
        
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int childCount = getChildCount();
        int specsizeWidth = MeasureSpec.getSize(widthMeasureSpec);
        int specsizeHeight = MeasureSpec.getSize(heightMeasureSpec);
        setMeasuredDimension(specsizeWidth, specsizeHeight);
        for(int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            child.measure(50, 50);
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int childCount = getChildCount();
        int startTop = 10;
        int startLeft = 0;
        for(int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            child.layout(startLeft, startTop, startLeft + child.getMeasuredWidth(), startTop + child.getMeasuredHeight());
            startLeft += (child.getMeasuredWidth() + 10);
        }
    }
    
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
    }
    
    protected boolean drawChild(Canvas canvas, View child, long drawingTime) {
        return super.drawChild(canvas, child, drawingTime);
    }
}
