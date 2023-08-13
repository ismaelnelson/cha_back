package com.semillero.crakruk.controller;
import com.semillero.crakruk.dto.ComboDto;
import com.semillero.crakruk.exeption.EntityNotFoundException;
import com.semillero.crakruk.service.IComboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/combos")
@CrossOrigin(origins = "*")
public class ComboController {

    @Autowired
    IComboService service;

    @PostMapping
    public ResponseEntity<ComboDto> createCombo(@Valid @RequestBody(required = true)ComboDto dto){
        return ResponseEntity.status(HttpStatus.CREATED).body(service.createCombo(dto));
    }

    @PostMapping("/image/{id}")
    public ResponseEntity<ComboDto> uploadImage(@Valid @RequestParam MultipartFile image,
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
    public ResponseEntity<ComboDto> getComboById(@PathVariable("id") Long id){
       return ResponseEntity.status(HttpStatus.OK).body(service.getById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCombo(@PathVariable("id") Long id){
        service.deleteCombo(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<?> updateCombo(@PathVariable("id") long id,
                                        @Valid @RequestBody(required = true) ComboDto dto) throws EntityNotFoundException {
        try{
            return ResponseEntity.status(HttpStatus.OK).body(service.updateCombo(id, dto));
        }catch (EntityNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("data.not.found");
        }

    }

    @GetMapping
    public ResponseEntity<List<ComboDto>> getAllCombos(){
        return ResponseEntity.status(HttpStatus.OK).body(service.getAll());
    }
}
