package com.spring.cvgenerator.controllers;

import com.spring.cvgenerator.dao.RoleRepository;
import com.spring.cvgenerator.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

public class BaseController {

    @Autowired
    protected Environment env;
    @Autowired
    protected UserService userService;

    @Autowired
    protected RoleRepository roleRepo;
}
