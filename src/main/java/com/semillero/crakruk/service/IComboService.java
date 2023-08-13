package com.semillero.crakruk.service;



import com.semillero.crakruk.dto.ComboDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface IComboService {

    ComboDto createCombo(ComboDto dto);

    ComboDto uploadImage(MultipartFile image, Long id) throws IOException;

    Boolean deleteImage(Long id);

    ComboDto getById(Long id);

    void deleteCombo(Long id);

    ComboDto updateCombo(Long id, ComboDto dto);

    List<ComboDto> getAll();
}
