package com.cssuwbcommunity.csuwbbot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class CsuwbbotApplication {

	public static void main(String[] args) {
		final ApplicationContext context = SpringApplication.run(CsuwbbotApplication.class, args);
	}
}
