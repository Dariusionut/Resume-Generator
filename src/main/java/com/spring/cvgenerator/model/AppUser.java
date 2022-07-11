package com.spring.cvgenerator.model;

import com.spring.cvgenerator.model.util.UserDetails;
import com.spring.cvgenerator.model.util.UserSecurity;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

import static com.spring.cvgenerator.util.DbConstants.*;

@Getter
@Setter
@ToString(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Entity(name = ENTITY_APP_USER)
@SequenceGenerator(name = SEQUENCE_APP_USER, allocationSize = 1)
@Table(name = TABLE_APP_USER, schema = SCHEMA_PUBLIC)
public final class AppUser extends BaseModel {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SEQUENCE_APP_USER)
    @Column(name = ID)
    private Long id;

    @Embedded
    private UserDetails userDetails;

    @Embedded
    private UserSecurity userSecurity;

    @Column(name = VERSION)
    @Version
    private Integer version;


}
