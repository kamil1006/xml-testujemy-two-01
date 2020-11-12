package kom.komowo.xmltestujemytwo.db1.entity;

import lombok.Data;

import java.util.HashSet;

@Data
public class WierszSet {

    HashSet<WierszTabeli> kontener;

    public WierszSet(HashSet<WierszTabeli> kontener) {
        this.kontener = kontener;
    }

    public WierszSet() {
        kontener= new HashSet<>();
    }
    public void putput(WierszTabeli w){
        kontener.add(w);

    }
}
