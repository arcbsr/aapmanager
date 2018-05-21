package arcAppManager;

import android.content.Context;
import android.os.AsyncTask;

import com.google.gson.Gson;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Created by Devbd on 3/26/18.
 */

public class HttpSyncAppManager extends AsyncTask<Void, Void, Object> {
    private onHttpSyncNotifyListener onHttpSyncNotify = null;
    protected final static String KEY_SAVED_DATA = "arc_app_manager_db";
    private final static String ARC_ROOT_URL = "http://79.143.190.131/arcappmanager/api";
    public static final String ARC_APP_DETAIL = "/app-details?pkg_name=";
    //"http://79.143.190.131/arcappmanager/api/app-details?pkg_name=";
    private boolean refreshData = true;//"http://79.143.190.131/arcappmanager/api/register-device";//
    private Context context = null;
    private static final String ARC_ERROR_CONNECTION = "Error in connection";
    private static final String MEDIA_TYPE = "application/json";
    private static final String MEDIA_TYPE_WWW = "application/x-www-form-urlencoded";

    public HttpSyncAppManager(onHttpSyncNotifyListener onHttpSyncNotify, boolean refreshData, Context context) {
        this.onHttpSyncNotify = onHttpSyncNotify;
        this.refreshData = refreshData;
        this.context = context;
        execute();
    }

    public interface onHttpSyncNotifyListener {
        void onPreConnection();

        void onPostConnection(Object object, boolean isSuccess);

        void onDoInBackground(boolean isLoaded);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        if (onHttpSyncNotify != null)
            onHttpSyncNotify.onPreConnection();
    }

    @Override
    protected void onPostExecute(Object apps) {
        super.onPostExecute(apps);
        if (apps == null) {
            if (onHttpSyncNotify != null)
                onHttpSyncNotify.onPostConnection(setDataForError(), false);
            return;
        }
        onHttpSyncNotify.onPostConnection(apps, true);
    }

    private class resultModule {
        String result = "";
        int responseCode = 200;
    }

    @Override
    protected Object doInBackground(Void... voids) {
        Apps response = null;
        resultModule result = new resultModule();
        try {
            if (refreshData) {
                result = getHttpResponse();
            } else {
                ArcLog.w("data load from local||server");
                result.result = ArcAppManagerdb.getStringValue(context, KEY_SAVED_DATA, "");
                if (result.result.length() == 0) {
                    result = getHttpResponse();
                }
            }
            ArcLog.w(result.result);
            Gson gson = new Gson();
            response = gson.fromJson(result.result, Apps.class);
            ArcAppManagerdb.setStringValue(context, KEY_SAVED_DATA, result.result);
            ArcAppManager.getInstance().setApps(response);
            if (response.getResponse().get(0).getStatus() == 1) {
            } else {
                ArcAppManager.getInstance().setMessage("Publisher disabled, please contact with admin.");
            }
            if (refreshData) {
                ArcAppManagerdb.setSetting(context, "arc_last_upd", System.currentTimeMillis());
                ArcLog.w("set interval for server");
            }
        } catch (Exception e) {
            ArcAppManager.getInstance().setMessage("Publisher disabled, please contact with admin.");
            ArcLog.w(e.getMessage());
            onHttpSyncNotify.onDoInBackground(false);
            return null;
        }
        if (result.responseCode != 200) {
            return null;

        }
        onHttpSyncNotify.onDoInBackground(true);
        return response;
    }

    private String setDataForError() {
        ArcAppManager.getInstance().setApps(null);
        ArcLog.w(ARC_ERROR_CONNECTION);
        return ARC_ERROR_CONNECTION;
    }

    private resultModule getHttpResponse() {
        ArcLog.w("data load from server");
        resultModule result = new resultModule();
        if (onHttpSyncNotify != null) {
            try {
                RequestBody formBody = null;
                FormBody.Builder formPBody = new FormBody.Builder();
                formPBody.add("package_name", context.getApplicationContext().getPackageName());
                if (ArcAppManager.getInstance().getmPostValues().size() > 0) {
                    for (String ss : ArcAppManager.getInstance().getmPostValues().keySet()) {
                        formPBody.add(ss, ArcAppManager.getInstance().getmPostValues().get(ss));
                    }
                }
                formBody = formPBody.build();
                String generateUrl = ARC_ROOT_URL
                        + ArcAppManager.getInstance().getExtraParam();
                ArcLog.w(generateUrl);
                OkHttpClient client = new OkHttpClient();
                Request request = null;
                if (ArcAppManager.getInstance().getmPostValues().size() > 0) {
                    request = new Request.Builder()
                            .url(generateUrl)
                            .post(formBody)
                            .addHeader("content-type", MEDIA_TYPE_WWW)
                            .addHeader("cache-control", "no-cache")
                            .addHeader("Authorization", ArcAppManager.getInstance().getAccessToken())
                            .build();
                } else {
                    request = new Request.Builder()
                            .url(generateUrl)
                            .addHeader("content-type", MEDIA_TYPE_WWW)
                            .addHeader("cache-control", "no-cache")
                            .addHeader("Authorization", ArcAppManager.getInstance().getAccessToken())
                            .build();
                }
                ArcLog.w(request.header("Authorization"));
                okhttp3.Response responsebody = client.newCall(request).execute();
                result.responseCode = responsebody.code();
                //Log.w("response code", responseCode + "");
                //Log.w("result ", responsebody.body().string());
                //result = new String(new MCrypt().decrypt(responsebody.body().string()));
                result.result = responsebody.body().string();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return result;
    }
}
