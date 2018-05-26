package arcAppManager;

import android.app.Activity;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.util.Random;

import appmanager.arcadio.com.arcappmanager.R;

/**
 * Created by Devbd on 3/26/18.
 */

class ShowPromotAppDialog {
    ArcAppManagerAnalytic analytic;

    private ArcAppManager.onPromoterNotifyListener mPromoterNotifyListener = null;


    public ShowPromotAppDialog(Activity activity, String buttonName, ArcAppManager.onPromoterNotifyListener promoterNotifyListener) throws Exception {
        analytic = new ArcAppManagerAnalytic(activity.getApplicationContext());
        this.mPromoterNotifyListener = promoterNotifyListener;
        final int totalAds = ArcAppManager.getInstance().getApps().
                getResponse().get(0).getPromotedAppsInfo().size();
        if (totalAds == 0) {
            ArcLog.w("No promoted ads.");
            return;
        }
        for (int i = 0; i < 10; i++) {
            int min = 0;
            int max = totalAds - 1;

            Random r = new Random();
            int selectedIndex = r.nextInt(max - min + 1) + min;
            final PromotedAppsInfo promotedAppsInfo = ArcAppManager.getInstance().getApps().
                    getResponse().get(0).getPromotedAppsInfo().get(selectedIndex);
            if (!isPackageInstalled(activity, promotedAppsInfo.pkgName)) {
                loadAppIcon(promotedAppsInfo, activity, buttonName);
                if (mPromoterNotifyListener != null) {
                    mPromoterNotifyListener.appShowed(promotedAppsInfo.pkgName);
                }
                return;
            }
        }
    }

    private String getApplicationName(Context context) {
        return context.getApplicationInfo().loadLabel(context.getPackageManager()).toString();
    }

    private void loadAppIcon(final PromotedAppsInfo promotedAppsInfo, final Activity activity, String buttonName) throws Exception {
        if (analytic != null) {
            analytic.pushToAnalytic(activity.getPackageName(), "Promoted", promotedAppsInfo.pkgName);
            ArcLog.w(activity.getPackageName());
        }
        final Dialog dialog = new Dialog(activity);
        dialog.setContentView(R.layout.promot_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setCanceledOnTouchOutside(false);
        final ImageView appIcon = dialog.findViewById(R.id.dlg_app_icon);
        final ImageView banner = dialog.findViewById(R.id.dlg_app_banner);
        final TextView title = dialog.findViewById(R.id.dlg_app_title);
        final TextView description = dialog.findViewById(R.id.dlg_app_info);
        final TextView promoter = dialog.findViewById(R.id.dlg_app_owner);
        promoter.setText("Promoted By: " + getApplicationName(activity));
        final Button download = dialog.findViewById(R.id.dlg_app_download);
        if (buttonName.length() > 0)
            download.setText(buttonName);
        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                if (mPromoterNotifyListener != null) {
                    mPromoterNotifyListener.appCancelled();
                }
            }
        });
        dialog.findViewById(R.id.dlg_close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mPromoterNotifyListener != null) {
                    mPromoterNotifyListener.appCancelled();
                }
                dialog.dismiss();
            }
        });
        dialog.findViewById(R.id.dlg_app_site).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_VIEW,
                        Uri.parse("http://www.arcadiosys.com/apps/detail/" + promotedAppsInfo.pkgName));
                activity.startActivity(i);
            }
        });
        download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (analytic != null) {
                    analytic.pushToAnalytic(activity.getPackageName(), "Install", promotedAppsInfo.pkgName);
                }
                goToPlaystore(activity, promotedAppsInfo.pkgName);
                dialog.dismiss();
            }
        });
        title.setText(promotedAppsInfo.appName);
        description.setText(promotedAppsInfo.shortDescription);
        Glide.with(activity).load(promotedAppsInfo.appIcon).diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        return false;
                    }
                })
                .into(appIcon);
        Glide.with(activity).load(promotedAppsInfo.appBanner).diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        return false;
                    }
                })
                .into(banner);

        dialog.show();
    }

    private void goToPlaystore(Context context, String pkgName) {
        if (mPromoterNotifyListener != null) {
            mPromoterNotifyListener.appInstallClicked(true);
        }
        try {
            context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(pkgName)));
        } catch (ActivityNotFoundException e1) {
            try {
                context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(("http://play.google.com/store/apps/details?id=" + pkgName))));
            } catch (Exception e2) {

            }
        }
    }

    private boolean isPackageInstalled(Context context, String packagename) {
        PackageManager packageManager = context.getPackageManager();
        try {
            packageManager.getPackageInfo(packagename, 0);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }
}
