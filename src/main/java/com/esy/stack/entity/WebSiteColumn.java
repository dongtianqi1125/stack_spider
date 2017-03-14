package com.esy.stack.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class WebSiteColumn {
    private Integer id;

    private Integer webSiteId;

    private String columnUrl;

    private String columnName;

    private String columnAlias;

    private Integer status;

    private String domainUrl;

    private Integer totalPages;
}