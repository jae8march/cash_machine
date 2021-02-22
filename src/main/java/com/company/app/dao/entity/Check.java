package com.company.app.dao.entity;

import com.company.app.dao.entity.enumeration.CheckStatus;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;

/**
 * Check entity with getter and setter.
 */
public class Check implements Serializable {
    static final long serialVersionUID = 1L;

    private long checkId;
    private Timestamp checkDate;
    private User user;
    private CheckStatus checkStatus;
    private double checkPrice;
    private List<Product> products;
    private Report report;

    public Check() {
    }

    public Check(long checkId, Timestamp checkDate, User user, CheckStatus checkStatus,
                 double checkPrice, List<Product> products, Report report) {
        this.checkId = checkId;
        this.checkDate = checkDate;
        this.user = user;
        this.checkStatus = checkStatus;
        this.checkPrice = checkPrice;
        this.products = products;
        this.report = report;
    }

    /** Getters */
    public long getCheckId() {
        return checkId;
    }

    public Timestamp getCheckDate() {
        return checkDate;
    }

    public User getUser() {
        return user;
    }

    public CheckStatus getCheckStatus() {
        return checkStatus;
    }

    public double getCheckPrice() {
        return checkPrice;
    }

    public List<Product> getProducts() {
        return products;
    }

    public Report getReport() {
        return report;
    }

    /** Setters */
    public void setCheckId(long checkId) {
        this.checkId = checkId;
    }

    public void setCheckDate(Timestamp checkDate) {
        this.checkDate = checkDate;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setCheckStatus(CheckStatus checkStatus) {
        this.checkStatus = checkStatus;
    }

    public void setCheckPrice(double checkPrice) {
        this.checkPrice = checkPrice;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public void setReport(Report report) {
        this.report = report;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Check)) return false;
        Check check = (Check) o;
        return checkId == check.checkId
                && Double.compare(check.checkPrice, checkPrice) == 0
                && Objects.equals(checkDate, check.checkDate)
                && Objects.equals(user, check.user)
                && checkStatus == check.checkStatus
                && Objects.equals(products, check.products)
                && Objects.equals(report, check.report);
    }

    @Override
    public int hashCode() {
        double result = user.hashCode();
        result = 31 * result + checkId + checkPrice;
        return (int) result;
    }
    @Override
    public String toString() {
        return "Check{" +
                "checkId=" + checkId +
                ", checkDate=" + checkDate +
                ", user=" + user +
                ", checkStatus=" + checkStatus +
                ", checkPrice=" + checkPrice +
                ", products=" + products +
                ", report=" + report +
                '}';
    }
}
