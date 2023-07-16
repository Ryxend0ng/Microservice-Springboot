package com.ryxen.departmentservice.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/department")
public class DepartmentController {
	@GetMapping
	public String hello() {
		return "hello";
	}
	@GetMapping("/v1")
	public String hello1() {
		return "hello v1";
	}
}
