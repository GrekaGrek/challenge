package com.coding.challenge.controller;

import com.coding.challenge.service.ShortenedUrlService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;


@RestController
@RequestMapping("/url")
class ShortenedUrlController {

    private final ShortenedUrlService urlService;

    public ShortenedUrlController(ShortenedUrlService urlService) {
        this.urlService = urlService;
    }

    @PostMapping("/shorten")
    public Mono<ResponseEntity<String>> shortenUrl(@RequestBody String originalUrl) {
        return urlService.shortenUrl(originalUrl)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @GetMapping("/resolve/{shortenedUrl}")
    public Mono<ResponseEntity<String>> resolveUrl(@PathVariable String shortenedUrl) {
        return urlService.resolveUrl(shortenedUrl)
                .map(resolvedUrl ->
                        ResponseEntity.status(HttpStatus.FOUND).body(resolvedUrl))
                .defaultIfEmpty(ResponseEntity.status(HttpStatus.NOT_FOUND).body("Shortened URL not found"));
    }
}
