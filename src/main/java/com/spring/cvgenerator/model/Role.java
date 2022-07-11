package com.spring.cvgenerator.model;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import static com.spring.cvgenerator.util.DbConstants.*;

@Getter
@Setter
@ToString(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Entity(name = ENTITY_ROLE)
@Table(name = TABLE_ROLE, schema = SCHEMA_PUBLIC)
public final class Role extends BaseModel {

    @Id
    @Column(name = ID)
    private Long id;
    @Column(name = ROLE_NAME, unique = TRUE, nullable = FALSE, length = 25)
    private String roleName;
}
