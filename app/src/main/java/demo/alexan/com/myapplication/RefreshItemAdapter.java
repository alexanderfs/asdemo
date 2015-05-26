package demo.alexan.com.myapplication;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alex on 2015/5/8.
 */
public class RefreshItemAdapter extends BaseAdapter {
    
    private List<RefreshItemActivity.ItemData> mDataList;
    private Context mCtx;
    private ListView mListView;
    
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            updateItem(msg.arg1);
        }
    };

    public RefreshItemAdapter(List<RefreshItemActivity.ItemData> mDataList, Context mCtx, ListView lv) {
        this.mDataList = mDataList;
        this.mCtx = mCtx;
        this.mListView = lv;
    }
    
    public void updateItemData(RefreshItemActivity.ItemData item) {
        Message msg = Message.obtain();
        int idx = -1;
        for(int i = 0; i < mDataList.size(); i++) {
            if(mDataList.get(i).getDataId() == item.getDataId()) {
                idx = i;
            }
        }
        msg.arg1 = idx;
        mDataList.set(idx, item);
        //mHandler.sendMessage(msg);
        updateItem(idx);
    }
    
    private void updateItem(int index) {
        if(mListView == null) {
            return;
        }
        int visiblePos = mListView.getFirstVisiblePosition();
        View view = mListView.getChildAt(index - visiblePos);
        ((TextView)view.findViewById(android.R.id.text1)).setText(((RefreshItemActivity.ItemData)getItem(index)).getData());
        
    }

    @Override
    public int getCount() {
        return mDataList.size();
    }

    @Override
    public Object getItem(int position) {
        return mDataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null) {
            convertView = LayoutInflater.from(mCtx).inflate(android.R.layout.simple_list_item_1, null);
        }
        ((TextView)convertView.findViewById(android.R.id.text1)).setText(mDataList.get(position).getData());
        return convertView;
    }
}
