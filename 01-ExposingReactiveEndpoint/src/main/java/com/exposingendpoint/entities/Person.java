package com.exposingendpoint.entities;


import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

@Getter
@Setter
public class Person {

  @Id
  private Long id;
  private String name;
  private Integer age;

}
