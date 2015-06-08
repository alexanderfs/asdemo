package demo.alexan.com.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.HorizontalScrollView;

import demo.alexan.com.utils.CascadeHoriScrollListener;
import demo.alexan.com.utils.CascadeScrollListener;

/**
 * Created by Alex on 2015/6/8.
 */
public class CascadeHorizontalView extends HorizontalScrollView {

    private CascadeHoriScrollListener scrollListener = null;
    
    public CascadeHorizontalView(Context context) {
        super(context);
    }

    public CascadeHorizontalView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setScrollListener(CascadeHoriScrollListener scrollListener) {
        this.scrollListener = scrollListener;
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if(scrollListener != null) {
            scrollListener.onCascadeScrollChanged(this, l, t, oldl, oldt);
        }
    }
}
