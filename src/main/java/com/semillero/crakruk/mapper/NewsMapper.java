package com.semillero.crakruk.mapper;

import com.semillero.crakruk.dto.NewsDto;
import com.semillero.crakruk.mapper.util.IMapper;
import com.semillero.crakruk.model.News;
import org.springframework.stereotype.Component;

import static com.semillero.crakruk.mapper.util.MapperUtil.streamListNonNull;

import java.util.List;

@Component
public class NewsMapper implements IMapper<News, NewsDto> {
    @Override
    public News toEntity(NewsDto dto) {
        return News.builder()
                .id(dto.getId())
                .title(dto.getTitle())
                .body(dto.getBody())
                .image(dto.getImage())
                .date(dto.getDate())
                .place(dto.getPlace())
                .location(dto.getLocation())
                .time(dto.getTime())
                .price(dto.getPrice())
                .twich(dto.getTwich())
                .deleted(false)
                .build();
    }

    @Override
    public News toEntity(Long id, NewsDto dto) {
        return News.builder()
                .id(id)
                .title(dto.getTitle())
                .body(dto.getBody())
                .image(dto.getImage())
                .date(dto.getDate())
                .place(dto.getPlace())
                .location(dto.getLocation())
                .time(dto.getTime())
                .price(dto.getPrice())
                .twich(dto.getTwich())
                .deleted(false)
                .build();
    }

    @Override
    public NewsDto toDto(News entity) {
        return NewsDto.builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .body(entity.getBody())
                .image(entity.getImage())
                .date(entity.getDate())
                .place(entity.getPlace())
                .location(entity.getLocation())
                .time(entity.getTime())
                .price(entity.getPrice())
                .twich(entity.getTwich())
                .build();
    }

    public void update(News entity, NewsDto dto){
        if (!(dto.getTitle() == null)){
            entity.setTitle(dto.getTitle());
        }
        if (!(dto.getBody().isEmpty())){
            entity.setBody(dto.getBody());
        }
        if (!(dto.getImage() == null)){
            entity.setImage(dto.getImage());
        }
        if (!(dto.getDate() == null)){
            entity.setDate(dto.getDate());
        }
        if (!(dto.getPlace() == null)){
            entity.setPlace(dto.getPlace());
        }
        if (!(dto.getLocation() == null)){
            entity.setLocation(dto.getLocation());
        }
        if (!(dto.getTime() == null)){
            entity.setTime(dto.getTime());
        }
        if(!(dto.getPrice() == null)){
            entity.setPrice(dto.getPrice());
        }
        if (!(dto.getTwich() == null)){
            entity.setTwich(dto.getTwich());
        }

    }

    @Override
    public List<NewsDto> toDtoList(List<News> list) {
        return streamListNonNull(list, this::toDto);
    }
}
