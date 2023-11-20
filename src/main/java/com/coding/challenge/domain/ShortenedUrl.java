package com.coding.challenge.domain;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.index.Indexed;

@Data
@Builder
@Document(collection = "shortened_urls")
public class ShortenedUrl {

    @Id
    private String id;
    private String originalUrl;
    @Indexed(unique = true)
    private String shortenedUrl;
}
