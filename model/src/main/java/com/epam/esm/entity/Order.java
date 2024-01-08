package com.epam.esm.entity;

import jakarta.persistence.*;
import org.hibernate.envers.Audited;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "orders")
@Audited
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;


    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "purchase_time")
    private LocalDateTime purchaseTime;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "gift_certificate_id")
    private GiftCertificate giftCertificate;

    public Order() {
    }

    public Order(long id, BigDecimal price, LocalDateTime purchaseTime, User user, GiftCertificate giftCertificate) {
        this.id = id;
        this.price = price;
        this.purchaseTime = purchaseTime;
        this.user = user;
        this.giftCertificate = giftCertificate;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public LocalDateTime getPurchaseTime() {
        return purchaseTime;
    }

    public void setPurchaseTime(LocalDateTime purchaseTime) {
        this.purchaseTime = purchaseTime;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public GiftCertificate getGiftCertificate() {
        return giftCertificate;
    }

    public void setGiftCertificate(GiftCertificate giftCertificate) {
        this.giftCertificate = giftCertificate;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getPrice() {
        return price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return id == order.id && Objects.equals(price, order.price) && Objects.equals(purchaseTime, order.purchaseTime) && Objects.equals(user, order.user) && Objects.equals(giftCertificate, order.giftCertificate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, price, purchaseTime, user, giftCertificate);
    }
}
