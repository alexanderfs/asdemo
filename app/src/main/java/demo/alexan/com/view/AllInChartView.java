package demo.alexan.com.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import demo.alexan.com.myapplication.R;

/**
 * Created by Alex on 2015/6/12.
 */
public class AllInChartView extends LinearLayout {
    
    

    public AllInChartView(Context context) {
        super(context);
    }

    public AllInChartView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    
    private void init() {
        LayoutInflater li = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        li.inflate(R.layout.hv_slide_chart, this, true);
    }
}
