package demo.alexan.com.view;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import demo.alexan.com.myapplication.R;

/**
 * Created by Alex on 2015/4/22.
 */
public class PinnedBannerScrollView extends ScrollView {
    private Context mContext;
    private View mTopView;
    private View mTopBanner;
    public PinnedBannerScrollView(Context context) {
        super(context);
        
    }
    
    private void initView(Context context) {
        mContext = context;
        mTopView = ((Activity)mContext).findViewById(R.id.image_view);
        mTopBanner = ((Activity)mContext).findViewById(R.id.pinned_banner);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if(mTopView != null) {
            
        }
    }
}
