/*
 * MIT License
 *
 * Copyright (c) 2017 JUAN CALVOPINA M
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 */

/**
 * The persistent class for the env_order database table.
 */
package com.ms.jp.audit.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.envers.Audited;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.ms.jp.audit.dto.OrderDTO;
import com.ms.jp.audit.utils.Utilities;

/**
 * @author juanca <juan.calvopina+dev@gmail.com>
 */
@Entity
@Audited
@Table(name = "env_order")
public class Order implements Serializable {

    private static final long serialVersionUID = -6669777807167682166L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private int orderId;

    @Column(name = "order_status")
    private int orderStatus;

    @Temporal(TemporalType.DATE)
    @Column(name = "sale_date")
    private Date saleDate;

    /**
     * bi-directional many-to-one association to OrderDetail
     * mappedBy must have the same name as object that was defined in OrderDetail entity
     * check the attribute 'private Order order'
     */
    @OneToMany(mappedBy = "order")
    private List<OrderDetail> orderDetails;

    /**
     * bi-directional many-to-one association to Person
     * you can define whatever name that you want to identify the customer
     */
    @JsonIgnore
    @JsonManagedReference
    @ManyToOne()
    @JoinColumn(name = "customer_id", referencedColumnName = "id")
    private Person customer;

    /**
     * bi-directional many-to-one association to Person
     * you can define whatever name that you want to identify the employee
     */
    @JsonManagedReference
    @ManyToOne()
    @JoinColumn(name = "employee_id", referencedColumnName = "id")
    private Person employee;

    public Order() {
    }

    public Order(OrderDTO orderDTO, Person customer, Person employee) {
        this.customer = customer;
        this.employee = employee;
        this.saleDate = Utilities.matchDate(orderDTO.getSaleDate());
        this.orderStatus = orderDTO.getOrderStatus();
    }

    public int getOrderId() {
        return this.orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getOrderStatus() {
        return this.orderStatus;
    }

    public void setOrderStatus(int orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Date getSaleDate() {
        return this.saleDate;
    }

    public void setSaleDate(Date saleDate) {
        this.saleDate = saleDate;
    }

    public List<OrderDetail> getOrderDetails() {
        return this.orderDetails;
    }

    public void setOrderDetails(List<OrderDetail> orderDetails) {
        this.orderDetails = orderDetails;
    }

    public Person getCustomer() {
        return this.customer;
    }

    public void setCustomer(Person customer) {
        this.customer = customer;
    }

    public Person getEmployee() {
        return this.employee;
    }

    public void setEmployee(Person employee) {
        this.employee = employee;
    }

}