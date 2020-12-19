package course.examples.Fragments.DynamicLayout;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class MyReceiver extends BroadcastReceiver {

    String PERMISSION = "edu.uic.cs478.sp2020.project3";

    @Override
    public void onReceive(Context context, Intent intent) {

        Log.i("Click", "Receiver "+intent);

        String result = intent.getAction();
        if(result != PERMISSION) {
            Log.i("Click Permissions","denied");
        }
        else {
            Log.i("Click Permissions", "accepted");

        String i = intent.getStringExtra("BUTTON");
        Log.i("Clickable", "Companion Receiver called into action! "+i);
        Intent myIntent= null;

        switch(i) {
            case "Restaurants":
                myIntent = new Intent(context, QuoteViewerActivity.class);
                Log.i("Click", "case 1 "+context);
                break;
            case "Attractions":
                myIntent = new Intent(context, AttractionViewerActivity.class);
                Log.i("Click", "case 2 "+context);
                break;
            default:
                Log.i("Click", "default here");

        }

        context.startActivity(myIntent);

        }
    }

}
