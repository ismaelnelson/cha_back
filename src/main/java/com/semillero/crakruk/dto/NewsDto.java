package com.semillero.crakruk.dto;

import com.semillero.crakruk.model.Image;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NewsDto {

    private Long id;

    @NotNull(message = "{error.empty_field}")
    private String title;

    @NotNull(message = "{error.empty_field}")
    private List<String> body;

    private Image image;

    private String date;

    private String place;

    private String location;

    private String time;

    private String price;

    private String twich;


}

