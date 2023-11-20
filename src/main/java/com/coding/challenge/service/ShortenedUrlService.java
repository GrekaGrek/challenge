package com.coding.challenge.service;

import reactor.core.publisher.Mono;

public interface ShortenedUrlService {
    Mono<String> shortenUrl(String originalUrl);
    Mono<String> resolveUrl(String shortenedUrl);
}
