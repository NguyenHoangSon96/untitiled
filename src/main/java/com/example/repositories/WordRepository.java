package com.example.repositories;

import com.example.entities.Word;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WordRepository extends CrudRepository<Word, Long> {

    public Word findFirstByNoun(String noun);


    @Query(value = "TRUNCATE words RESTART IDENTITY", nativeQuery = true)
    public void truncateAll();
}
