package com.spring.bugtrackerbe.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class FilterProjectsRequestDTO {

    @NotNull
    @Min(0)
    private Integer pageNumber;
    @NotNull
    @Min(1)
    private Integer pageSize;

    public Integer getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(Integer pageNumber) {
        this.pageNumber = pageNumber;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }
}
