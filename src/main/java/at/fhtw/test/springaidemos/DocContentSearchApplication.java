package at.fhtw.test.springaidemos;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.SystemPromptTemplate;
import org.springframework.ai.document.Document;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.SimpleVectorStore;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.ai.reader.pdf.PagePdfDocumentReader;
import org.springframework.core.io.FileSystemResourceLoader;
import org.springframework.core.io.Resource;

import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

@SpringBootApplication
public class DocContentSearchApplication {

	public static void main(String[] args) {
		SpringApplication.run(DocContentSearchApplication.class, args);
	}

//	@Bean
//	VectorStore vectors(EmbeddingModel embedding,
//						@Value(<your-pdf-file-here>) Resource pdf) {
//		SimpleVectorStore vectors = new SimpleVectorStore(embedding);
//		PagePdfDocumentReader reader = new PagePdfDocumentReader(pdf);
//		TokenTextSplitter splitter = new TokenTextSplitter();
//		List<Document> documents = splitter.apply(reader.get());
//		vectors.accept(documents);
//		return vectors;
//	}

	VectorStore vectors(EmbeddingModel embedding, String pdfFilePath) {
		Resource pdf = new FileSystemResourceLoader().getResource(pdfFilePath);
		SimpleVectorStore vectors = new SimpleVectorStore(embedding);
		PagePdfDocumentReader reader = new PagePdfDocumentReader(pdf);
		TokenTextSplitter splitter = new TokenTextSplitter();
		List<Document> documents = splitter.apply(reader.get());
		vectors.accept(documents);
		return vectors;
	}

	@Bean
	CommandLineRunner vectorsClr(ChatClient chatClient, EmbeddingModel embedding) {
		return args -> {
			Scanner in = new Scanner(System.in);

			System.out.print("Enter the file-path to your PDF-file: ");
			String filePath = in.nextLine();
			VectorStore vectors = vectors(embedding, filePath);

			System.out.print("Enter your question: ");
			String query = in.nextLine(); //"What programming activities are documented?";

			List<Document> documents = vectors.similaritySearch(query);
			String inlined = documents.stream()
					.map(Document::getContent)
					.collect(Collectors.joining(System.lineSeparator()));

			Message system = new SystemPromptTemplate("""
					You are a virtual assistant.
					You answer questions with data provided in the DOCUMENTS section.
					You are only allowed to use information from the DOCUMENTS paragraph and no other information.
					If you are not sure or don't know, honestly say you don't know.
					
					DOCUMENTS:
					{documents}
					""")
					.createMessage(Map.of("documents",inlined));
			UserMessage user = new UserMessage(query);

			String answer = chatClient.prompt()
					.messages(system, user)
					.call()
					.content();
			System.out.printf("\nChatGPT answered: \n\n%s\n", answer);
		};
	}
}
