package com.example.dto;

import lombok.Data;

@Data
public class WordDTO {

    public String word;
    public String code;
    public String noun;
    public String verb;
    public String adjective;
    public String adverb;

    public String definition;

    public Boolean status;
}
