package com.semillero.crakruk.repository;

import com.semillero.crakruk.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image,Long> {
}
