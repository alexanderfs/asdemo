package demo.alexan.com.myapplication;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import demo.alexan.com.view.AllInChartView;


public class FusionScrollActivity extends ActionBarActivity {
    
    private RadioGroup mRg;
    private AllInChartView mainView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fusion_scroll);
        mRg = (RadioGroup) findViewById(R.id.colorful_ball_radiogroup);
        mRg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId == R.id.redball_trend) {
                    mainView.changeBall(100, 35);
                } else if(checkedId == R.id.blueball_trend) {
                    mainView.changeBall(100, 16);
                }
            }
        });
        mainView = (AllInChartView) findViewById(R.id.main_frame);
    }

}
