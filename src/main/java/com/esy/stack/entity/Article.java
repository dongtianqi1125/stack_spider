package com.esy.stack.entity;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
@Setter
@Getter
@ToString
public class Article {
    private Long id;

    private Integer columnId;

    private String title;

    private String tag;

    private String summary;

    private String author;

    private String realSource;

    private String keywords;

    private Date publishTime;

    private Date createTime;

    private String url;

    private Integer status;

    private Integer times;
}