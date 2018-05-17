package appmanager.arcadio.com.arcappmanager;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.util.HashMap;

import arcAppManager.ArcAppManager;
import arcAppManager.HttpSyncAppManager;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //startActivity(new Intent(this, AboutActivity.class));
        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Log.w("status", ArcAppManager.getInstance().showPromotedAds(MainActivity.this,
                            "Sucks", null));
                    Log.w("status all", ArcAppManager.getInstance().getStatus());

//                    Gson gson = new Gson();
//                    AppData appData = gson.fromJson(ArcAppManager.getInstance().getAppData(), AppData.class);
//                    Log.w("Version upd needed", appData.townhall.thsize + "");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        ArcAppManager.getInstance()
                .showLog(true);
        ArcAppManager.getInstance().setExtraParam(HttpSyncAppManager.ARC_APP_DETAIL + getApplicationContext().getPackageName());
        ArcAppManager.getInstance().initiate(this, new HttpSyncAppManager.onHttpSyncNotifyListener() {
            @Override
            public void onPreConnection() {

            }

            @Override
            public void onPostConnection(Object object, boolean isSuccess) {
                Toast.makeText(MainActivity.this, "update: " + isSuccess, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onDoInBackground(boolean isLoaded) {
                Log.w("is loaded", isLoaded + "");
            }
        }, 0);
    }
}
