/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;

/**
 *
 * @author pupil
 */
@Entity
public class Orders implements Serializable{
    @Id @GeneratedValue(strategy =GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    private Product product;
    @OneToOne
    private Customer customer;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date date;
    private int sold;

    public Orders() {
    }

    public Orders(Product product, Customer customer, Date date, int sold) {
        this.product = product;
        this.customer = customer;
        this.date = date;
        this.sold = sold;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getSold() {
        return sold;
    }

    public void setSold(int sold) {
        this.sold = sold;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 97 * hash + Objects.hashCode(this.id);
        hash = 97 * hash + Objects.hashCode(this.product);
        hash = 97 * hash + Objects.hashCode(this.customer);
        hash = 97 * hash + Objects.hashCode(this.date);
        hash = 97 * hash + this.sold;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Orders other = (Orders) obj;
        if (this.sold != other.sold) {
            return false;
        }
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.product, other.product)) {
            return false;
        }
        if (!Objects.equals(this.customer, other.customer)) {
            return false;
        }
        if (!Objects.equals(this.date, other.date)) {
            return false;
        }
        return true;
    }

    

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Order{");
        sb.append("product=").append(product);
        sb.append(", customer=").append(customer);
        sb.append(", date=").append(date);
        sb.append(", sold=").append(sold);
        sb.append('}');
        return sb.toString();
    }
    
}
