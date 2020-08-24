package com.eximbills.producer;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.JSONObject;
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

	private final SimpleDateFormat SDF = new SimpleDateFormat("HH:mm:ss");

	private static final String HOSTNAME = System.getenv().getOrDefault("HOSTNAME", "unknow").replace("\n", " - ")
			.trim();

	@Value("${TOPIC:knative-demo-topic}")
	private String topic;

	public static void main(String[] args) {
		SpringApplication.run(ProducerApplication.class, args);
	}

	@Autowired
	private KafkaTemplate<String, String> template;

	@RequestMapping("/send/{msg}")
	public void sendMsg(@PathVariable String msg) {
		JSONObject request = new JSONObject().put("message", msg).put("sender", HOSTNAME).put("send-time",
				SDF.format(new Date()));
		this.template.send(topic, request.toString());
	}

}
