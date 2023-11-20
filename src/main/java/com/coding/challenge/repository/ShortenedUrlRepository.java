package com.coding.challenge.repository;

import com.coding.challenge.domain.ShortenedUrl;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface ShortenedUrlRepository extends ReactiveMongoRepository<ShortenedUrl, String> {
    Mono<ShortenedUrl> findByShortenedUrl(String shortenedUrl);
}
