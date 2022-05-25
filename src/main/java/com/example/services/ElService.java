package com.example.services;

import com.example.dto.WordDTO;
import com.example.entities.Word;
import com.example.repositories.WordRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;

@Service
public class ElService {

    final WordRepository wordRepository;

    @Autowired
    public ElService(WordRepository wordRepository) {
        this.wordRepository = wordRepository;
    }

    public void addWord(WordDTO wordDTO) throws Exception {
        var word = new Word();
        BeanUtils.copyProperties(wordDTO, word);
        wordRepository.save(word);
    }

    public void checkWord(WordDTO wordDTO) throws IllegalAccessException {
        Field[] fields = wordDTO.getClass().getDeclaredFields();
        for (Field field : fields) {
//            System.out.println(field.getName());
//            System.out.println(field.get(wordDTO));
            if (field.get(wordDTO) == "" || field.get(wordDTO) == null) {
                System.out.println(field.getName());
            } else {
                System.out.println(field.get(wordDTO));
            }
        }
    }

}
