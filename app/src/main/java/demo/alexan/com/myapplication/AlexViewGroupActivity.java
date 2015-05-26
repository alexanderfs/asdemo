package demo.alexan.com.myapplication;

import android.app.Activity;
import android.os.Bundle;

import demo.alexan.com.view.AlexViewGroup;

/**
 * Created by Alex on 2015/4/27.
 */
public class AlexViewGroupActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AlexViewGroup avg = new AlexViewGroup(this);
        setContentView(avg);
    }
}
