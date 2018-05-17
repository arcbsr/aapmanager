package arcAppManager;

/**
 * Created by Devbd on 3/26/18.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PromotedAppsInfo {

    @SerializedName("id")
    @Expose
    public Integer id;
    @SerializedName("pkg_name")
    @Expose
    public String pkgName;
    @SerializedName("app_name")
    @Expose
    public String appName;
    @SerializedName("app_icon")
    @Expose
    public String appIcon;
    @SerializedName("app_banner")
    @Expose
    public String appBanner;
    @SerializedName("short_description")
    @Expose
    public String shortDescription;

}