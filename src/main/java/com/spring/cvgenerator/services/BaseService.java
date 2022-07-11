package com.spring.cvgenerator.services;

import com.spring.cvgenerator.dao.GenderRepository;
import com.spring.cvgenerator.dao.RoleRepository;
import com.spring.cvgenerator.dao.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

public abstract class BaseService {

    @Autowired
    protected Environment env;
    @Autowired
    protected UserRepository userRepository;
    @Autowired
    protected RoleRepository roleRepo;
    @Autowired
    protected GenderRepository genderRepo;


}
