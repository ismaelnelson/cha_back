package com.semillero.crakruk.service.imp;

import com.semillero.crakruk.dto.ComboDto;
import com.semillero.crakruk.exeption.EntityNotFoundException;
import com.semillero.crakruk.mapper.ComboMapper;
import com.semillero.crakruk.model.Combo;
import com.semillero.crakruk.model.Image;
import com.semillero.crakruk.repository.ComboRepository;
import com.semillero.crakruk.service.IComboService;
import com.semillero.crakruk.service.IImageService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class ComboService implements IComboService {

    private final ComboRepository comboRepository;
    private final ComboMapper mapper;
    private final IImageService imageService;

    @Override
    @Transactional
    public ComboDto createCombo(ComboDto dto) {
        Combo combo = mapper.toEntity(dto);
        comboRepository.save(combo);
        return mapper.toDto(combo);
    }

    @Override
    @Transactional
    public ComboDto uploadImage(MultipartFile image,Long id) throws IOException {
        Optional<Combo> combo = comboRepository.findById(id);

        if (combo.isPresent()){

            Image img = imageService.uploadImage(image);
            Combo com = combo.get();
            if (com.getImage() != null){
                Long idImg = com.getImage().getId();
                com.setImage(null);
                imageService.deleteImage(idImg);
            }
            com.setImage(img);
            comboRepository.save(com);
            return mapper.toDto(com);
        }
        return null;
    }

    @Override
    public Boolean deleteImage(Long id) {
        Optional<Combo> combo = comboRepository.findById(id);

        if (combo.isPresent()){
            Combo com = combo.get();
            imageService.deleteImage(com.getImage().getId());
            com.setImage(null);
            comboRepository.save(com);
            return true;

        }
        return false;
    }

    @Override
    public ComboDto getById(Long id) {
        Combo combo = comboRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Combo", "id", id));
        return mapper.toDto(combo);
    }

    @Override
    public void deleteCombo(Long id) {

        if (!comboRepository.existsById(id)){
            throw new EntityNotFoundException("Combo", "id", id);
        }
        comboRepository.deleteById(id);
    }

    public Combo findByID(Long id){
        Optional<Combo> combo = comboRepository.findById(id);
        if(combo.isEmpty())throw new EntityNotFoundException("Combo", "id", id);
        return combo.get();
    }

    @Override
    public ComboDto updateCombo(Long id, ComboDto dto) {
        Combo combo = this.findByID(id);
        mapper.update(combo,dto);
        comboRepository.save(combo);
        return mapper.toDto(combo);
    }

    @Override
    public List<ComboDto> getAll() {
        return mapper.toDtoList(comboRepository.findAll());
    }
}
