package io.seanapse.clients.jms.services.clients.cmd.api;

import io.seanapse.clients.jms.services.clients.core.configuration.AxonConfig;
import io.seanapse.clients.jms.services.clients.core.configuration.MongoConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import({AxonConfig.class, MongoConfig.class})
public class ClientsCommandApplication {

	public static void main(String[] args) {
		SpringApplication.run(ClientsCommandApplication.class, args);
	}

}
