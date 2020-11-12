package kom.komowo.xmltestujemytwo.db1.entity;

import lombok.Data;

import java.util.ArrayList;
import java.util.HashSet;

@Data
public class ExcelEntityModel {
    private HashSet<WierszT> tab8;
    private ArrayList<Wiersz> kolumny;

    public ExcelEntityModel() {
        tab8=new HashSet<>();
        kolumny=new ArrayList<>();
    }
}
