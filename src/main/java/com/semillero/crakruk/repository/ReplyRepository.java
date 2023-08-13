package com.semillero.crakruk.repository;

import com.semillero.crakruk.model.Reply;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;

public interface ReplyRepository extends JpaRepository<Reply,Long> {


    List<Reply> findAllByOrderByUpdatedAsc();
}
