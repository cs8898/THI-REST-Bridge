package ml.raketeufo.thi.restbride.entity.room;

import java.util.Date;

public class TimeSlot {
    private static final long[] START_TIMES = {29700000, 32400000, 35700000, 38400000, 44400000, 47700000, 50400000, 53700000, 56400000, 59700000, 62400000, 65700000, 68400000, 71700000, 74400000};
    private static final long[] END_TIMES = {32400000, 35700000, 38400000, 44400000, 47700000, 50400000, 53700000, 56400000, 59700000, 62400000, 65700000, 68400000, 71700000, 74400000};
    public Date start;
    public Date end;
    public Integer startStunde;
    public Integer endStunde;

    public TimeSlot merge(TimeSlot otherSlot){
        if(this.startStunde+1 <= otherSlot.startStunde && this.endStunde+1 <= otherSlot.startStunde){
            //Mergeable
            this.endStunde = otherSlot.startStunde;
        }else{
            return null;
        }
        return this;
    }

    public void calcTimes(Date date){
        start = new Date();
        //start.setTime()

    }
}
