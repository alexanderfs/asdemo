package demo.alexan.com.utils;

import demo.alexan.com.view.CascadeScrollView;

/**
 * Created by Alex on 2015/6/8.
 */
public interface CascadeScrollListener {
    public void onCascadeScrollChanged(CascadeScrollView scrollView, int x, int y, int oldX, int oldY);
}
