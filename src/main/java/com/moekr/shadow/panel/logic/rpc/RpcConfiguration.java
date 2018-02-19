package com.moekr.shadow.panel.logic.rpc;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@EqualsAndHashCode
@ToString
@Configuration
@ConfigurationProperties("shadow.rpc")
public class RpcConfiguration {
}
