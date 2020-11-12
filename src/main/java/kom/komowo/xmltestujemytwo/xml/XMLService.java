package kom.komowo.xmltestujemytwo.xml;

import kom.komowo.xmltestujemytwo.db1.entity.Wiersz;
import kom.komowo.xmltestujemytwo.db1.entity.WierszSet;
import kom.komowo.xmltestujemytwo.db1.entity.WierszT;
import kom.komowo.xmltestujemytwo.db1.entity.WierszTabeli;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;
import org.w3c.dom.*;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;




@Service
@Getter
@Setter
public class XMLService {

    private String nazwaPliku="dane_abs.xml";
    private String nazwaPlikuDocelowego="dane_abs1.xml";

    private int level;

    private ArrayList<String> poziomy = new ArrayList<String>();
    private ArrayList<ArrayList<String>> poziomy3 = new ArrayList<ArrayList<String>>();

    private String[][] poziomy2=new String[10][100000];
    private int[] poziomy2a=new int[10];
    private int licznik;

    private HashMap<String ,String> poziomy4= new HashMap<String, String>();


    private int iloscPoziomow;
    private XmlStructure struktura;
    private String[][] kolumny;
    private int wezel;
    private int pole;
    private  Element root;
    private Document document;

    private ArrayList<Wiersz> wiersze = new ArrayList<>();

    private ArrayList<Wiersz> wybraneKolumny = new ArrayList<>();

    ArrayList<WierszTabeli> wynikowaTabela;

    ArrayList<WierszTabeli> tablica= new ArrayList<WierszTabeli>();

    ArrayList<HashMap<String,String>> tab3 = new ArrayList<>();
    ArrayList<HashMap<String,String>> tab4 = new ArrayList<>();
    HashSet<HashMap<String,String>> tab6 = new HashSet<>();

    WierszTabeli ww;

    public WierszSet tab7;

    public HashSet<WierszT> tab8 = new HashSet<>();

