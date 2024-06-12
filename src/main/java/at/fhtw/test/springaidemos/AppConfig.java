package at.fhtw.test.springaidemos;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
    @Bean
    public ChatClient createChatClient(ChatModel chatModel) {
        return ChatClient.create(chatModel);
    }
}
