package demo.alexan.com.myapplication;

import android.app.Activity;
import android.os.Bundle;

import demo.alexan.com.utils.CascadeHoriScrollListener;
import demo.alexan.com.utils.CascadeScrollListener;
import demo.alexan.com.view.CascadeHorizontalView;
import demo.alexan.com.view.CascadeScrollView;

/**
 * Created by Alex on 2015/6/8.
 */
public class ExtendCascadeScrollActivity extends Activity implements CascadeScrollListener, CascadeHoriScrollListener{
    
    private CascadeScrollView csv1;
    private CascadeScrollView csv2;
    
    private CascadeHorizontalView chv1;
    private CascadeHorizontalView chv2;
    
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bidirection_scroll_layout);
        csv1 = (CascadeScrollView) findViewById(R.id.side_banner);
        csv1.setScrollListener(this);
        csv2 = (CascadeScrollView) findViewById(R.id.main_vertical);
        csv2.setScrollListener(this);
        
        chv1 = (CascadeHorizontalView) findViewById(R.id.top_banner);
        chv1.setScrollListener(this);
        chv2 = (CascadeHorizontalView) findViewById(R.id.main_horizontal);
        chv2.setScrollListener(this);
    }

    @Override
    public void onCascadeScrollChanged(CascadeScrollView scrollView, int x, int y, int oldX, int oldY) {
        if(scrollView == csv1 && csv2 != null) {
            csv2.smoothScrollTo(x, y);
        } else if(scrollView == csv2 && csv1 != null) {
            csv1.smoothScrollTo(x, y);
        }
    }

    @Override
    public void onCascadeScrollChanged(CascadeHorizontalView scrollView, int x, int y, int oldX, int oldY) {
        if(scrollView == chv1 && chv2 != null) {
            chv2.smoothScrollTo(x, y);
        } else if(scrollView == chv2 && chv1 != null) {
            chv1.smoothScrollTo(x, y);
        }
    }
}
