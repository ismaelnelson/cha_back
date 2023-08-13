package com.semillero.crakruk.controller;


import com.semillero.crakruk.dto.ReplyDto;
import com.semillero.crakruk.exeption.EntityNotFoundException;
import com.semillero.crakruk.service.IReplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping("/reply")
@CrossOrigin(origins = "*")
public class ReplyController {

    @Autowired
    private IReplyService service;

    @PostMapping("/{id}")
    public ResponseEntity<ReplyDto> createReply(HttpServletRequest request,@PathVariable("id") Long id, @Valid @RequestBody(required = true)ReplyDto dto){
        return ResponseEntity.status(HttpStatus.CREATED).body(service.createReply(dto,request,id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReply(@PathVariable("id") Long id){
        service.deleteReply(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateReply(@PathVariable("id") long id,
                                           @Valid @RequestBody(required = true) ReplyDto dto) throws EntityNotFoundException {
        try{
            return ResponseEntity.status(HttpStatus.OK).body(service.updateReply(id,dto));
        }catch (EntityNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("data.not.found");
        }

    }

    @PostMapping("/like/{id}")
    public ResponseEntity<Void> likeComment(@PathVariable("id") Long id,HttpServletRequest request){
        service.likeReply(id,request);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
