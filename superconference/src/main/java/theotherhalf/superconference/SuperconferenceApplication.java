package theotherhalf.superconference;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication//(exclude = { SecurityAutoConfiguration.class})
public class SuperconferenceApplication {

	public static void main(String[] args)
	{
		SpringApplication.run(SuperconferenceApplication.class, args);
	}

}
