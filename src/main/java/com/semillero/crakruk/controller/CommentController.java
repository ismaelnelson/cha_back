package com.semillero.crakruk.controller;

import com.semillero.crakruk.auth.model.UserModel;
import com.semillero.crakruk.auth.repository.UserRepository;
import com.semillero.crakruk.auth.service.IUserService;
import com.semillero.crakruk.dto.CommentDto;
import com.semillero.crakruk.dto.CommentPaginationDto;
import com.semillero.crakruk.exeption.EntityNotFoundException;
import com.semillero.crakruk.service.ICommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/comments")
@CrossOrigin(origins = "*")
public class CommentController {

    @Autowired
    ICommentService service;




    @PostMapping
    public ResponseEntity<CommentDto> createComment(HttpServletRequest request, @Valid @RequestBody(required = true)CommentDto dto){
        return ResponseEntity.status(HttpStatus.CREATED).body(service.createComment(dto,request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommentDto> getCommentById(@PathVariable("id") Long id){
        return ResponseEntity.status(HttpStatus.OK).body(service.getById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComment(@PathVariable("id") Long id){
        service.deleteComment(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateComment(@PathVariable("id") long id,
                                         @Valid @RequestBody(required = true) CommentDto dto) throws EntityNotFoundException {
        try{
            return ResponseEntity.status(HttpStatus.OK).body(service.updateComment(id, dto));
        }catch (EntityNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("data.not.found");
        }

    }

    @GetMapping
    public ResponseEntity<CommentPaginationDto> getAllComments(@RequestParam(required = false) Integer page){
        return ResponseEntity.status(HttpStatus.OK).body(service.getAllComments(page));
    }

    @GetMapping("/popular")
    public ResponseEntity<List<CommentDto>> getPopularComments(){
        return ResponseEntity.status(HttpStatus.OK).body(service.getPopularComments());
    }

    @PostMapping("/{id}")
    public ResponseEntity<Void> likeComment(@PathVariable("id") Long id,HttpServletRequest request){
        service.likeComment(id,request);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
