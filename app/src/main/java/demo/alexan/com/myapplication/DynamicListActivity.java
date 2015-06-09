package demo.alexan.com.myapplication;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.text.Layout;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ActionMenuView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Alex on 2015/5/26.
 */
public class DynamicListActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ListView lv = new ListView(this);
        LinearLayout layout = new LinearLayout(this);
        LinearLayout.LayoutParams lp = 
                new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        layout.setLayoutParams(lp);
        initListData();
        MyAdapter ma = new MyAdapter(this);
        lv.setAdapter(ma);
        layout.addView(lv);
        setContentView(layout);
        //init();
    }
    
    private void init() {
    }
    
    private List<DataItem> listData = new ArrayList<DataItem>();
    
    private void initListData() {
        Random r = new Random();
        for(int i = 0; i < 10; i++) {
            DataItem di = new DataItem();
            di.ballCount = r.nextInt(7) + 1;
            listData.add(di);
        }
    }
    
    class MyAdapter extends BaseAdapter {
        private Context mCtx;
        private int maxBallSize;
        private int llWidth;
        
        MyAdapter(Context ctx) {
            this.mCtx = ctx;
            init();
        }
        
        private void init() {
            DisplayMetrics dm = mCtx.getResources().getDisplayMetrics();
            int scrrenWidth = dm.widthPixels;
            maxBallSize = (int)(30 * dm.density + 0.5);
            
            View measureView = LayoutInflater.from(mCtx).inflate(R.layout.award_list_item, null);
            int size = View.MeasureSpec.makeMeasureSpec(scrrenWidth, View.MeasureSpec.EXACTLY);
            measureView.measure(size, size);

            RelativeLayout ll = (RelativeLayout) measureView.findViewById(R.id.lottery_basic_info);
            llWidth = ll.getMeasuredWidth();
        }

        @Override
        public int getCount() {
            return listData.size();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder vh;
            if(convertView == null) {
                convertView = LayoutInflater.from(mCtx).inflate(R.layout.award_list_item, parent, false);
                vh = new ViewHolder();
                vh.iv = (ImageView)convertView.findViewById(R.id.lottery_icon);
                vh.layout = (LinearLayout)convertView.findViewById(R.id.award_lucky_num_stub);
                vh.content = (RelativeLayout)convertView.findViewById(R.id.lottery_basic_info);
                //margin不算在控件尺寸范围内，而padding算。所以这里布局都改成了用padding。
                //todo:但是还有个问题就是使用原有的尺寸会导致宽度不够。还不知道是什么问题。这里原来用的linearlayout，有这个问题
                //todo:换成relative后就又没问题了。android sdk真是神奇呀！
                //LinearLayout.LayoutParams ll = new LinearLayout.LayoutParams(llWidth, LinearLayout.LayoutParams.WRAP_CONTENT);
                //vh.content.setLayoutParams(ll);
                
                convertView.setTag(vh);
            } else {
                vh = (ViewHolder) convertView.getTag();
            }
            vh.layout.removeAllViews();
            int balls = listData.get(position).ballCount;
            int itemSize = llWidth / balls;
            if(itemSize > maxBallSize) {
                itemSize = maxBallSize;
            }
            Random rm = new Random();
            for(int i = 0; i < balls; i++) {
                ImageView itemIv = new ImageView(mCtx);
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(itemSize, itemSize);
                itemIv.setLayoutParams(lp);
                itemIv.setBackgroundColor(colorList[rm.nextInt(7)]);
                vh.layout.addView(itemIv);
            }
            return convertView;
        }
    }
    
    int[] colorList = new int[] {
            Color.BLACK, Color.BLUE, Color.CYAN, Color.DKGRAY, Color.GRAY, Color.GREEN, Color.MAGENTA
    };
    
    static class ViewHolder {
        ImageView iv;
        LinearLayout layout;
        RelativeLayout content;
    }
    
    class DataItem {
        public int ballCount;
    }
}