    public void drzewoXml(){

        try{

            File file = new File(nazwaPliku);
            InputStreamReader isr = new InputStreamReader(new FileInputStream(file.getAbsolutePath()), "WINDOWS-1250");

            DocumentBuilderFactory documentBuilderFactory=DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            document = documentBuilder.parse(new InputSource(isr));

             root = document.getDocumentElement();

            document.getDocumentElement().normalize();

            if (root.hasChildNodes()) {

                licznik=0;
                identyfikujIloscPoziomow(root,1);
                struktura= new XmlStructure();
                struktura.setIloscPoziomow(iloscPoziomow);


                System.out.println("---1111-------");
                galazka5b(root,1,"");
                System.out.println("------2222----");
                System.out.println("ilosc unikalnych kolumn = "+ poziomy.size());
                System.out.println("-----33333-----");
                kolumny=new String[poziomy.size()+1][4];
                zasilTabliceKolumn();
                for(int k=1;k<=poziomy.size();k++){

                    for(int w=0;w<4;w++){
                        System.out.print(kolumny[k][w]+"+");
                    }
                    System.out.println();
                }
                //---------------------------------------------
                System.out.println("-----33333-----");

                for(int k=1;k<=poziomy.size();k++){
                    if(kolumny[k][1].equals("tak")){
                        wezel++;
                    }else {pole++;
                    }

                }
                System.out.println("-----33333-----");
                System.out.println("ilosc wezlow " + wezel);
                System.out.println("ilosc pol "+pole);
                System.out.println("-----33333-----");

                //---------------------------------------------

                System.out.println("-----4444-----");
                System.out.println("ilosc poziomow "+ iloscPoziomow);
                System.out.println("-----4444-----");

                for(String tekst:poziomy){
                    System.out.println(tekst);
                }
                System.out.println("ilosc kolumn "+poziomy.size());
                //---------------------------------------------
                System.out.println("----555555------");
                for(String nn:poziomy4.keySet()){
                    System.out.println("key "+nn +" value "+poziomy4.get(nn));
                }

                //---------------------------------------------
                System.out.println("------66666----");

                //---------------------------------------------
                poziomy2=convertTable(poziomy2);
                System.out.println("-----777777-----");
                for(int k=0;k<=iloscPoziomow;k++){

                    for(int w=0;w<poziomy2a[k];w++){
                        System.out.println(k+" "+w+" "+poziomy2[k][w]+" ");
                    }
                }
                //---------------------------------------------
                System.out.println("----88888------");
                for(int k=0;k<=iloscPoziomow;k++){

                    for(int w=0;w<poziomy2a[k];w++){


                        System.out.println(k+" "+w+" "+poziomy2[k][w]+" ");
                    }
                }

                //---------------------------------------------

            }
            }catch (Exception e){
                System.out.println(e.getMessage());
            }

    }
    //--------------------------------------------------------------------------------------------
        public void galazka(Node node)
        {
            if(node.hasChildNodes()){
                for (Node object = node.getFirstChild(); object != null; object = object.getNextSibling()){
                    if (object instanceof Element){
                        System.out.print("-");
                        galazka(object);
                    }
                }


            }
            else
            {
                if(node.getNodeType() == Node.ELEMENT_NODE) {
                    Element elem = (Element) node;
                    System.out.println(":"+elem.getNodeName() + " " );
                }
            }

        }
    //--------------------------------------------------------------------------------------------
    public void galazka2(Node node){

        if(node.hasChildNodes()) {
            NodeList nodeList1 = node.getChildNodes();
            System.out.println(node.getNodeName()+" ma dzieciaki :"+nodeList1.getLength());
            for (int j = 0; j < nodeList1.getLength(); j++) {
                Node node1 = nodeList1.item(j);
                if (node1.getNodeType() == node1.ELEMENT_NODE) {

                    Element elem = (Element) node1;
                    System.out.println(j +"dzieciak " + node1.getNodeName()+"-->"+elem.getTextContent());
                    //galazka2(node1);
                }
            }
        }else
        {
            System.out.println("*");
        }


    }
    //--------------------------------------------------------------------------------------------
    public void  galazka3(Node node,String name){

        if(node.hasChildNodes()) {
            NodeList nodeList1 = node.getChildNodes();


           // System.out.println(node.getNodeName()+" ma dzieciaki :"+nodeList1.getLength());
            for (int j = 0; j < nodeList1.getLength(); j++) {
                Node node1 = nodeList1.item(j);
                if (node1.getNodeType() == node1.ELEMENT_NODE ) {

                           // System.out.println(j + "dzieciak " + node1.getNodeName());

                    galazka3(node1,node1.getNodeName());

                }
                else {
                            if(!node1.getTextContent().toString().equals(""))
                            System.out.println(j + "**" +name+"**" +node1.getTextContent()+"++");

                }
            }
        }
        else {

        }

    }
    //--------------------------------------------------------------------------------------------
    public void galazka4(Node node,int n) {

            if(iloscPoziomow <n){
                iloscPoziomow++;
           //     nazwyKolumn[iloscKolumn]=node.getNodeName();

            }
        if (node.hasChildNodes()) {
            NodeList nodeList1 = node.getChildNodes();
            for (int i = 0; i < nodeList1.getLength(); i++) {

                Node node1 = nodeList1.item(i);

                if(node1.getNodeType() == Node.ELEMENT_NODE) {
                    Element elem1 = (Element) node1;
                    for(int k=0;k<n;k++)        System.out.print(" ");
                    if(elem1.getChildNodes().getLength()==1){



                        System.out.println("level-b "+n+" "+elem1.getNodeName()+"-->"+ elem1.getTextContent());
                       // System.out.println("++"+elem1.getTextContent());
                    }
                    else {
                        System.out.println("level-a "+n+" "+elem1.getTagName());
                        galazka4(elem1, n+1);
                    }
                }
                }
        }
    }
    //--------------------------------------------------------------------------------------------
    public void galazka5(Node node){
        //System.out.println(" galazka " + node.getNodeName()+" ma "+node.getChildNodes().getLength()+" dzieci:");
        if (node.hasChildNodes()) {
            NodeList nodeList1 = node.getChildNodes();
            for (int i = 0; i < nodeList1.getLength(); i++) {

                Node node1 = nodeList1.item(i);
                if(node1.getNodeType() == Node.ELEMENT_NODE) {


                    if (node1.hasChildNodes()) {
                          NodeList nodeList2 =node1.getChildNodes();


                            if(nodeList2.getLength()==1){
                               if(!nodeList1.item(0).hasChildNodes()){
                                   System.out.print(node1.getNodeName()+" ");
                                   System.out.println("nie ma gałązki");

                               }
                               else{

                               }
                            }else{
                                System.out.print(node1.getNodeName()+" ");
                                System.out.println("ma galazki");
                                galazka5(node1);
                            }

                    }
                }

            }
        }
    }
//--------------------------------------------------------------------------------------------
public void galazka5a(Node node,int n,String sciezka){
    //System.out.println(" galazka " + node.getNodeName()+" ma "+node.getChildNodes().getLength()+" dzieci:");
    if(iloscPoziomow <n){
        iloscPoziomow++;
      //  nazwyKolumn[iloscKolumn]=node.getNodeName();

    }

    if (node.hasChildNodes()) {
        NodeList nodeList1 = node.getChildNodes();
        for (int i = 0; i < nodeList1.getLength(); i++) {

            Node node1 = nodeList1.item(i);
            if(node1.getNodeType() == Node.ELEMENT_NODE) {


                if (node1.hasChildNodes()) {
                    NodeList nodeList2 =node1.getChildNodes();
                    for(int k=0;k<n;k++)        System.out.print(" ");
                    //sciezka=sciezka+node1.getNodeName();
                    System.out.print(sciezka);
                    System.out.print(node1.getNodeName()+" ");
                    if(nodeList2.getLength()==1){
                        if(!nodeList2.item(0).hasChildNodes()){
                            System.out.print(node1.getTextContent()+" ");
                            System.out.println("nie ma gałązki");

                        }
                        else{                        }
                    }else{

                        System.out.println("ma galazki");
                        galazka5a(node1,n+1,sciezka);
                    }

                }
            }

        }
    }
}

//--------------------------------------------------------------------------------------------
public void galazka7(Node node, int n, String sciezka){
    //System.out.println(" galazka " + node.getNodeName()+" ma "+node.getChildNodes().getLength()+" dzieci:");
    if(iloscPoziomow <n){
        iloscPoziomow++;
      //  nazwyKolumn[iloscKolumn]=node.getNodeName();

    }

    if (node.hasChildNodes()) {
        NodeList nodeList1 = node.getChildNodes();
        sciezka=sciezka+" "+node.getNodeName();

        for (int i = 0; i < nodeList1.getLength(); i++) {

            Node node1 = nodeList1.item(i);
            if(node1.getNodeType() == Node.ELEMENT_NODE) {


                if (node1.hasChildNodes()) {
                    NodeList nodeList2 =node1.getChildNodes();

                 //   for(int k=0;k<n;k++)        System.out.print(" ");


                    System.out.print(sciezka+" ");

                   // System.out.print(node1.getNodeName()+" ");
                    if(nodeList2.getLength()==1){
                        if(!nodeList1.item(0).hasChildNodes()){

                            System.out.println(" "+" nie ma gałązki");

                        }
                        else{                        }
                    }else{

                        System.out.println("ma galazki");

                        galazka7(node1,n+1,sciezka);
                    }

                }
            }

        }
    }
}
//--------------------------------------------------------------------------------------------
public void galazka5b(Node node,int n,String sciezka){


    if (node.hasChildNodes()) {
        NodeList nodeList1 = node.getChildNodes();
        for (int i = 0; i < nodeList1.getLength(); i++) {

            Node node1 = nodeList1.item(i);
            if(node1.getNodeType() == Node.ELEMENT_NODE) {


                if (node1.hasChildNodes()) {
                    NodeList nodeList2 =node1.getChildNodes();
                   // for(int k=0;k<n;k++)        System.out.print(" ");


                    if(nodeList2.getLength()==1){
                        if(!nodeList2.item(0).hasChildNodes()){
                            System.out.print("lv:"+n+" ");
                            System.out.print("sc:"+sciezka+" ");
                            System.out.print(" nn:"+node1.getNodeName()+" ");
                            System.out.print(" tx"+node1.getTextContent()+" ");
                            System.out.println("<************ nie ma gałązki");
                           // System.out.println("");
                           // poziomy.add(node1.getNodeName());

                            if(!poziomy.contains(n+"*"+"nie*"+node1.getNodeName()+"*"+node.getNodeName())){
                                poziomy.add(n+"*"+"nie*"+node1.getNodeName()+"*"+node.getNodeName());
                            }
                            poziomy2[n][poziomy2a[n]]=node1.getNodeName();
                            poziomy2a[n]++;

                            poziomy4.put(n+"_"+node1.getNodeName(),node1.getNodeName());


                        }
                        else{                        }
                    }else{
                        System.out.print("lv:"+n+" ");
                        System.out.print("sc:"+sciezka+" ");
                        System.out.print("nn:"+node1.getNodeName()+" ");
                       // System.out.println("");
                        System.out.println("<-------- ma galazki");

                            if(!poziomy.contains(n+"*"+"tak*"+node1.getNodeName()+"*"+node.getNodeName())){
                                poziomy.add(n+"*"+"tak*"+node1.getNodeName()+"*"+node.getNodeName());
                            }


                        poziomy2[n][poziomy2a[n]]=node1.getNodeName();
                        poziomy2a[n]++;

                        poziomy4.put(n+"_"+node1.getNodeName(),node1.getNodeName());
                        galazka5b(node1,n+1,sciezka+"/"+node1.getNodeName());


                    }

                }
            }

        }
    }
}

