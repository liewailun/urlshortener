package com.projetinhos.urlshortener.dtos;

import jakarta.validation.constraints.NotBlank;

public record UrlRequestRecord(@NotBlank String urlString) {}
