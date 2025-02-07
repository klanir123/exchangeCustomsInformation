package com.msg.customs.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.msg.customs.service.UnionMemebershipService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class UnionMembershipController {

	private final UnionMemebershipService unionMemebershipService;
	
	
	@GetMapping("/union/membership/{country}")
	private boolean checkUnionMembership(@PathVariable String country) {
		return unionMemebershipService.checkUnionMembership(country);
	}
	
}