    //--------------------------------------------------------------------------------------------
        public void identyfikujIloscPoziomow(Node node,int n) {
              if(iloscPoziomow <n){  iloscPoziomow++; }

            if (node.hasChildNodes()) {
                NodeList nodeList1 = node.getChildNodes();
                for (int i = 0; i < nodeList1.getLength(); i++) {
                           Node node1 = nodeList1.item(i);
                            if (node1.getNodeType() == Node.ELEMENT_NODE) {
                               if (node1.hasChildNodes()) {
                                    NodeList nodeList2 = node1.getChildNodes();
                                      if (!(nodeList2.getLength() == 1)) {
                                             identyfikujIloscPoziomow(node1, n + 1);
                                             licznik++;
                                        }else{
                                          licznik++;
                                      }
                                }
                            }
                }
            }
        }
    //--------------------------------------------------------------------------------------------
public  String[][]  convertTable(String[][] tablica){

            String[][]  poziomy3convert = new String[10][100000];

            for(int i=1;i<=iloscPoziomow;i++) {
                ArrayList<String> lista = new ArrayList<>();
                for(int j=0;j<poziomy2a[i];j++){
                    if(!lista.contains(tablica[i][j])){
                        lista.add(tablica[i][j]);
                    }
                }
                int j=0;
                for(String tekst:lista){
                    poziomy3convert[i][j]=tekst;
                    j++;
                }
                poziomy2a[i]=j;

            }

    return poziomy3convert;
}

//--------------------------------------------------------------------------------------------
public void zasilTabliceKolumn(){

        int k=1;
        for(String tekst:poziomy){

        String[] rezultat=tekst.split(String.valueOf("\\*"));
        kolumny[k][0]=rezultat[0];
            kolumny[k][1]=rezultat[1];
            kolumny[k][2]=rezultat[2];
            kolumny[k][3]=rezultat[3];
            NodeList nodeList= root.getElementsByTagName(kolumny[k][2]);
            wiersze.add(new Wiersz( Integer.parseInt(kolumny[k][0]),kolumny[k][1],kolumny[k][2],kolumny[k][3],nodeList.getLength()));

            k++;
    }
}
//--------------------------------------------------------------------------------------------
    public void dzialaj(        String nazwaNoda){

       identyfikujPola(nazwaNoda);
      NodeList nodeList = document.getElementsByTagName(nazwaNoda);
      wynikowaTabela= new ArrayList<>();
        System.out.println("######1111###########");

        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);

