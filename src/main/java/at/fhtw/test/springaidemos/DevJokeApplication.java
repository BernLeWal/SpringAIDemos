package at.fhtw.test.springaidemos;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class DevJokeApplication {

	public static void main(String[] args) {
		SpringApplication.run(DevJokeApplication.class, args);
	}

	@Bean
	CommandLineRunner simpleClr(ChatClient chatClient) {
		return args -> {
			String answer = chatClient.prompt()
					.user("Tell me another software-developer joke!")
					.call()
					.content();
			System.out.printf("\nChatGPT answered: \n\n%s\n", answer);
		};
	}
}
