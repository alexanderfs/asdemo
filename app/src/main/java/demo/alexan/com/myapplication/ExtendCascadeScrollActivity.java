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
public class ExtendCascadeScrollActivity extends Activity implements CascadeScrollListener{
    
    private CascadeScrollView csv1;
    private CascadeScrollView csv2;
    private CascadeScrollView csv3;
    
    /*private CascadeHorizontalView chv1;
    private CascadeHorizontalView chv2;*/
    
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bidirection_scroll_layout);
        csv1 = (CascadeScrollView) findViewById(R.id.top_banner);
        csv1.setScrollListener(this);
        csv2 = (CascadeScrollView) findViewById(R.id.side_banner);
        csv2.setScrollListener(this);
        csv3 = (CascadeScrollView) findViewById(R.id.main_horizontal);
        csv3.setScrollListener(this);
        //setContentView(R.layout.hv_scrollview_test);
    }

    @Override
    public void onCascadeScrollChanged(CascadeScrollView scrollView, int x, int y, int oldX, int oldY) {
        if(scrollView == csv1) {
            if(csv2 != null) {
                csv2.smoothScrollTo(x, y);    
            }
            if(csv3 != null) {
                csv3.smoothScrollTo(x, y);
            }
        } else if(scrollView == csv2) {
            if(csv1 != null) {
                csv1.smoothScrollTo(x, y);
            }
            if(csv3 != null) {
                csv3.smoothScrollTo(x, y);
            }
        } else if(scrollView == csv3) {
            if(csv1 != null) {
                csv1.smoothScrollTo(x, y);
            }
            if(csv2 != null) {
                csv2.smoothScrollTo(x, y);
            }
        }
    }

    /*@Override
    public void onCascadeScrollChanged(CascadeHorizontalView scrollView, int x, int y, int oldX, int oldY) {
        if(scrollView == chv1 && chv2 != null) {
            chv2.smoothScrollTo(x, y);
        } else if(scrollView == chv2 && chv1 != null) {
            chv1.smoothScrollTo(x, y);
        }
    }*/
}
