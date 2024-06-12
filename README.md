# Spring AI Demos
A collection of demo-applications to show the functionallity of AI integration in Spring Boot applications.
Based on the article "Leicht gemacht: Einstieg in KI mit Spring" in javamagazin6.2024

## Pre-Requisites

* Generate a API-KEY on the OpenAI Platform: https://platform.openai.com/api-keys
* Create a application.properties file based on the [application.properties.sample](src/main/resources/application.properties.sample) file, and set your API-key there. 

## Running

Start the corresponding Spring-Boot Application for a specific demo:

- **DevJokeApplication** - Demo for beginners, using just one line as a user prompt (without context).
- **WeatherApplication** - Shows a prompt with providing a context.
- **DocContentSearchApplication** - Locally generates a vector-db of a PDF-document, with similarity search and prompting AI providing the matched result as context.

