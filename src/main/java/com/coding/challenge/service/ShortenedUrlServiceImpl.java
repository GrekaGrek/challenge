package com.coding.challenge.service;

import com.coding.challenge.domain.ShortenedUrl;
import com.coding.challenge.repository.ShortenedUrlRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import reactor.core.publisher.Mono;

import java.util.Base64;

@Service
class ShortenedUrlServiceImpl implements ShortenedUrlService {

    private final ShortenedUrlRepository urlRepository;

    public ShortenedUrlServiceImpl(ShortenedUrlRepository urlRepository) {
        this.urlRepository = urlRepository;
    }

    @Override
    public Mono<String> shortenUrl(String originalUrl) {
        String md5Hash = DigestUtils.md5DigestAsHex(originalUrl.getBytes());
        String base64Encoded = Base64.getEncoder().encodeToString(md5Hash.getBytes());
        String shortUrl = base64Encoded.substring(0, 5);

        ShortenedUrl shortenedUrl = ShortenedUrl.builder()
                .originalUrl(originalUrl)
                .shortenedUrl(shortUrl)
                .build();
        return urlRepository.save(shortenedUrl)
                .map(ShortenedUrl::getShortenedUrl);
    }

    @Override
    public Mono<String> resolveUrl(String shortenedUrl) {
        return urlRepository.findByShortenedUrl(shortenedUrl)
                .map(ShortenedUrl::getOriginalUrl);
    }
}
