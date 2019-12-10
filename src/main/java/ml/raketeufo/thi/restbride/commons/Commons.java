package ml.raketeufo.thi.restbride.commons;

public class Commons {

    public static Double parseDoubleString(String str){
        return Double.parseDouble("0"+ str.replace(".","").replace(",","."));
    }
}
