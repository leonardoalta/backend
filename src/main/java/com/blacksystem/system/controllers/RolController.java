package com.blacksystem.system.controllers;

import com.blacksystem.system.repositorys.users.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/role")
public class RolController {
    @Autowired
    private RoleRepository roleRepository;

}
