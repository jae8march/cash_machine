package com.company.app.dao.entity;

import com.company.app.dao.entity.enumeration.ReportType;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Objects;

/**
 * Report entity with getter and setter.
 */
public class Report implements Serializable {
    static final long serialVersionUID = 1L;

    private long id;
    private ReportType reportType;
    private Timestamp date;
    private transient double beforeCash;
    private transient double nowCash;
    private transient double totalCash;

    public Report() {
    }

    public Report(long id, ReportType reportType, Timestamp date, double beforeCash,
                  double nowCash, double totalCash) {
        this.id = id;
        this.reportType = reportType;
        this.date = date;
        this.beforeCash = beforeCash;
        this.nowCash = nowCash;
        this.totalCash = totalCash;
    }

    /** Getters */
    public long getId() {
        return id;
    }

    public ReportType getReportType() {
        return reportType;
    }

    public Timestamp getDate() {
        return date;
    }

    public double getBeforeCash() {
        return beforeCash;
    }

    public double getNowCash() {
        return nowCash;
    }

    public double getTotalCash() {
        return totalCash;
    }

    /** Setters */
    public void setId(long id) {
        this.id = id;
    }
    public void setReportType(ReportType reportType) {
        this.reportType = reportType;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public void setBeforeCash(double beforeCash) {
        this.beforeCash = beforeCash;
    }

    public void setNowCash(double nowCash) {
        this.nowCash = nowCash;
    }

    public void setTotalCash(double totalCash) {
        this.totalCash = totalCash;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Report)) return false;
        Report report = (Report) o;
        return id == report.id &&
                beforeCash == report.beforeCash &&
                nowCash == report.nowCash &&
                totalCash == report.totalCash &&
                reportType == report.reportType &&
                Objects.equals(date, report.date);
    }

    @Override
    public int hashCode() {
        double result = date.hashCode();
        result = 31 * result + beforeCash + totalCash + nowCash;
        return (int) result;
    }

    @Override
    public String toString() {
        return "Report{" +
                "id=" + id +
                ", reportType=" + reportType +
                ", date=" + date +
                ", beforeCash=" + beforeCash +
                ", nowCash=" + nowCash +
                ", totalCash=" + totalCash +
                '}';
    }
}
