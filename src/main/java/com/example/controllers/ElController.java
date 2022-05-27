package com.example.controllers;

import com.example.dto.WordDTO;
import com.example.entities.Word;
import com.example.services.ElService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/el")
public class ElController {

    final ElService elService;

    @Autowired
    public ElController(ElService elService) {
        this.elService = elService;
    }

    @PostMapping("/add-word")
    public ResponseEntity<WordDTO> addWord(@RequestBody WordDTO wordDTO) throws Exception {
        return new ResponseEntity(elService.addWord(wordDTO), HttpStatus.OK);
    }

    @GetMapping("/find-randoms")
    public ResponseEntity<List<Word>> findRandoms() {
        return new ResponseEntity(elService.findRandoms(), HttpStatus.OK);
    }
}
