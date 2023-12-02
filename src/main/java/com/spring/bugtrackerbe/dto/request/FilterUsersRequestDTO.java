package com.spring.bugtrackerbe.dto.request;

public class FilterUsersRequestDTO extends PaginationRequestDTO {

    private String nameOrEmailPattern = null;
    private Boolean status = null;

    public String getNameOrEmailPattern() {
        return nameOrEmailPattern;
    }

    public void setNameOrEmailPattern(String nameOrEmailPattern) {
        this.nameOrEmailPattern = nameOrEmailPattern;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }
}
