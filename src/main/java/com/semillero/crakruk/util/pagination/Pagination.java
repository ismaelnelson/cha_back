package com.semillero.crakruk.util.pagination;

import com.semillero.crakruk.dto.PaginationUrlDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Pagination <T>{

    private Integer pages;

    private Integer currentPage;

    private PaginationUrlDto urls;

    private Page<T> list;
}
