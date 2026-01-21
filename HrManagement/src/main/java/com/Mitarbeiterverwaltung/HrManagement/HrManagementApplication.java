package com.Mitarbeiterverwaltung.HrManagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class HrManagementApplication {

	public static void main(String[] args) {
		String datasourceUrl = System.getenv("SPRING_DATASOURCE_URL");
		String datasourceUser = System.getenv("SPRING_DATASOURCE_USER");
		String datasourcePwd = System.getenv("SPRING_DATASOURCE_PWD");

		System.out.println(" ############SPRING_DATASOURCE_URL############ " + datasourceUrl);
		System.out.println(" ############SPRING_DATASOURCE_USER############ " + datasourceUser);
		System.out.println(" ############SPRING_DATASOURCE_PWD############ " + datasourcePwd);
		System.setProperty("spring.datasource.url", datasourceUrl);
		System.setProperty("spring.datasource.username", datasourceUser);
		System.setProperty("spring.datasource.password", datasourcePwd);

		String urlRabbit = System.getenv("SPRING_QUEUE_URL");
		System.out.println(" ##############SPRING_QUEUE_URL############ " + urlRabbit);
		System.setProperty("spring.rabbitmq.host", urlRabbit);
		String vhRabbit = System.getenv("SPRING_QUEUE_VH");
		System.out.println(" ##############SPRING_QUEUE_VH############ " + vhRabbit);
		System.setProperty("spring.rabbitmq.virtual-host", vhRabbit);

		SpringApplication.run(HrManagementApplication.class, args);
	}

}
