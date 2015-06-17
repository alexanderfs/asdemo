package demo.alexan.com.myapplication;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button b = (Button) findViewById(R.id.btn_test_pinnedlist);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, PinnedSectionListActivity.class);
                startActivity(i);
            }
        });
        
        b = (Button) findViewById(R.id.btn_test_customviewgroup);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, AlexViewGroupActivity.class);
                startActivity(i);
            }
        });
        
        b = (Button) findViewById(R.id.btn_test_slide_listitem);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, SlideCutActivity.class));
            }
        });
        
        b = (Button) findViewById((R.id.btn_test_scroll));
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ScrollActivity.class));
            }
        });

        b = (Button) findViewById((R.id.btn_test_slide_menu));
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, SlideMenuActivity.class));
            }
        });

        b = (Button) findViewById((R.id.btn_test_refresh_item));
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, RefreshItemActivity.class));
            }
        });
        
        b = (Button) findViewById(R.id.btn_dynamic_ball_list);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, DynamicListActivity.class));
            }
        });
        
        ((Button)findViewById(R.id.btn_cascade_scroll)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, CascadeScrollActivity.class));
            }
        });
        
        findViewById(R.id.btn_extend_cascade_scroll).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ExtendCascadeScrollActivity.class));
            }
        });
        
        findViewById(R.id.btn_hv_slide).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, HvSlideActivity.class));
            }
        });

        findViewById(R.id.btn_fusion_slide).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, FusionScrollActivity.class));
                overridePendingTransition(R.anim.slide_bottom_in, R.anim.slide_bottom_out);
            }
        });

        findViewById(R.id.btn_test_trendchart).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, TrendTestActivity.class));
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
