package com.rookies3.myspringbootlab.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "book_details")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter @Setter
//Owner(주인)
public class BookDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "book_detail_id")
    private Long id;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private String language;

    @Column(nullable = false)
    private Integer pageCount;

    @Column(nullable = false)
    private String publisher;

    @Column(nullable = false)
    private String coverImageUrl;

    @Column(nullable = false)
    private String edition;

    //1:1
    @OneToOne(fetch = FetchType.LAZY)
    //@JoinColumn은 FK에 해당하는 어노테이션.
    @JoinColumn(name = "book_id", unique = true)
    private Book book;
}