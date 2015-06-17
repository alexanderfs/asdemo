package demo.alexan.com.myapplication;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.GridView;

import demo.alexan.com.trend.Trend15x5Activity;
import demo.alexan.com.trend.TrendDltActivity;
import demo.alexan.com.trend.TrendK3Activity;
import demo.alexan.com.trend.TrendQlcActivity;
import demo.alexan.com.trend.TrendSsqActivity;


public class TrendTestActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trendchart);
        
        findViewById(R.id.trend_ssq).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(TrendTestActivity.this, TrendSsqActivity.class));
            }
        });
        findViewById(R.id.trend_dlt).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(TrendTestActivity.this, TrendDltActivity.class));
            }
        });
        findViewById(R.id.trend_15x5).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(TrendTestActivity.this, Trend15x5Activity.class));
            }
        });
        findViewById(R.id.trend_qlc).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(TrendTestActivity.this, TrendQlcActivity.class));
            }
        });
        findViewById(R.id.trend_k3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(TrendTestActivity.this, TrendK3Activity.class));
            }
        });
    }
}
