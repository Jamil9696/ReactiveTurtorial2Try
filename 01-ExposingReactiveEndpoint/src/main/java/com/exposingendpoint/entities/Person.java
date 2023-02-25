package com.exposingendpoint.entities;


import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Getter
@Setter
@Table(schema = "public", name = "person")
public class Person {

  @Id
  private Long id;
  private String name;
  private Integer age;

}
