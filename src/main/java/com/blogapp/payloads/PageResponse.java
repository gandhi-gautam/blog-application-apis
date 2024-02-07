package com.blogapp.payloads;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class PageResponse {
    private List<?> content;
    private int pageNumber;
    private int pageSize;
    private long totalElements;
    private long totalPages;
    private boolean lastPage;
}
