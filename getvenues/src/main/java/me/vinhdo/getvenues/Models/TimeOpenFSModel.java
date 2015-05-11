package me.vinhdo.getvenues.Models;

/**
 * Created by vinhdo on 5/12/15.
 */

import com.google.gson.annotations.SerializedName;

import java.util.Iterator;
import java.util.List;

public class TimeOpenFSModel {
    @SerializedName("days")
    private List<Integer> listDays;
    @SerializedName("open")
    private List<OpenTime> listOpens;

    public List<Integer> getListDays() {
        return this.listDays;
    }

    public List<OpenTime> getListOpens() {
        return this.listOpens;
    }

    public String getTime() {
        return getWeekDay() + "*" + getTimeOpen();
    }

    public String getTimeOpen() {
        String str = "";
        if ((this.listOpens != null) && (this.listOpens.size() > 0)) {
            for (OpenTime time : listOpens) {
                str = str + time.getOpenTime() + ",";
            }
        }
        if (str.length() > 0 && str.charAt(-1 + str.length()) == ',') {
            str = str.substring(0, -1 + str.length());
        }
        return str;
    }

    public String getWeekDay() {
        String str = "";
        if ((this.listDays != null) && (this.listDays.size() > 0)) {
            for (int day : listDays) {
                if ((day > 0) && (day < 7)) {
                    str = str + "TH" + (day + 1) + ",";
                } else if (day == 7) {
                    str = str + "CN,";
                }
            }
        }
        if (str.length() > 0 && str.charAt(-1 + str.length()) == ',') {
            str = str.substring(0, -1 + str.length());
        }
        return str;
    }

    public void setListDays(List<Integer> paramList) {
        this.listDays = paramList;
    }

    public void setListOpens(List<OpenTime> paramList) {
        this.listOpens = paramList;
    }
}

class OpenTime {
    @SerializedName("end")
    private String end;
    @SerializedName("start")
    private String start;

    public String getEnd() {
        return this.end.subSequence(0, 2) + ":" + this.end.subSequence(2, 4);
    }

    public String getOpenTime() {
        return getStart() + "-" + getEnd();
    }

    public String getStart() {
        return this.start.substring(0, 2) + ":" + this.start.subSequence(2, 4);
    }

    public void setEnd(String paramString) {
        this.end = paramString;
    }

    public void setStart(String paramString) {
        this.start = paramString;
    }
}
