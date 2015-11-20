package com.project.controller;

import com.project.service.ProductPersistenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

@Controller
@EnableWebMvc
@ManagedBean
@ViewScoped
@RequestMapping("/")
public class ProductSearchController {
    @Autowired
    ProductPersistenceService productPersistenceService;

}
