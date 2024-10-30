package com.example.shinwoosystem.controller;

import com.example.shinwoosystem.models.entity.Board;
import com.example.shinwoosystem.dao.repository.BoardRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;


@Controller
public class MainController {
	    @GetMapping("/part1")
	    public String part1() {
	        return "part1";  // "part1"은 src/main/resources/templates/part1.html을 의미
	    }
	    
	    @GetMapping("/part2")
	    public String part2() {
	        return "part2";  // "part1"은 src/main/resources/templates/part1.html을 의미
	    }
	    
	    @GetMapping("/part3")
	    public String part3() {
	        return "part3";  // "part1"은 src/main/resources/templates/part1.html을 의미
	    }
	    
	    @GetMapping("/part4")
	    public String part4() {
	        return "part4";  // "part1"은 src/main/resources/templates/part1.html을 의미
	    }
	    @GetMapping("/part5")
	    public String part5() {
	        return "part5";  // "part1"은 src/main/resources/templates/part1.html을 의미
	    }
	    @GetMapping("/aerospace")
	    public String aerospace() {
	        return "aerospace";  // "part1"은 src/main/resources/templates/part1.html을 의미
	    }
	    @GetMapping("/defense")
	    public String defense() {
	        return "defense";  // "part1"은 src/main/resources/templates/part1.html을 의미
	    }
	    @GetMapping("/design")
	    public String design() {
	        return "design";  // "part1"은 src/main/resources/templates/part1.html을 의미
	    }
	    @GetMapping("/layout")
	    public String layout() {
	        return "layout";  // "part1"은 src/main/resources/templates/part1.html을 의미
	    }
	    @GetMapping("/machine")
	    public String machine() {
	        return "machine";  // "part1"은 src/main/resources/templates/part1.html을 의미
	    }
	    @GetMapping("/precision")
	    public String precision() {
	        return "precision";  // "part1"은 src/main/resources/templates/part1.html을 의미
	    }
	    @GetMapping("/space")
	    public String space() {
	        return "space";  // "part1"은 src/main/resources/templates/part1.html을 의미
	    }
	    

	}

	
	

