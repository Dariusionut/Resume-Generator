package com.spring.cvgenerator.model;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import static com.spring.cvgenerator.util.DbConstants.*;

@Getter
@Setter
@ToString(callSuper = true)
@AllArgsConstructor
@RequiredArgsConstructor
@NoArgsConstructor
@Entity(name = ENTITY_GENDER)
@Table(name = TABLE_GENDER, schema = SCHEMA_PUBLIC)
public final class Gender extends BaseModel {

    @Id
    @Column(name = ID)
    private Long id;

    @Column(name = GENDER_NAME, nullable = FALSE, unique = TRUE, length = 25)
    @NonNull
    private String genderName;
}
