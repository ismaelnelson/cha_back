package com.semillero.crakruk.controller;

import com.semillero.crakruk.dto.ComboDto;
import com.semillero.crakruk.dto.NewsDto;
import com.semillero.crakruk.exeption.EntityNotFoundException;
import com.semillero.crakruk.service.INewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/news")
@CrossOrigin(origins = "*")
public class NewsController {

    @Autowired
    INewsService service;

    @PostMapping
    public ResponseEntity<NewsDto> createNews(@Valid @RequestBody(required = true) NewsDto newsDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(service.createNews(newsDto));
    }

    @PostMapping("/image/{id}")
    public ResponseEntity<NewsDto> uploadImage(@Valid @RequestParam MultipartFile image,
                                                @PathVariable(value = "id") Long id) throws IOException {
        return ResponseEntity.status(HttpStatus.OK).body(service.uploadImage(image,id));
    }

    @DeleteMapping("/image/{id}")
    public ResponseEntity<?> deleteImage(@PathVariable(value = "id") Long id){
        Boolean response = service.deleteImage(id);

        if (response){
            return ResponseEntity.status(HttpStatus.OK).body("");
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("");
    }

    @GetMapping("/{id}")
    public ResponseEntity<NewsDto> getNewsById(@PathVariable("id") Long id){
        return ResponseEntity.status(HttpStatus.OK).body(service.getById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNews(@PathVariable("id") Long id){
        service.deleteNews(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateNews(@PathVariable("id") long id,
                                        @Valid @RequestBody(required = true) NewsDto newsUpdate) throws EntityNotFoundException {
        try{
            return ResponseEntity.status(HttpStatus.OK).body(service.updateNews(id, newsUpdate));
        }catch (EntityNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("data.not.found");
        }

    }

    @GetMapping
    public ResponseEntity<List<NewsDto>> getAllNews(){
        return ResponseEntity.status(HttpStatus.OK).body(service.getAllNews());
    }

}
