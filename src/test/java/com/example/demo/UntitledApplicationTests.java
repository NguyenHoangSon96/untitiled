package com.example.demo;

import com.example.dto.WordDTO;
import com.example.repositories.WordRepository;
import com.example.services.ElService;
import lombok.SneakyThrows;
import org.junit.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@RunWith(SpringRunner.class)
class UntitledApplicationTests {

	@Autowired
	ElService elService;

	@Autowired
	WordRepository wordRepository;

	@Before
	void init() {
		wordRepository.truncateAll();
	}

	@SneakyThrows
	@Test
	void addWord() {
		var wordDTO = new WordDTO();
		wordDTO.noun = "son";
		elService.addWord(wordDTO);

		var word = wordRepository.findFirstByNoun("son");
		assertEquals(word.noun, "son");
	}

	@SneakyThrows
	@Test
	void checkWord() {
		var wordDTO = new WordDTO();
		wordDTO.noun = "danh tu";
		wordDTO.verb = null;
		wordDTO.adjective = "tinh tu";
		wordDTO.adverb = "trang tu";
		elService.addWord(wordDTO);

	}

}
