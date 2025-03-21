package co.id.pdamkotasmg.pekerjaanteknik.model.warning;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class DataItem{

    @SerializedName("peringatan")
    private List<String> peringatan;

    @SerializedName("hello")
    private String hello;

    @SerializedName("status_apps")
    private String statusApps;

    @SerializedName("status_apps_message")
    private String statusAppsMessage;

    @SerializedName("update_apk")
    private List<String> updateApk;

    @SerializedName("message_update")
    private List<String> messaggeUpdate;

    @SerializedName("warning")
    private List<String> warning;

    @SerializedName("verifikator")
    private List<String> verifikator;

    @SerializedName("error")
    private List<String> error;

    @SerializedName("info")
    private List<String> info;

    @SerializedName("image_header")
    private String image_header;

    public String getStatusAppsMessage() {
        return statusAppsMessage;
    }

    public void setStatusAppsMessage(String statusAppsMessage) {
        this.statusAppsMessage = statusAppsMessage;
    }

    public String getStatusApps() {
        return statusApps;
    }

    public void setStatusApps(String statusApps) {
        this.statusApps = statusApps;
    }

    public String getHello() {
        return hello;
    }

    public void setHello(String hello) {
        this.hello = hello;
    }

    public List<String> getMessaggeUpdate() {
        return messaggeUpdate;
    }

    public void setMessaggeUpdate(List<String> messaggeUpdate) {
        this.messaggeUpdate = messaggeUpdate;
    }

    public String getImage_header() {
        return image_header;
    }

    public void setImage_header(String image_header) {
        this.image_header = image_header;
    }

    public void setPeringatan(List<String> peringatan){
        this.peringatan = peringatan;
    }

    public List<String> getPeringatan(){
        return peringatan;
    }

    public void setUpdateApk(List<String> updateApk){
        this.updateApk = updateApk;
    }

    public List<String> getUpdateApk(){
        return updateApk;
    }

    public void setWarning(List<String> warning){
        this.warning = warning;
    }

    public List<String> getWarning(){
        return warning;
    }

    public void setVerifikator(List<String> verifikator){
        this.verifikator = verifikator;
    }

    public List<String> getVerifikator(){
        return verifikator;
    }

    public void setError(List<String> error){
        this.error = error;
    }

    public List<String> getError(){
        return error;
    }

    public void setInfo(List<String> info){
        this.info = info;
    }

    public List<String> getInfo(){
        return info;
    }
}