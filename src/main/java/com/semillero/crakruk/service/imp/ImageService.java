package com.semillero.crakruk.service.imp;

import com.semillero.crakruk.model.Image;
import com.semillero.crakruk.repository.ImageRepository;
import com.semillero.crakruk.service.IImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Service
public class ImageService implements IImageService {

    @Autowired
    private ImageRepository repository;

    @Override
    public Image uploadImage(MultipartFile file) throws IOException {

        if (file == null){
            return null;
        }

        Image image = new Image(file);
        repository.save(image);

        return image;
    }

    @Override
    public Boolean deleteImage(Long id){
        Optional<Image> image = repository.findById(id);

        if (image.isEmpty()){
            return false;
        }

        repository.delete(image.get());
        return true;
    }
}
