package ru.brandanalyst.core.model;

import java.sql.Timestamp;
import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: Dmitry Batkovich
 * Date: 10/16/11
 * Time: 7:22 PM
 * general model of one dot
 */
public class SingleDot implements Comparable<SingleDot>{
    private final Date date;
    private final double value;

    public SingleDot(Date date, double value) {
        this.date = date;
        this.value = value;
    }

    public double getValue() {
        return value;
    }

    public Date getDate() {
        return date;
    }

    @Override
    public int compareTo(SingleDot o) {
        return this.getDate().compareTo(o.getDate());
    }
}
