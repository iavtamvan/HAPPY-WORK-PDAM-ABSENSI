package co.id.pdamkotasmg.pekerjaanteknik.model.fileHandler;

import com.google.gson.annotations.SerializedName;

public class Data{

    @SerializedName("path")
    private String path;

    @SerializedName("filetype")
    private Object filetype;

    @SerializedName("filename")
    private String filename;

    @SerializedName("filepath")
    private String filepath;

    @SerializedName("fileurl")
    private String fileurl;

    @SerializedName("fileurl_alias")
    private String fileurlAlias;

    public void setPath(String path){
        this.path = path;
    }

    public String getPath(){
        return path;
    }

    public void setFiletype(Object filetype){
        this.filetype = filetype;
    }

    public Object getFiletype(){
        return filetype;
    }

    public void setFilename(String filename){
        this.filename = filename;
    }

    public String getFilename(){
        return filename;
    }

    public void setFilepath(String filepath){
        this.filepath = filepath;
    }

    public String getFilepath(){
        return filepath;
    }

    public void setFileurl(String fileurl){
        this.fileurl = fileurl;
    }

    public String getFileurl(){
        return fileurl;
    }

    public void setFileurlAlias(String fileurlAlias){
        this.fileurlAlias = fileurlAlias;
    }

    public String getFileurlAlias(){
        return fileurlAlias;
    }
}