package com.semillero.crakruk.repository;

import com.semillero.crakruk.model.Combo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ComboRepository extends JpaRepository<Combo,Long> {
}
