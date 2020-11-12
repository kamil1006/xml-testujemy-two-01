package kom.komowo.xmltestujemytwo.db1.entity;

import lombok.Data;

import java.util.HashMap;

@Data
public class WierszT {
    private HashMap<String,String> wiersz;

    public WierszT(HashMap<String, String> wiersz) {
        this.wiersz = wiersz;
    }

    public WierszT() {
        wiersz= new HashMap<>();
    }
}
