package com.projetinhos.urlshortener.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.projetinhos.urlshortener.dtos.UrlRequestRecord;
import com.projetinhos.urlshortener.dtos.UrlResponseRecord;
import com.projetinhos.urlshortener.models.UrlModel;
import com.projetinhos.urlshortener.services.UrlShortenerService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
public class UrlShortenerController {
    private UrlShortenerService urlShortenerService;

    public UrlShortenerController(UrlShortenerService urlShortenerService) {
        this.urlShortenerService = urlShortenerService;
    }

    @PostMapping("/shorten")
    public ResponseEntity<UrlResponseRecord> generateLink(HttpServletRequest request, @RequestBody @Valid UrlRequestRecord urlRecordDto) {
        UrlModel url = urlShortenerService.saveUrl(urlRecordDto.urlString());
        return ResponseEntity.ok().body(new UrlResponseRecord(request.getRequestURL().toString().replace("shorten", url.getCode())));
    }

    @GetMapping("/{code}")
    public ResponseEntity<UrlResponseRecord> getShortenedLink(HttpServletRequest request, @PathVariable String code) {
        String url = urlShortenerService.getShortenedLink(code);

        if(url == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok().body(new UrlResponseRecord(url));
    }

}
