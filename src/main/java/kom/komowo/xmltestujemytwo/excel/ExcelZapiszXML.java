package kom.komowo.xmltestujemytwo.excel;


import kom.komowo.xmltestujemytwo.db1.entity.ExcelEntityModel;
import kom.komowo.xmltestujemytwo.db1.entity.Wiersz;
import kom.komowo.xmltestujemytwo.db1.entity.WierszT;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.servlet.view.document.AbstractXlsView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

public class ExcelZapiszXML extends AbstractXlsView {

        @Override
        protected void buildExcelDocument(Map<String, Object> model, Workbook workbook, HttpServletRequest request,
                                          HttpServletResponse response) throws Exception {


            response.setHeader("Content-Disposition", "attachment;filename=\"wykaz.xls\"");
            Sheet sheet = workbook.createSheet("dane");
            Row header = sheet.createRow(0);

            ExcelEntityModel dane = (ExcelEntityModel) model.get("dane");

            ArrayList<Wiersz> kolumny= dane.getKolumny();
            int k=1;
            for(Wiersz w:kolumny){
                header.createCell(k).setCellValue(w.getNazwa());
                k++;
            }


            HashSet<WierszT> tab8= dane.getTab8();
            int rowNum = 1;
            for(WierszT w:tab8){
                Row row = sheet.createRow(rowNum++);
                int k2=1;
                for(Wiersz w2:kolumny){
                    row.createCell(k2++).setCellValue(w.getWiersz().get(w2.getNazwa()));
                }

            }


        }

    }
