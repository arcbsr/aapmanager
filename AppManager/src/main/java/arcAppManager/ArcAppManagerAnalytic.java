package arcAppManager;

import android.content.Context;


/**
 * Created by Devbd on 12/3/16.
 */

class ArcAppManagerAnalytic {
    //    private FirebaseAnalytics mFirebaseAnalytics;
    private Context mContext;

    ArcAppManagerAnalytic(Context context) {
//        this.mContext = context;
//        mFirebaseAnalytics = FirebaseAnalytics.getInstance(mContext);
    }

    void pushToAnalytic(String name, String type, String id) {
//        ArcLog.w("analytics status: Triggerd>>name:" + name + " ,type:" + type + ", id:" + id);
//        Bundle bundle = new Bundle();
//        bundle.putString(FirebaseAnalytics.Param.ITEM_ID, id);
//        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, name);
//        bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, type);
//        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);
    }

    void pushToAnalytic(String type) {
        pushToAnalytic("not_given", type, "not_given_id");
    }

    void pushToAnalytic(String type, String id) {
        pushToAnalytic("not_given", type, id);
    }

}
