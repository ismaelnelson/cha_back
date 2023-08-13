package com.semillero.crakruk.service;

import com.semillero.crakruk.dto.NewsDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface INewsService {

    NewsDto createNews(NewsDto dto);

    NewsDto uploadImage(MultipartFile image, Long id) throws IOException;

    Boolean deleteImage(Long id);

    NewsDto getById(Long id);

    void deleteNews(Long id);

    NewsDto updateNews(Long id, NewsDto dto);

    List<NewsDto> getAllNews();
}
