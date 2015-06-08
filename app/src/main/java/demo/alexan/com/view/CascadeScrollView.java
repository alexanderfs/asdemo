package demo.alexan.com.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

import demo.alexan.com.utils.CascadeScrollListener;

/**
 * Created by Alex on 2015/6/8.
 */
public class CascadeScrollView extends ScrollView {
    
    private CascadeScrollListener scrollListener = null;
    
    public CascadeScrollView(Context context) {
        super(context);
    }

    public CascadeScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CascadeScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setScrollListener(CascadeScrollListener scrollListener) {
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
