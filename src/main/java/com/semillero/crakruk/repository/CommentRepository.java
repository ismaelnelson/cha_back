package com.semillero.crakruk.repository;

import com.semillero.crakruk.model.Comment;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment,Long> {

    @Query("SELECT c FROM Comment c WHERE c.user.deleted = false AND c.deleted = false")
   List<Comment> findAllActiveUser(Pageable pageable);
    @Query("SELECT c FROM Comment c WHERE c.user.deleted = false AND c.deleted = false ORDER BY c.created DESC ")
    List<Comment> findAllCommentsRecientes(Pageable pageable);

    @Query("SELECT COUNT(c) FROM Comment c WHERE c.user.deleted = false AND c.deleted = false")
    int countComments();

}
