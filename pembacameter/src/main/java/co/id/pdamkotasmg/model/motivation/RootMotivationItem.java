package co.id.pdamkotasmg.model.motivation;

import com.google.gson.annotations.SerializedName;

public class RootMotivationItem{

    @SerializedName("quote")
    private String quote;

    @SerializedName("by")
    private String by;

    public void setQuote(String quote){
        this.quote = quote;
    }

    public String getQuote(){
        return quote;
    }

    public void setBy(String by){
        this.by = by;
    }

    public String getBy(){
        return by;
    }
}