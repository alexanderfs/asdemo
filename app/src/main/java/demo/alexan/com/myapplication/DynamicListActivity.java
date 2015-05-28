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
        private int width;
        
        MyAdapter(Context ctx) {
            this.mCtx = ctx;
            init();
        }
        
        private void init() {
            DisplayMetrics dm = new DisplayMetrics();
            dm = mCtx.getResources().getDisplayMetrics();
            int scrrenWidth = dm.widthPixels;
            
            View measureView = LayoutInflater.from(mCtx).inflate(R.layout.award_list_item, null);
            int size = View.MeasureSpec.makeMeasureSpec(scrrenWidth, View.MeasureSpec.EXACTLY);
            measureView.measure(size, size);
            int totalWidth = measureView.getMeasuredWidth();
            
            RelativeLayout rl = (RelativeLayout) measureView.findViewById(R.id.award_basic_info);
            int rlWidth = rl.getMeasuredWidth();
            
            ImageView iv = (ImageView) measureView.findViewById(R.id.lottery_icon);
            int ivWidth = iv.getMeasuredWidth();
            
            ImageView iv2 = (ImageView) measureView.findViewById(R.id.right_arraw_img);
            int iv2Width = iv2.getMeasuredWidth();
            
            //LinearLayout ll = (LinearLayout) measureView.findViewById(R.id.award_lucky_num_stub);
            //ll.measure(View.MeasureSpec.AT_MOST, View.MeasureSpec.AT_MOST);
            width = measureView.getMeasuredWidth() - iv.getMeasuredHeight() - iv2.getMeasuredWidth();
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
                convertView.setTag(vh);
            } else {
                vh = (ViewHolder) convertView.getTag();
            }
            vh.layout.removeAllViews();
            int balls = listData.get(position).ballCount;
            int itemSize = width / balls;
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
    }
    
    class DataItem {
        public int ballCount;
    }
}
