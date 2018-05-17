
package arcAppManager;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Screenshot {

    @SerializedName("id")
    @Expose
    private Integer id=0;
    @SerializedName("app_detail_id")
    @Expose
    private Integer appDetailId=0;
    @SerializedName("photo_name")
    @Expose
    private String photoName="";
    @SerializedName("created_at")
    @Expose
    private String createdAt="";
    @SerializedName("updated_at")
    @Expose
    private String updatedAt="";

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAppDetailId() {
        return appDetailId;
    }

    public void setAppDetailId(Integer appDetailId) {
        this.appDetailId = appDetailId;
    }

    public String getPhotoName() {
        return photoName;
    }

    public void setPhotoName(String photoName) {
        this.photoName = photoName;
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

}
