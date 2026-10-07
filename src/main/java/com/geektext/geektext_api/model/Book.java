package com.geektext.geektext_api.model;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "books")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String isbn;
    private String name;
    private String description;
    private BigDecimal price;
    @Column(name = "author_id")
    private Long authorId;
    private String genre;
    private String publisher;
    private Integer yearPublished;
    private Integer copiesSold;

    public Long getId() { return id; }
    public String getIsbn() { return isbn; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public BigDecimal getPrice() { return price; }
    public Long getAuthorId() { return authorId; }
    public String getGenre() { return genre; }
    public String getPublisher() { return publisher; }
    public Integer getYearPublished() { return yearPublished; }
    public Integer getCopiesSold() { return copiesSold; }

    public void setId(Long id) { this.id = id; }
    public void setIsbn(String isbn) { this.isbn = isbn; }
    public void setName(String name) { this.name = name; }
    public void setDescription(String description) { this.description = description; }
    public void setPrice(BigDecimal price) { this.price = price; }
    public void setAuthorId(Long authorId) { this.authorId = authorId; }
    public void setGenre(String genre) { this.genre = genre; }
    public void setPublisher(String publisher) { this.publisher = publisher; }
    public void setYearPublished(Integer yearPublished) { this.yearPublished = yearPublished; }
    public void setCopiesSold(Integer copiesSold) { this.copiesSold = copiesSold; }
}