package com.coding.challenge.service;

import com.coding.challenge.domain.ShortenedUrl;
import com.coding.challenge.repository.ShortenedUrlRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ShortenedUrlServiceImplTest {

    @Mock
    private ShortenedUrlRepository urlRepository;

    @InjectMocks
    private ShortenedUrlServiceImpl urlService;

    @Test
    void testShortenUrl() {
        String originalUrl = "https://www.example.com";
        String expectedShortUrl = "abcde";

        ShortenedUrl shortenedUrl = ShortenedUrl.builder()
                .originalUrl(originalUrl)
                .shortenedUrl(expectedShortUrl)
                .build();

        when(urlRepository.save(any(ShortenedUrl.class))).thenReturn(Mono.just(shortenedUrl));

        StepVerifier.create(urlService.shortenUrl(originalUrl))
                .expectNext(expectedShortUrl)
                .verifyComplete();

        verify(urlRepository).save(any(ShortenedUrl.class));
    }

    @Test
    void testResolveUrl() {
        String shortenedUrl = "abcde";
        String expectedOriginalUrl = "https://www.example.com";

        ShortenedUrl savedUrl = ShortenedUrl.builder()
                .originalUrl(expectedOriginalUrl)
                .shortenedUrl(shortenedUrl)
                .build();

        when(urlRepository.findByShortenedUrl(shortenedUrl)).thenReturn(Mono.just(savedUrl));

        StepVerifier.create(urlService.resolveUrl(shortenedUrl))
                .expectNext(expectedOriginalUrl)
                .verifyComplete();

        verify(urlRepository).findByShortenedUrl(shortenedUrl);
    }
}