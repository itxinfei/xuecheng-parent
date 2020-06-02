package com.xuecheng.manage_course.controller;

import com.xuecheng.framework.domain.course.ext.CategoryNode;
import com.xuecheng.manage_course.service.CategoryService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * CategoryControllerApi
 */
@RestController
@RequestMapping("/category")
public class CategoryController {

    @Resource
    CategoryService categoryService;

   // @Override
    @GetMapping("/list")
    public CategoryNode findList() {
        return categoryService.findList();
    }
}
