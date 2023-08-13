package com.semillero.crakruk.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PaginationUrlDto {
    private String previousUrl;
    private String nextUrl;
    Integer currentPage;
    Integer totalPages;
}
