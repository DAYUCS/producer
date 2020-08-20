package com.eximbills.producer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class ProducerApplication {

	@Value("${topic:knative-demo-topic}")
	private String topic;

	public static void main(String[] args) {
		SpringApplication.run(ProducerApplication.class, args);
	}

	@Autowired
	private KafkaTemplate<String, String> template;

	@RequestMapping("/send/{msg}")
	public void sendMsg(@PathVariable String msg) {
		this.template.send(topic, msg);
	}

}
