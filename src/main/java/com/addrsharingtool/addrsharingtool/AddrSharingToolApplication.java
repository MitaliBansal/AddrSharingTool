package com.addrsharingtool.addrsharingtool;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import lombok.extern.log4j.Log4j2;

@Log4j2
@SpringBootApplication
public class AddrSharingToolApplication {

	public static void main(String[] args) {
		try {
			SpringApplication.run(AddrSharingToolApplication.class, args);
		} catch(Exception ex) {
			log.error("Error in running AddrSharingToolApplication: {}", ex.getMessage());
		}
	}

}