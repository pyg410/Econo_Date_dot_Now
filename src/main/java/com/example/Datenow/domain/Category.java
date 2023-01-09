package com.example.Datenow.domain;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT) // JSON으로 변환시 한글명이 반환되도록 하기 위해, @JsonFormat 어노테이션을 지정한다.
public enum Category {
    맛집탐방,
    쇼핑,
    힐링실내,
    힐링실외, 
    스포츠,
    테마파크
}
