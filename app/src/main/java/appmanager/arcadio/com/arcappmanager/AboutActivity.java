package appmanager.arcadio.com.arcappmanager;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.hanks.htextview.typer.TyperTextView;

import arcAppManager.ArcAppManager;
import arcAppManager.HttpSyncAppManager;


public class AboutActivity extends Activity {
    int refreshIntervalHour = 6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_about);
        setTitle("");
//        ((TyperTextView) findViewById(R.id.version_des)).setText("Connecting....");
//        TyperTextView typerTextView = findViewById(R.id.version_des);
//        typerTextView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                ArcAppManager.getInstance().initiateSplash(1, AboutActivity.this, 1000);
//            }
//        });
        refreshIntervalHour = getIntent().getIntExtra("refreshIntervalHour", 6);
        ArcAppManager.getInstance().initiate(this, new HttpSyncAppManager.onHttpSyncNotifyListener() {
            @Override
            public void onPreConnection() {

            }

            @Override
            public void onPostConnection(Object o, boolean b) {

                setResult(b ? RESULT_OK : RESULT_CANCELED);
                //finish();
            }

            @Override
            public void onDoInBackground(boolean s) {
            }
        }, refreshIntervalHour);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == android.R.id.home) {
            // finish the activity
            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
