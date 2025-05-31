package com.chnkmlogt.korona.repository;

import com.chnkmlogt.korona.model.ParsedNews;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ParsedNewsRepository extends MongoRepository<ParsedNews, String> {
    Page<ParsedNews> findAll(Pageable pageable);

}
