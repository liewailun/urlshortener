package com.projetinhos.urlshortener.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.projetinhos.urlshortener.models.UrlModel;
import com.projetinhos.urlshortener.repositories.UrlRepository;

public class UrlShortenerServiceTest {
    @Mock
    private UrlRepository urlRepository;

    @InjectMocks
    private UrlShortenerService urlShortenerService;
    
    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Should return already existing record")
    void testGetShortenedLinkAlreadyExists() {
        String urlText = "http://AAAAAA.com";

        UrlModel url = new UrlModel();
        url.setCode("AAAAAA");
        url.setUrl(urlText);
        url.setIdUrl(UUID.randomUUID());

        when(urlRepository.findUrlModelByCode(any())).thenReturn(url);

        String result = urlShortenerService.getShortenedLink("AAAAAA");

        assertEquals(urlText, result);
        verify(urlRepository, times(1)).findUrlModelByCode(any());
    }

    @Test
    @DisplayName("Should return null if link doesnt exist")
    void testGetShortenedLinkDoesntExist() {
        when(urlRepository.findUrlModelByCode(any())).thenReturn(null);
        
        String url = "http://AAAAAA.com";
        String result = urlShortenerService.getShortenedLink(url);

        assertEquals(null, result);
        verify(urlRepository, times(1)).findUrlModelByCode(any());
    }

    @Test
    @DisplayName("Should successfully save and return it")
    void testSaveUrlNew() {
        String urlText = "http://AAAAAA.com";

        UrlModel url = new UrlModel();
        url.setCode("AAAAAA");
        url.setUrl(urlText);
        url.setIdUrl(UUID.randomUUID());

        when(urlRepository.findUrlModelByCode(any())).thenReturn(null);
        when(urlRepository.findUrlModelByUrl(urlText)).thenReturn(null);
        when(urlRepository.save(any())).thenReturn(url);

        UrlModel result = urlShortenerService.saveUrl(urlText);

        assertEquals(urlText, result.getUrl());
        verify(urlRepository, times(1)).findUrlModelByCode(any());
        verify(urlRepository, times(1)).findUrlModelByUrl(urlText);
        verify(urlRepository, times(1)).save(any(UrlModel.class));
    }

    @Test
    @DisplayName("Should return existing")
    void testSaveUrlExists() {
        String urlText = "http://AAAAAA.com";

        UrlModel url = new UrlModel();
        url.setCode("AAAAAA");
        url.setUrl(urlText);
        url.setIdUrl(UUID.randomUUID());

        when(urlRepository.findUrlModelByUrl(urlText)).thenReturn(url);

        UrlModel result = urlShortenerService.saveUrl(urlText);

        assertEquals(urlText, result.getUrl());
        verify(urlRepository, times(1)).findUrlModelByUrl(urlText);
    }
}
