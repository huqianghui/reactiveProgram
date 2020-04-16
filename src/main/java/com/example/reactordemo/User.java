package com.example.reactordemo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@AllArgsConstructor
@NoArgsConstructor
@Log4j2
@Getter
@Setter
@Document("users")
public class User {

    @Id
    private Integer id;

    private String name;

    private String teamName;

    private Double salary;


}
