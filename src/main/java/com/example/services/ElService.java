package com.example.services;

import com.example.dto.WordDTO;
import com.example.entities.Word;
import com.example.repositories.WordRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Service
public class ElService {

    final WordRepository wordRepository;

    @Autowired
    public ElService(WordRepository wordRepository) {
        this.wordRepository = wordRepository;
    }

    public Word addWord(WordDTO wordDTO) throws Exception {
        if (wordRepository.findFirstByCode(wordDTO.code).isPresent()) {
            throw new Exception("Word is duplicated");
        }
        var word = new Word();
        BeanUtils.copyProperties(wordDTO, word);
        word.status = false;
        word.code = wordDTO.noun + wordDTO.verb + wordDTO.adjective + wordDTO.adverb;
        return wordRepository.save(word);
    }

    public List<Word> findRandoms() {
        var words = wordRepository.find10WordsByStatus(false);
        if (!CollectionUtils.isEmpty(words)) {
            if (words.size() < 10) wordRepository.updateStatusAll(true);
            return words;
        } else {
            wordRepository.updateStatusAll(true);
            return wordRepository.find10WordsByStatus(false);
        }
    }

}
