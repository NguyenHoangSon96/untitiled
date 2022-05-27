package com.example.repositories;

import com.example.entities.Word;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WordRepository extends CrudRepository<Word, Long> {

    public Word findFirstByNoun(String noun);


    @Query(value = "TRUNCATE words RESTART IDENTITY", nativeQuery = true)
    public void truncateAll();

    Optional<Word> findFirstByCode(String code);

    @Query(value = "SELECT w FROM Word w " +
            "WHERE (:noun IS NULL OR w.noun = :noun) " +
            "AND (:verb IS NULL OR w.verb = :verb) " +
            "AND (:adjective IS NULL OR w.adjective = :adjective) " +
            "AND (:adverb IS NULL OR w.adverb = :adverb)")
    Optional<Word> checkWord(String noun, String verb, String adjective, String adverb);

    @Query(value = "SELECT * FROM words WHERE status = TRUE LIMIT 1", nativeQuery = true)
    Word findRandom();

    @Query(value = "SELECT * FROM words WHERE status = :status LIMIT 10", nativeQuery = true)
    List<Word> find10WordsByStatus(Boolean status);

    @Query(value = "UPDATE words SET status = :status WHERE TRUE", nativeQuery = true)
    void updateStatusAll(Boolean status);
}
