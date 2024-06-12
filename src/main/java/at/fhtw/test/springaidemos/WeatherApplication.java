package at.fhtw.test.springaidemos;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class WeatherApplication {

	public static void main(String[] args) {
		SpringApplication.run(WeatherApplication.class, args);
	}

	@Bean
	CommandLineRunner contextClr(ChatClient chatClient) {
		return args -> {
			String answer = chatClient.prompt()
					.system("""
							Current Weather
							10:48 AM
							13 °C
							Cloudy with some rain
							RealFeel 11 °C
							Wind Gusts 29 km/h
							Humidity 78 %
							Dew Point -1 °C
							Pressure 1011 mb
							Cloud Cover 62 %
							Visibility 12 km
							Cloud Ceiling 10200 m""")
					.user("What is the today's temperature?")
					.call()
					.content();
			System.out.printf("\nChatGPT answered: \n\n%s\n", answer);
		};
	}
}
