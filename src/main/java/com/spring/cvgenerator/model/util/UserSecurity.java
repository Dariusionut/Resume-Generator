package com.spring.cvgenerator.model.util;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.spring.cvgenerator.model.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

import static com.spring.cvgenerator.util.DbConstants.*;
import static javax.persistence.CascadeType.MERGE;
import static javax.persistence.CascadeType.PERSIST;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Embeddable
public final class UserSecurity {

    @Column(name = USERNAME, nullable = FALSE, unique = TRUE, length = 25)
    private String username;

    @Column(name = EMAIL, nullable = FALSE, unique = TRUE, length = 100)
    private String email;

    @Column(name = PASSWORD, nullable = FALSE, length = 100)
    private String password;

    @ManyToMany(cascade = {MERGE, PERSIST}, fetch = FetchType.EAGER)
    @JoinTable(name = TABLE_USER_ROLE,
            joinColumns = @JoinColumn(name = USER_ID, nullable = FALSE),
            inverseJoinColumns = @JoinColumn(name = ROLE_ID, nullable = FALSE))
    private List<Role> roles;

    @Column(name = IS_ENABLED, nullable = FALSE)
    private Boolean isEnabled = TRUE;

    @Transient
    @JsonIgnore
    private transient Role role;

}
