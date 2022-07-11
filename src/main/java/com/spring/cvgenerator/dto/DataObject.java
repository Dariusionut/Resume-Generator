package com.spring.cvgenerator.dto;

import com.spring.cvgenerator.model.BaseModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class DataObject implements Serializable {

    private List<? extends BaseModel> list;

    private Long totalRecords;

    private Integer recordsPerPage;

    private Integer dataSize;

    protected final Integer firstPage = 1;

    private Integer maxPage;

    private Integer currentPage;

}
