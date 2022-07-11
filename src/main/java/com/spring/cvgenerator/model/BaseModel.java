package com.spring.cvgenerator.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.time.LocalDateTime;

import static com.spring.cvgenerator.util.AppConstants.DATETIME_FORMAT;
import static com.spring.cvgenerator.util.DbConstants.DATE_ADDED;

@Getter
@Setter
@ToString
@NoArgsConstructor
@MappedSuperclass
@SuperBuilder
public abstract class BaseModel implements Serializable {

    @Column(name = DATE_ADDED)
    @JsonFormat(pattern = DATETIME_FORMAT)
    protected final LocalDateTime dateAdded = LocalDateTime.now();

    protected abstract Long getId();
}
