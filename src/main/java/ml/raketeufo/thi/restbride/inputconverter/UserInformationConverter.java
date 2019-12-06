package ml.raketeufo.thi.restbride.inputconverter;

import ml.raketeufo.thi.restbride.entity.Adresse;
import ml.raketeufo.thi.restbride.entity.Pruefungsordnung;
import ml.raketeufo.thi.restbride.entity.Rueckmeldung;
import ml.raketeufo.thi.restbride.entity.UserInformation;

import javax.json.JsonArray;
import javax.json.JsonObject;


public class UserInformationConverter {

    public static UserInformation convert(JsonObject json){
        UserInformation userInfo = new UserInformation();
        JsonArray dataArray = json.getJsonArray("data");
        JsonObject data = dataArray.getJsonObject(1);
        String pcounter = data.getString("pcounter");
        pcounter = pcounter.replaceAll("€","");
        userInfo.printerCredit = Double.parseDouble(pcounter);
        data = data.getJsonObject("persdata");
        userInfo.bibliotheksNummer = data.getString("bibnr");
        userInfo.email = data.getString("email");
        userInfo.fachrichtung = data.getString("fachrich");
        userInfo.internalEmail = data.getString("fhmail");
        userInfo.matrikelNummer = data.getString("mtknr");
        userInfo.name = data.getString("name");

        Adresse adresse = new Adresse();
        adresse.str = data.getString("str");
        adresse.plz = data.getString("plz");
        adresse.ort = data.getString("ort");
        userInfo.addresse = adresse;

        Pruefungsordnung pruefungsordnung = new Pruefungsordnung();
        pruefungsordnung.url = data.getString("po_url");
        pruefungsordnung.version = data.getString("pver");
        userInfo.pruefungsordnung = pruefungsordnung;

        Rueckmeldung rueckmeldung = new Rueckmeldung();
        rueckmeldung.rueckgemeldet = data.getString("rue").equals("1");
        rueckmeldung.semester = data.getString("rue_sem");
        userInfo.rueckmeldung = rueckmeldung;

        userInfo.studiengang = data.getString("stg");
        userInfo.studiengruppe = data.getString("stgru");

        // userInfo.schwerpunkte =

        userInfo.telefon = data.getString("telefon");
        userInfo.user = data.getString("user");
        userInfo.vorname = data.getString("vname");

        return userInfo;
    }
}
