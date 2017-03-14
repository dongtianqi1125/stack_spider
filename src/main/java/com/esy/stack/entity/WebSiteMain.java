package com.esy.stack.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class WebSiteMain {
    private Integer id;

    private String webName;

    private String webChar;

    private String webAlias;

    private String webMainUrl;

    private String className;
}