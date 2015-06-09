package demo.alexan.com.myapplication;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by Alex on 2015/4/28.
 */
public class ScrollActivity extends Activity implements View.OnClickListener{
    private TextView tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scroll);
        tv = (TextView) findViewById(R.id.scroll_text);
        int sizex = tv.getWidth();
        int sizey = tv.getHeight();
        System.out.println("" + sizex + sizey);
        Button b = (Button) findViewById(R.id.scroll_left);
        b.setOnClickListener(this);
        b = (Button) findViewById(R.id.scroll_right);
        b.setOnClickListener(this);
        b = (Button) findViewById(R.id.scroll_up);
        b.setOnClickListener(this);
        b = (Button) findViewById(R.id.scroll_down);
        b.setOnClickListener(this);


        DisplayMetrics dMtr = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dMtr);
        tv.setText(dMtr.toString() + ", densityDpi: " + dMtr.densityDpi);
    }
    
    private int preL = 0;
    private int preT = 0;
    private int preR = 0;
    private int preB = 0;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.scroll_left:
                /*这里好像有点知道他们的区别了。首先是坐标，使用scrollto方法会将当前的view中参数指定的坐标移到原点。
                使用layout方法会将控件的原点移动到其父view的，由参数指定的位置处。
                这两个函数我觉得可以对应于gravity和layout_gravity这两个布局参数。*/
                //tv.scrollBy(20, 0);;
                preL += 20;
                preR = (preL + tv.getWidth());
                preB = (preT + tv.getHeight());
                tv.layout(preL, preT, preR, preB);
                break;
            case R.id.scroll_right:
                //tv.scrollBy(-20, 0);\
                preL -= 20;
                preR = (preL + tv.getWidth());
                preB = (preT + tv.getHeight());
                tv.layout(preL, preT, preR, preB);
                break;
            case R.id.scroll_up:
                tv.scrollBy(0, 20);
                break;
            case R.id.scroll_down:
                preT -= 20;
                tv.scrollTo(preL, preT);
                //tv.scrollBy(0, -20);
                break;
        }
    }
}
