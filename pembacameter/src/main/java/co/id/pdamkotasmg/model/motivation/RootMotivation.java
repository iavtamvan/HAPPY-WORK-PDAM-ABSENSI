package co.id.pdamkotasmg.model.motivation;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RootMotivation{

    @SerializedName("RootMotivation")
    private List<RootMotivationItem> rootMotivation;

    public void setRootMotivation(List<RootMotivationItem> rootMotivation){
        this.rootMotivation = rootMotivation;
    }

    public List<RootMotivationItem> getRootMotivation(){
        return rootMotivation;
    }
}