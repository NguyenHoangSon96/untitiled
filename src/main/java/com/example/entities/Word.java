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
    @Column(name = "word_id")
    public Long wordId;

    @Column(name = "noun")
    public String noun;

    @Column(name = "verb")
    public String verb;

    @Column(name = "adjective")
    public String adjective;

    @Column(name = "adverb")
    public String adverb;

}
