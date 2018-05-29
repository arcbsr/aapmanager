package appmanager.arcadio.com.arcappmanager;

import android.Manifest;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import arcAppManager.ArcAppManager;
import arcAppManager.HttpSyncAppManager;
import arcAppManager.PermissionUtils;
import permissions.Nammu;
import permissions.PermissionCallback;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //startActivity(new Intent(this, AboutActivity.class));

        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final PermissionUtils permissionUtils = new PermissionUtils(MainActivity.this);
                if (!permissionUtils.isPermissionGranted(permissionUtils.checkAllPermissionsIfNot(
                        MainActivity.this, new String[]{
                                Manifest.permission.WRITE_EXTERNAL_STORAGE}))) {
                    permissionUtils.AskForPermission(MainActivity.this, findViewById(R.id.button),
                            Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            "Need Permission", "Allow", new PermissionCallback() {
                                @Override
                                public void permissionGranted() {
                                    Toast.makeText(MainActivity.this, "Permission Done", Toast.LENGTH_SHORT).show();
                                }

                                @Override
                                public void permissionRefused() {
                                    Toast.makeText(MainActivity.this, "Permission Refused", Toast.LENGTH_SHORT).show();

                                }
                            });
                }

            }
        });
        //ArcAppManager.getInstance().clearCache(this);
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
        }, 12);
    }

    @Override
    public void onRequestPermissionsResult(
            int requestCode, String[] permissions, int[] grantResults) {
        Nammu.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private void checkPermissionAndThenLoad() {
        //check for permission
        Nammu.init(this);
        if (Nammu.checkPermission(Manifest.permission.READ_EXTERNAL_STORAGE) &&
                Nammu.checkPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            Toast.makeText(MainActivity.this, "Permission Done", Toast.LENGTH_SHORT).show();
        } else {
            if (Nammu.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                permissionSnackBar();
            } else {
                Nammu.askForPermission(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE}, permissionReadstorageCallback);
            }
        }
    }

    private void permissionSnackBar() {
        Snackbar.make(findViewById(R.id.button), "Permission Needed",
                Snackbar.LENGTH_INDEFINITE)
                .setAction("Allow", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Nammu.askForPermission(MainActivity.this,
                                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
                                        Manifest.permission.WRITE_EXTERNAL_STORAGE}, permissionReadstorageCallback);
                    }
                }).show();
    }

    private final PermissionCallback permissionReadstorageCallback = new PermissionCallback() {
        @Override
        public void permissionGranted() {
            Toast.makeText(MainActivity.this, "Permission Done", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void permissionRefused() {
            Toast.makeText(MainActivity.this, "Permission Refeused", Toast.LENGTH_SHORT).show();
            permissionSnackBar();
        }
    };
}
