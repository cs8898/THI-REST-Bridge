package ml.raketeufo.thi.restbride.backendcom.response;

import ml.raketeufo.thi.restbride.entity.backend.user.UserInformation;
import ml.raketeufo.thi.restbride.inputconverter.UserInformationConverter;

import javax.inject.Inject;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.bind.annotation.JsonbTransient;

public class PersDataResponse extends BaseResponse {

    private static final int INDEX_MAGICNUMBER = 0;

    @JsonbTransient
    int magicnumber;

    UserInformation info;

    public int getMagicnumber() {
        return this.magicnumber;
    }

    public UserInformation getInfo() {
        return this.info;
    }


    public PersDataResponse(JsonObject obj) {
        super(obj);
    }

    @Override
    public void map(JsonObject obj) {
        super.map(obj);
        if (super.isOk()) {
            JsonArray dataArray = obj.getJsonArray(DATA);
            magicnumber = dataArray.getInt(INDEX_MAGICNUMBER);
            this.info = UserInformationConverter.convert(obj);
        }
    }
}
