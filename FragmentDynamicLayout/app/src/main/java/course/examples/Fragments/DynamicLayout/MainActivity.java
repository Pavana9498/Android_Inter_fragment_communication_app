package course.examples.Fragments.DynamicLayout;

import android.app.ActionBar;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends Activity {

    private static final String PERMISSION = "edu.uic.cs478.sp2020.project3";
    final int REQUEST_CODE = 1;

    BroadcastReceiver mReceiver = new MyReceiver();
    IntentFilter mFilter = new IntentFilter(PERMISSION);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i("Click"," create before");
        super.onCreate(savedInstanceState);
        Log.i("Click"," create before");


        ActionBar actionBar=getActionBar();
        actionBar.setTitle("Application 2");
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.rgb(48,63,159)));

        Log.i("Click","before");

        setContentView(R.layout.activity_main);
        Log.i("Click create","created");

        registerReceiver(mReceiver, mFilter);
        Log.i("Click","registered");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.res:
                Log.i("Click res in app","entered switch");
                Intent i = new Intent(this, QuoteViewerActivity.class);
                startActivity(i.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT));
                return true;
            case R.id.att:
                Log.i("Click act in app","entered switch");
                Intent i1 = new Intent(this, AttractionViewerActivity.class);
                startActivity(i1.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT));
                return true;
            default:
                return super.onOptionsItemSelected(menuItem);
        }
    }

    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mReceiver);
    }

}
