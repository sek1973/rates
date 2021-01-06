package sk.servlets;

import java.sql.Date;
import java.sql.Timestamp;

/** class to hold rate query history data - reflects database record */
public class HistoryEntity {
    public int id;
    public Timestamp ts;
    public Date date;
    public Double rate;

    HistoryEntity(int _id, Timestamp _ts, Date _date, Double _rate) {
        this.id = _id;
        this.ts = _ts;
        this.date = _date;
        this.rate = _rate;
    }
}
