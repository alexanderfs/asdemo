package demo.alexan.com.utils;

import demo.alexan.com.view.CascadeHorizontalView;
import demo.alexan.com.view.CascadeScrollView;

/**
 * Created by Alex on 2015/6/8.
 */
public interface CascadeHoriScrollListener {
    public void onCascadeScrollChanged(CascadeHorizontalView scrollView, int x, int y, int oldX, int oldY);
}
