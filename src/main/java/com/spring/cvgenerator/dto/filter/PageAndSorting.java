package com.spring.cvgenerator.dto.filter;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
public abstract class PageAndSorting implements Serializable {

    protected Integer page = 1;

    protected Integer pageSize = 10;

    protected String order;

    protected Boolean isAsc = true;

}
