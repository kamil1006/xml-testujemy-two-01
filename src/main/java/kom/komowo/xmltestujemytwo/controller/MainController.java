package kom.komowo.xmltestujemytwo.controller;

import kom.komowo.xmltestujemytwo.db1.entity.ExcelEntityModel;
import kom.komowo.xmltestujemytwo.db1.entity.Wiersz;
import kom.komowo.xmltestujemytwo.db1.entity.WierszT;
import kom.komowo.xmltestujemytwo.db1.entity.WierszTabeli;
import kom.komowo.xmltestujemytwo.excel.ExcelZapiszXML;
import kom.komowo.xmltestujemytwo.xml.XMLService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Controller
public class MainController {

    @Autowired
    XMLService xmlService;

    ArrayList<Wiersz> kolumny;

    //-------------------------------------------------------------------------------------------
    @GetMapping("/")
    public String stronaStartowa(Model model){





        return "main";
    }
    //-------------------------------------------------------------------------------------------
    @PostMapping("/")
    public String handleFileUpload(@RequestParam("file") MultipartFile file,
                                   RedirectAttributes redirectAttributes) {

        System.out.println( file.getOriginalFilename());
        redirectAttributes.addFlashAttribute("message",
                "Z sukcesem wskazales na plik: " + file.getOriginalFilename() + "!");


       xmlService.setNazwaPliku(file.getOriginalFilename());
        xmlService.drzewoXml();

        System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++ il poziomow "+xmlService.getIloscPoziomow());
       Integer p=xmlService.getIloscPoziomow();
        redirectAttributes.addFlashAttribute("ilPoziomow",p.toString());
        redirectAttributes.addFlashAttribute("poziomy",xmlService.getPoziomy());
        redirectAttributes.addFlashAttribute("wiersze",xmlService.getWiersze());
        return "redirect:/";
    }
    //-------------------------------------------------------------------------------------------
    @PostMapping("/wybor")
    public String handleFileUpload(@RequestParam(value = "wybor") String wybor,Model model) {
        System.out.println(wybor);

        String wiadomosc=" wybrales " +wybor;
        model.addAttribute("message2",wiadomosc);
        xmlService.dzialaj(wybor);
         kolumny = xmlService.getWybraneKolumny();
        model.addAttribute("wybraneKolumny",kolumny);
        System.out.println("---+++----");
        for(Wiersz w:kolumny){
            System.out.println(w);
        }
        System.out.println("---+++----");
        int licz=0;
        for(WierszTabeli w: xmlService.getWynikowaTabela()){
            System.out.println(w);
            licz++;
        }
        System.out.println("---+++----");
        System.out.println(licz);
        System.out.println("---+++----");
       // xmlService.xpathujemy(wybor);

        xmlService.galazka7();

       HashSet<WierszT> tab8 = xmlService.getTab8();
        model.addAttribute("wyniki",tab8);

        return "wybor";
    }
    //-------------------------------------------------------------------------------------------
    @PostMapping("/generujExcel")
    public ModelAndView zapiszExcel(Model model){

        ExcelEntityModel dane = new ExcelEntityModel();
        dane.setKolumny(kolumny);
        dane.setTab8(xmlService.getTab8());
                model.addAttribute("dane",dane);

        return  new ModelAndView(new ExcelZapiszXML(),
                "dane",
                dane);
    }

    //-------------------------------------------------------------------------------------------

}
