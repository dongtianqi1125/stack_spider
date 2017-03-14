package com.esy.stack.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ArticleWithBLOBs extends Article {
    private String content;

    private String htmlContent;
    
}