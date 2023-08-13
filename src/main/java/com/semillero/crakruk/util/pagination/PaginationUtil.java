package com.semillero.crakruk.util.pagination;

import com.semillero.crakruk.dto.PaginationUrlDto;

public class PaginationUtil {
    public static int resolvePageNumber(Integer page) {
        if (page == null) return 0;
        if (page < 1) return 0;
        return page - 1;
    }

    public static PaginationUrlDto getPreviousAndNextPage(int page, int maximumPageNumber) {
        if (page == 0 && maximumPageNumber == 0) {
            return new PaginationUrlDto(null, getPaginationUrl(1),page+1,maximumPageNumber+1);
        }
        if (page == 0) {
            return new PaginationUrlDto(null, getPaginationUrl(2),page+1,maximumPageNumber+1);
        }
        if(page == maximumPageNumber) {
            return new PaginationUrlDto(
                    getPaginationUrl(maximumPageNumber),
                    null
                    ,page+1,maximumPageNumber+1
            );
        }
        return new PaginationUrlDto(getPaginationUrl(page), getPaginationUrl(page + 2),page+1,maximumPageNumber+1);
    }

   /* public static PaginationUrlDto getPreviousAndNextPage(int page, int maximumPageNumber, String route) {
        if (page == 0 && maximumPageNumber == 0) {
            return new PaginationUrlDto(getPaginationUrl(page,route), getPaginationUrl(1,route));
        }
        if (page == 0) {
            return new PaginationUrlDto(getPaginationUrl(page,route), getPaginationUrl(2,route));
        }
        if(page == maximumPageNumber) {
            return new PaginationUrlDto(
                    getPaginationUrl(maximumPageNumber),
                    getPaginationUrl(maximumPageNumber + 1)
            );
        }
        return new PaginationUrlDto(getPaginationUrl(page,route), getPaginationUrl(page + 2,route));
    }*/

    public static String getPaginationUrl(int page) {
        return "http://localhost:8083/comments?page=" + page;
    }

    public static String getPaginationUrl(int page,String route) {
        return "http://localhost:8083/"+route+"?page=" + page;
    }

}
