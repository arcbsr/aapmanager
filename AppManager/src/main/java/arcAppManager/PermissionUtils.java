package arcAppManager;

import android.app.Activity;
import android.os.Build;
import android.support.design.widget.Snackbar;
import android.view.View;

import permissions.Nammu;
import permissions.PermissionCallback;

public class PermissionUtils {

    public PermissionUtils(Activity activity) {
        Nammu.init(activity);
    }

    private static final String ALL_PERMISSION_GRANTED = "success";

    public void AskForPermission(final Activity activity, final View view
            , String permission, final String msg, final String button, final PermissionCallback permissionCallback) {
        //check for permission

        if (Nammu.shouldShowRequestPermissionRationale(activity, permission)) {
            permissionSnackBar(activity, view, msg, button, permissionCallback, new String[]{permission});
        } else {
            Nammu.askForPermission(activity, new String[]{permission}, permissionCallback);
        }
    }

    public boolean isPermissionGranted(String permission) {
        return ALL_PERMISSION_GRANTED.equals(permission);

    }

    public String checkAllPermissionsIfNot(final Activity activity, final String[] permissions) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return ALL_PERMISSION_GRANTED;
        }
        for (String permission : permissions) {
            if (!Nammu.checkPermission(permission)) {
                return permission;
            }
        }
        return ALL_PERMISSION_GRANTED;
    }

    private void permissionSnackBar(final Activity context, final View view, final String msg, final String button,
                                    final PermissionCallback permissionCallback, final String[] permissions) {
        Snackbar.make(view, msg,
                Snackbar.LENGTH_INDEFINITE)
                .setAction(button, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Nammu.askForPermission(context, permissions, permissionCallback);
                    }
                }).show();
    }
    //Helper
//    final PermissionUtils permissionUtils = new PermissionUtils(MainActivity.this);
//                if (!permissionUtils.isPermissionGranted(permissionUtils.checkAllPermissionsIfNot(
//    MainActivity.this, new String[]{
//        Manifest.permission.WRITE_EXTERNAL_STORAGE}))) {
//        permissionUtils.AskForPermission(MainActivity.this, findViewById(R.id.button),
//                Manifest.permission.WRITE_EXTERNAL_STORAGE,
//                "Need Permission", "Allow", new PermissionCallback() {
//                    @Override
//                    public void permissionGranted() {
//                        Toast.makeText(MainActivity.this, "Permission Done", Toast.LENGTH_SHORT).show();
//                    }
//
//                    @Override
//                    public void permissionRefused() {
//                        Toast.makeText(MainActivity.this, "Permission Refused", Toast.LENGTH_SHORT).show();
//
//                    }
//                });
//    }

//    @Override
//    public void onRequestPermissionsResult(
//            int requestCode, String[] permissions, int[] grantResults) {
//        Nammu.onRequestPermissionsResult(requestCode, permissions, grantResults);
//    }
    //end
}
