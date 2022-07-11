package com.spring.cvgenerator.dto.filter;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Data
@ToString(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Component
public class UserFilter extends PageAndSorting {

    private String name;

    private Long genderId;

    private Long roleId;

    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private LocalDate dob;

}
