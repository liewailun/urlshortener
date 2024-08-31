package com.projetinhos.urlshortener.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.projetinhos.urlshortener.models.UrlModel;

public interface UrlRepository extends JpaRepository<UrlModel, UUID>{
    UrlModel findUrlModelByCode(String code);
    UrlModel findUrlModelByUrl(String url);
}
