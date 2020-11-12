package kom.komowo.xmltestujemytwo.db1.entity;

import lombok.Data;

@Data
public class Wiersz {
private int poziom;
private String wezel;
private String nazwa;
    private String ojciec;
    private int iloscWystapien;

    public Wiersz(int poziom, String wezel, String nazwa,String ojciec,int iloscWystapien) {
        this.poziom = poziom;
        this.wezel = wezel;
        this.nazwa = nazwa;
        this.ojciec = ojciec;
        this.iloscWystapien = iloscWystapien;
    }
}
