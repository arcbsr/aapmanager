package arcAppManager;

import android.util.Log;

/**
 * Created by Devbd on 3/26/18.
 */

class ArcLog {
    private final static String arcLogTag = "arc manager";
    static boolean IS_LOG = false;

    static void w(String log) {
        if (IS_LOG)
            Log.w(arcLogTag, log);
    }
}
