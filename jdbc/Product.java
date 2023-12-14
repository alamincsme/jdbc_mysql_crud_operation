package com.jdbc;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Product {
    private Long id ;
    private Long version;
    private String name;
    private String description;
    private BigDecimal price;
    private LocalDateTime dateCreated;
    private LocalDateTime dateLastUpaded;


    public void setId(Long id) {
        this.id = id;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public void setDateCreated(LocalDateTime dateCreated) {
        this.dateCreated = dateCreated;
    }

    public void setDateLastUpaded(LocalDateTime dateLastUpaded) {
        this.dateLastUpaded = dateLastUpaded;
    }

    public Long getId() {
        return id;
    }

    public Long getVersion() {
        return version;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public LocalDateTime getDateCreated() {
        return dateCreated;
    }

    public LocalDateTime getDateLastUpaded() {
        return dateLastUpaded;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", version=" + version +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", dateCreated=" + dateCreated +
                ", dateLastUpaded=" + dateLastUpaded +
                '}';
    }
}
