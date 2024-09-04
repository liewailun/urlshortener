package com.projetinhos.urlshortener.repositories;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import static org.assertj.core.api.Assertions.assertThat;

import com.projetinhos.urlshortener.models.UrlModel;

import jakarta.persistence.EntityManager;

@DataJpaTest
@ActiveProfiles("test")
public class UrlRepositoryTest {

    @Autowired
    EntityManager em;

    @Autowired
    UrlRepository urlRepository;

    private UrlModel createUrl(String url, String code){
        UrlModel newUrl = new UrlModel();
        newUrl.setUrl(url);
        newUrl.setCode(code);

        em.persist(newUrl);

        return newUrl;
    }

    
    @Test
    @DisplayName("Should find url model by code successfully")
    void testFindUrlModelByCodeSuccess() {
        String url = "http://www.aaaaaa.com";
        String code = "aaaaaa";
        this.createUrl(url, code);

        UrlModel result = urlRepository.findUrlModelByCode(code);

        assertThat(result != null).isTrue();
    }

    @Test
    @DisplayName("Should fail to find url model by code")
    void testFindUrlModelByCodeFailure() {
        String wrongCode = "bbbbbb";

        UrlModel result = urlRepository.findUrlModelByCode(wrongCode);

        assertThat(result == null).isTrue();
    }

    @Test
    @DisplayName("Should find url model by url successfully")
    void testFindUrlModelByUrl() {
        String url = "http://www.aaaaaa.com";
        String code = "aaaaaa";
        this.createUrl(url, code);

        UrlModel result = urlRepository.findUrlModelByUrl(url);

        assertThat(result != null).isTrue();
    }
}
