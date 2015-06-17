package demo.alexan.com.trend;

import android.app.Activity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import demo.alexan.com.myapplication.R;
import demo.alexan.com.view.AllInChartView;

public class TrendSsqActivity extends Activity {

    private RadioGroup mRg;
    private RadioButton redRb;
    private RadioButton blueRb;
    private AllInChartView mainView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_trend_ssq);
        mRg = (RadioGroup) findViewById(R.id.colorful_ball_radiogroup);
        redRb = (RadioButton) findViewById(R.id.redball_trend);
        blueRb = (RadioButton) findViewById(R.id.blueball_trend);
        mRg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.redball_trend) {
                    redRb.setTextColor(getResources().getColor(R.color.choosen_tab));
                    blueRb.setTextColor(getResources().getColor(R.color.unchoosen_tab));
                    mainView.changeBall(100, 35);
                } else if (checkedId == R.id.blueball_trend) {
                    redRb.setTextColor(getResources().getColor(R.color.unchoosen_tab));
                    blueRb.setTextColor(getResources().getColor(R.color.choosen_tab));
                    mainView.changeBall(100, 16);
                }
            }
        });
        mainView = (AllInChartView) findViewById(R.id.main_frame);
        super.onCreate(savedInstanceState);
    }
}
