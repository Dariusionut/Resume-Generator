package com.spring.cvgenerator.model.util;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.spring.cvgenerator.model.Gender;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.Period;

import static com.spring.cvgenerator.util.AppConstants.DATE_FORMAT;
import static com.spring.cvgenerator.util.DbConstants.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Embeddable
public final class UserDetails {

    @Column(name = FIRST_NAME, nullable = FALSE, length = 25)
    private String firstName;

    @Column(name = LAST_NAME, nullable = FALSE, length = 25)
    private String lastName;
    @Column(name = DOB, nullable = FALSE)
    @JsonFormat(pattern = DATE_FORMAT)
    @DateTimeFormat(pattern = DATE_FORMAT)
    private LocalDate dob;

    @ManyToOne(targetEntity = Gender.class, fetch = FetchType.EAGER)
    @JoinColumn(name = GENDER_ID, nullable = FALSE)
    private Gender gender;

    @Transient
    public Integer getAge() {
        return Period.between(this.dob, LocalDate.now()).getYears();
    }


}
