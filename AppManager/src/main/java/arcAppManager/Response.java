
package arcAppManager;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Response {

    @SerializedName("id")
    @Expose
    private Integer id = 0;
    @SerializedName("user_id")
    @Expose
    private Integer userId = 0;
    @SerializedName("pkg_name")
    @Expose
    private String pkgName = "";
    @SerializedName("app_name")
    @Expose
    private String appName = "";
    @SerializedName("platform")
    @Expose
    private String platform = "";
    @SerializedName("app_link")
    @Expose
    private String appLink = "";
    @SerializedName("app_version")
    @Expose
    private String appVersion = "";
    @SerializedName("is_major_update")
    @Expose
    private Object isMajorUpdate = false;
    @SerializedName("short_description")
    @Expose
    private String shortDescription = "";
    @SerializedName("description")
    @Expose
    private String description = "";
    @SerializedName("app_icon")
    @Expose
    private String appIcon = "";
    @SerializedName("app_banner")
    @Expose
    private String appBanner = "";
    @SerializedName("links")
    @Expose
    private String links = "";
    @SerializedName("app_data")
    @Expose
    private String appData = "";
    @SerializedName("created_at")
    @Expose
    private String createdAt = "";
    @SerializedName("updated_at")
    @Expose
    private String updatedAt = "";
    @SerializedName("promoted_apps_info")
    @Expose
    private List<PromotedAppsInfo> promotedAppsInfo = new ArrayList<>();
    @SerializedName("screenshots")
    @Expose
    private List<Screenshot> screenshots = new ArrayList<>();

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getStatusAds() {
        return statusAds;
    }

    public void setStatusAds(Integer statusAds) {
        this.statusAds = statusAds;
    }

    public Integer getIsPromoteApps() {
        return isPromoteApps;
    }

    public void setIsPromoteApps(Integer isPromoteApps) {
        this.isPromoteApps = isPromoteApps;
    }

    @SerializedName("status")
    @Expose
    private Integer status = 0;
    @SerializedName("status_ads")
    @Expose
    private Integer statusAds = 0;
    @SerializedName("is_promote_apps")
    @Expose
    private Integer isPromoteApps = 0;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getPkgName() {
        return pkgName;
    }

    public void setPkgName(String pkgName) {
        this.pkgName = pkgName;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public String getAppLink() {
        return appLink;
    }

    public void setAppLink(String appLink) {
        this.appLink = appLink;
    }

    public String getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }

    public boolean getIsMajorUpdate() {
        try {
            if (isMajorUpdate.toString().equalsIgnoreCase("on")) {
                return true;
            } else if (isMajorUpdate.toString().equalsIgnoreCase("off")) {
                return false;
            }
            return Boolean.parseBoolean(isMajorUpdate.toString());
        } catch (Exception e) {
            return false;
        }
    }

    public void setIsMajorUpdate(Object isMajorUpdate) {
        this.isMajorUpdate = isMajorUpdate;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAppIcon() {
        return appIcon;
    }

    public void setAppIcon(String appIcon) {
        this.appIcon = appIcon;
    }

    public String getAppBanner() {
        return appBanner;
    }

    public void setAppBanner(String appBanner) {
        this.appBanner = appBanner;
    }

    public String getLinks() {
        return links;
    }

    public void setLinks(String links) {
        this.links = links;
    }

    public String getAppData() {
        return appData;
    }

    public void setAppData(String appData) {
        this.appData = appData;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public List<PromotedAppsInfo> getPromotedAppsInfo() {
        return promotedAppsInfo;
    }

    public void setPromotedAppsInfo(List<PromotedAppsInfo> promotedAppsInfo) {
        this.promotedAppsInfo = promotedAppsInfo;
    }

    public List<Screenshot> getScreenshots() {
        return screenshots;
    }

    public void setScreenshots(List<Screenshot> screenshots) {
        this.screenshots = screenshots;
    }

}
