package com.micropos.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.webflux.dsl.WebFlux;

@Configuration
public class DeliveryIntegrationFlow {

    private static final String channelName = "deliveryChannel";

    @Bean
    public IntegrationFlow inGateDelivery() {
        return IntegrationFlows.from(WebFlux.inboundGateway("/deliveryIntegration/{orderId}")
                        .requestMapping(m -> m.methods(HttpMethod.GET))
                        .payloadExpression("#pathVariables.orderId"))
                .headerFilter("accept-encoding", false)
                .channel(channelName)
                .get();
    }

    @Bean
    public IntegrationFlow outGateDelivery() {
        return IntegrationFlows.from(channelName)
                .handle(WebFlux.outboundGateway(m -> "http://127.0.0.1:8084/api/delivery/" + m.getPayload())
                        .httpMethod(HttpMethod.GET)
                        .expectedResponseType(String.class))
                .get();
    }
}
