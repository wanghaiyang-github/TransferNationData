package com.bazl.lims.transfer;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.bazl.lims.transfer.dao")
public class TransferNationDataApplication {

	public static void main(String[] args) {
		SpringApplication.run(TransferNationDataApplication.class, args);
	}

}
