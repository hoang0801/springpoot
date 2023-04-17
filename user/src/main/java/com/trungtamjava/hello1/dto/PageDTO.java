package com.trungtamjava.hello1.dto;

import lombok.Data;

import java.util.List;
@Data
public class PageDTO<T> {
    private  int TotalPages;
    private long getTotalElements;
    private List<T> contents;

}
;