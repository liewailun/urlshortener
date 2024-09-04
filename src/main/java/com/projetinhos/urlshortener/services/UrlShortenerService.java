package com.projetinhos.urlshortener.services;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;

import com.projetinhos.urlshortener.models.UrlModel;
import com.projetinhos.urlshortener.repositories.UrlRepository;

@Service
public class UrlShortenerService {
    private final UrlRepository urlRepository;

    public UrlShortenerService(UrlRepository urlRepository) {
        this.urlRepository = urlRepository;
    }

    public UrlModel saveUrl(String url) {
        // Check if url has already been registered
        UrlModel dbUrl = urlRepository.findUrlModelByUrl(url);

        if(dbUrl == null) {
            // Generate new urlCode
            var urlCode = RandomStringUtils.randomAlphanumeric(8);
            while(urlRepository.findUrlModelByCode(urlCode) != null)
            {
                urlCode = RandomStringUtils.randomAlphanumeric(8);
            }

            UrlModel newUrl = new UrlModel();
            newUrl.setCode(urlCode);
            newUrl.setUrl(url);
            dbUrl = urlRepository.save(newUrl);
        }

        return dbUrl;
    }

    public String getShortenedLink(String code) {
        UrlModel dbUrl = urlRepository.findUrlModelByCode(code);

        if(dbUrl == null)
            return null;

        return dbUrl.getUrl();
    }
}
