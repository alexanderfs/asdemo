package demo.alexan.com.myapplication;

import android.app.Activity;
import android.os.Bundle;

import demo.alexan.com.utils.CascadeScrollListener;
import demo.alexan.com.view.CascadeScrollView;

/**
 * Created by Alex on 2015/6/8.
 */
public class CascadeScrollActivity extends Activity implements CascadeScrollListener{
    
    private CascadeScrollView csv1;
    private CascadeScrollView csv2;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cascade_scroll_layout);
        csv1 = (CascadeScrollView) findViewById(R.id.scrollview1);
        csv1.setScrollListener(this);
        csv2 = (CascadeScrollView) findViewById(R.id.scrollview2);
        csv2.setScrollListener(this);
    }

    @Override
    public void onCascadeScrollChanged(CascadeScrollView scrollView, int x, int y, int oldX, int oldY) {
        if(scrollView == csv1 && csv2 != null) {
            csv2.smoothScrollTo(x, y);
        } else if(scrollView == csv2 && csv1 != null) {
            csv1.smoothScrollTo(x, y);
        }
    }
}