            if(node.getNodeType() == Node.ELEMENT_NODE) {
                Element elem = (Element) node;
                WierszTabeli w2 = new WierszTabeli();
                ArrayList<String> t=new ArrayList<>();

                int ilosc=0;
                for(Wiersz w:wybraneKolumny){

                    if(ilosc<elem.getElementsByTagName(w.getNazwa()).getLength()) {
                        ilosc = elem.getElementsByTagName(w.getNazwa()).getLength();
                    }
                           if(ilosc==1){
                               t.add(elem.getElementsByTagName(w.getNazwa()).item(0).getTextContent());
                           }
                           else{}
               }
                   w2.setDane(t);
                   wynikowaTabela.add(w2);
            }

        }
        System.out.println("#####################");
        System.out.println("ilosc nodow"+nodeList.getLength());
        System.out.println("#####################");
}
    //--------------------------------------------------------------------------------------------

    public void identyfikujPola(String tata){

            for(Wiersz wiersz: wiersze){
                if(wiersz.getOjciec().equals(tata)){
                    if(wiersz.getWezel().equals("tak")){
                        identyfikujPola(wiersz.getNazwa());
                    }else{
                        wybraneKolumny.add(wiersz);
                    }
                }

            }


}
//--------------------------------------------------------------------------------------------
public void xpathujemy( String nazwaNoda){
    //xpath
    XPath xPath =  XPathFactory.newInstance().newXPath();
    String expression = "/DANE_ABSENCJA/LIST_G_NR_PRAC1/*";

    try {
        NodeList listaNodow = (NodeList) xPath.compile(expression).evaluate(document, XPathConstants.NODESET);
        System.out.println("xpath count"+listaNodow.getLength());

    }
    catch (XPathExpressionException e)
    {

    }
    //------------------
    NodeList nodeList = document.getElementsByTagName("NR_PRAC");
    System.out.println(" NR_PRAC count"+nodeList.getLength());
     nodeList = document.getElementsByTagName("G_NR_PRAC");
    System.out.println(" G_NR_PRAC count"+nodeList.getLength());
    Node node = nodeList.item(0);
    Element elem = (Element) node;
    System.out.println("tekst kontext->"+elem.getTextContent());
    System.out.println("przerwa");
    System.out.println("parent to->"+elem.getParentNode().getNodeName());
    System.out.println("prefix->"+elem.getPrefix());

    System.out.println("parent parenta->"+(elem.getParentNode()).getParentNode().getNodeName());
    System.out.println("ilosc dzieci parenta"+elem.getParentNode().getChildNodes().getLength());
    for(int n=0;n<elem.getParentNode().getChildNodes().getLength();n++){
        Node node3 = elem.getParentNode().getChildNodes().item(n);
       // if(node3.getNodeType() == Node.ELEMENT_NODE) {
            System.out.println(n+"---------"+node3.getTextContent());
       // }


    }

    //------------------

}
//--------------------------------------------------------------------------------------------
public void galazka7(){

      //tablica= new ArrayList<WierszTabeli>(); //  ArrayList<WierszTabeli>
        HashMap<String,String> wierszyk = new HashMap<String, String>();

     galazka7Rek(wierszyk,root);

    ArrayList<WierszTabeli> tablica2= new ArrayList<>();
    for(WierszTabeli w:tablica){
        if(!tablica2.contains(w))
            tablica2.add(w);


    }

    for(HashMap<String,String> w :tab3)
    {
        if(!tab3.contains(w)){
            tab4.add(w);
        }
    }

    HashSet<HashMap<String,String>> tab5 = new HashSet<>(tab3);

    for(HashMap<String,String> w:tab6 ){
    //    System.out.println(w);
    }
int c=0;
    for(WierszT w:tab8 ){
           System.out.println(w);
           c++;
    }
    System.out.println(c+" tyle c");

    System.out.println("koniec?");
}
//--------------------------------------------------------------------------------------------

