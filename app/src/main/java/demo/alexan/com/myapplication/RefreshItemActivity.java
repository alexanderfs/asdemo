package demo.alexan.com.myapplication;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alex on 2015/5/8.
 */
public class RefreshItemActivity extends Activity {
    
    class ItemData {
        private int dataId;
        private String data;

        public int getDataId() {
            return dataId;
        }

        public void setDataId(int dataId) {
            this.dataId = dataId;
        }

        public String getData() {
            return data;
        }

        public void setData(String data) {
            this.data = data;
        }
    }
    
    private List<ItemData> mDataList;
    private ListView mLv;
    private RefreshItemAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
        setContentView(R.layout.refresh_item_activity);
        mLv = (ListView) findViewById(R.id.refresh_item_listview);
        mAdapter = new RefreshItemAdapter(mDataList, this, mLv);
        mLv.setAdapter(mAdapter);
        mLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ItemData item = (ItemData) parent.getItemAtPosition(position);
                item.setData("new data" + position);
                mAdapter.updateItemData(item);
            }
        });
    }
    
    private void init() {
        mDataList = new ArrayList<ItemData>();
        for(int i = 0; i < 20; i++) {
            ItemData idata = new ItemData();
            idata.setData("item" + i);
            idata.setDataId(i);
            mDataList.add(idata);
        }
    }
}
