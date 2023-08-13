package com.semillero.crakruk.service;

import com.semillero.crakruk.model.Image;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface IImageService {

    Image uploadImage(MultipartFile file) throws IOException;

    Boolean deleteImage(Long id);
}