public void galazka7Rek(HashMap<String,String> w, Node node){

    if (node.hasChildNodes()) {
        NodeList nodeList1 = node.getChildNodes();
        for (int i = 0; i < nodeList1.getLength(); i++) {

            Node node1 = nodeList1.item(i);
            //----------------------
            if(node1.getNodeType() == Node.ELEMENT_NODE) {
                int marker=0;
                //******************* sprawdzamy czy dzieci mają dzieci


                for (int j = 0; j < nodeList1.getLength(); j++) {
                    if(nodeList1.item(j)!=null) {
                        Node node2 = nodeList1.item(j);
                        if (node2.getNodeType() == Node.ELEMENT_NODE) {
                            NodeList nodeList2 = node2.getChildNodes();
                          //  if (String.valueOf(nodeList2.getLength()) != null)
                                if (nodeList2.getLength() > 1) marker = 1;

                        }
                    }
                }
                //******************* jak dzieci  mają dzieci
                if (marker==1) {
                    // wpierw nie mające dzieci dodajemy
                    for (int j = 0; j < nodeList1.getLength(); j++) {
                        Node node2 = nodeList1.item(j);
                        if(node2.getNodeType() == Node.ELEMENT_NODE) {
                            NodeList nodeList2 = node2.getChildNodes();
                            if (nodeList2.getLength()==1)
                            {
                                w.put(node2.getNodeName(),node2.getTextContent());

                            }

                        }

                    }
                    // pozniej zajmujemy sie tymi ktore maja dzieci
                    for (int j = 0; j < nodeList1.getLength(); j++) {
                        Node node2 = nodeList1.item(j);
                        if(node2.getNodeType() == Node.ELEMENT_NODE) {
                            NodeList nodeList2 = node2.getChildNodes();
                            //if (nodeList2.getLength()>1)
                            {
                                for(int x =0;x<nodeList2.getLength();x++){
                                    Node node3 = nodeList2.item(x);
                                    galazka7Rek(w,node3);
                                }

                            }

                        }

                    }
                   ////////////////
                }
                //******************* jak dzieci nie mają dziecie
                else {
                    for (int j = 0; j < nodeList1.getLength(); j++) {
                        Node node2 = nodeList1.item(j);
                        if(node2.getNodeType() == Node.ELEMENT_NODE) {
                            NodeList nodeList2 = node2.getChildNodes();
                            if (nodeList2.getLength()==1)
                            {
                                w.put(node2.getNodeName(),node2.getTextContent());

                            }

                        }

                    }
                   // tt
                     ww = new WierszTabeli();//WierszTabeli
                    ww.setWierszyk(w);
                  // if(!tab3.contains(w))
                    tab3.add(w);
                    tab6.add(w);
                   // tab7.putput(ww);
                   // System.out.println(ww);

                  WierszT www= new WierszT();
                  for(String key: w.keySet()){
                      www.getWiersz().put(key,w.get(key));

                  }
                  tab8.add(www);

                 // if(!tablica.contains(ww))
                    tablica.add(ww);
                    //tt
                }
                //*******************
            }
            //----------------------
        }
    }

}

//--------------------------------------------------------------------------------------------


}
