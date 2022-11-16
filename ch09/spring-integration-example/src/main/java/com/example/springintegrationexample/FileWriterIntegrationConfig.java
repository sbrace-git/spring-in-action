package com.example.springintegrationexample;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.Profile;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.annotation.Transformer;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.channel.MessageChannels;
import org.springframework.integration.file.FileWritingMessageHandler;
import org.springframework.integration.file.dsl.Files;
import org.springframework.integration.file.support.FileExistsMode;
import org.springframework.integration.transformer.GenericTransformer;

import java.io.File;

@Configuration
public class FileWriterIntegrationConfig {

    @Profile("xmlConfig")
    @Configuration
    @ImportResource("classpath:/filewriter-config.xml")
    public static class XmlConfiguration {

    }

    @Profile("javaConfig")
    @Bean
    @Transformer(inputChannel = "textInChannel", outputChannel = "fileWriterChannel")
    public GenericTransformer<String, String> upperCaseTransformer() {
        return String::toUpperCase;
    }

    @Profile("javaConfig")
    @Bean
    @ServiceActivator(inputChannel = "fileWriterChannel")
    public FileWritingMessageHandler fileWriter() {
        FileWritingMessageHandler fileWritingMessageHandler = new FileWritingMessageHandler(new File("D:\\common\\temp\\files"));
        fileWritingMessageHandler.setExpectReply(false);
        fileWritingMessageHandler.setFileExistsMode(FileExistsMode.APPEND);
        fileWritingMessageHandler.setAppendNewLine(true);
        return fileWritingMessageHandler;
    }

    @Profile("javaDsl")
    @Bean
    public IntegrationFlow fileWriterFlow() {
        return IntegrationFlows
                .from(MessageChannels.direct("textInChannel"))
                .<String, String>transform(String::toUpperCase)
                .handle(Files.outboundAdapter(new File("D:\\common\\temp\\files"))
                        .fileExistsMode(FileExistsMode.APPEND)
                        .appendNewLine(true))
                .get();
    }
}
