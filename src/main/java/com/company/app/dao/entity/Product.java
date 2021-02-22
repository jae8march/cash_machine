package com.company.app.dao.entity;

import java.io.Serializable;
import java.util.Objects;

/**
 * Product entity with getter and setter.
 */
public class Product implements Serializable {
    static final long serialVersionUID = 1L;

    private long code;
    private String name;
    private double price;
    private long quantity;
    private double weight;
    private boolean weightSold;
    private Check check;

    public Product() {
    }

    public Product(long code, String name, double price, long quantity,
                   double weight, boolean weightSold) {
        this.code = code;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.weight = weight;
        this.weightSold = weightSold;
    }

    public Product(int code, String name, double price, long quantity,
                   double weight, boolean weightSold, Check check) {
        this.code = code;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.weight = weight;
        this.weightSold = weightSold;
        this.check = check;
    }

    /** Getters */
    public long getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public double getWeight() {
        return weight;
    }

    public long getQuantity() {
        return quantity;
    }

    public boolean isWeightSold() {
        return weightSold;
    }

    public Check getCheck() {
        return check;
    }

    /** Setters */
    public void setCode(long code) {
        this.code = code;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public void setQuantity(long quantity) {
        this.quantity = quantity;
    }

    public void setWeightSold(boolean weightSold) {
        this.weightSold = weightSold;
    }

    public void setCheck(Check check) {
        this.check = check;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Product)) return false;
        Product product = (Product) o;
        return Objects.equals(code, product.code) &&
                Objects.equals(name, product.name) &&
                Objects.equals(price, product.price) &&
                Objects.equals(weight, product.weight) &&
                Objects.equals(quantity, product.quantity) &&
                Objects.equals(weightSold, product.weightSold) &&
                Objects.equals(check, product.check);
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = (int) (31 * result + price + quantity);
        return result;
    }

    @Override
    public String toString() {
        return "Product{" +
                "code=" + code +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", weight=" + weight +
                ", quantity=" + quantity +
                ", weightSold=" + weightSold +
                '}';
    }
}
