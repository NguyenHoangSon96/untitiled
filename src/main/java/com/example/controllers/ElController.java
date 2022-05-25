package com.example.controllers;

import com.example.dto.WordDTO;
import com.example.services.ElService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/el")
public class ElController {

    final ElService elService;

    @Autowired
    public ElController(ElService elService) {
        this.elService = elService;
    }

    @CrossOrigin
    @PostMapping("/add-word")
    public String addWord(@RequestBody WordDTO wordDTO) throws Exception {
        elService.addWord(wordDTO);
        elService.checkWord(wordDTO);
        return "ok";
    }
}
