package com.example.entities;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@Table(name = "words")
public class Word extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public Long id;

    @Column(name = "code")
    public String code;

    @Column(name = "noun")
    public String noun;

    @Column(name = "verb")
    public String verb;

    @Column(name = "adjective")
    public String adjective;

    @Column(name = "adverb")
    public String adverb;

    @Column(name = "status")
    public Boolean status;
}
