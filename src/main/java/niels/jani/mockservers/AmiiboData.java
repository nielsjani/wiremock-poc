package niels.jani.mockservers;

import java.io.Serializable;

public class AmiiboData implements Serializable {
    public String data;

    private AmiiboData(){}

    public AmiiboData(String data){
        this.data = data;
    }
}
