package com.dteodoro.javari.game.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "files")
@Getter
@Setter
public class FileStorageProperties {
private String uploadDir;
}
