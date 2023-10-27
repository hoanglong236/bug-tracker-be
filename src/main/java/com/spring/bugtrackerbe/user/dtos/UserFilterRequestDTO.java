package com.spring.bugtrackerbe.user.dtos;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class UserFilterRequestDTO {

    @NotNull
    @Min(0)
    private Integer pageNumber = 0;
    @NotNull
    @Min(1)
    private Integer pageSize = 12;

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
