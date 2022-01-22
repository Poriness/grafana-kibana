package edu.wanna.apigateway;

import com.netflix.appinfo.ApplicationInfoManager;
import java.util.List;
import java.util.function.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class JwtAuthenticationFilter implements GatewayFilter {

    private static final List<String> openApiEndpoints = List.of(
            "/auth/authenticate",
            "/auth/validate",
            "/user/register"
    );

    @Autowired
    private WebClient client;

    @Autowired
    private ApplicationInfoManager applicationInfoManager;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        if (!isSecured().test(request)) {
            return chain.filter(exchange);
        }

        if (this.isAuthMissing(request))
            return this.onError(exchange, "Authorization header is missing in request", HttpStatus.UNAUTHORIZED);

        final String token = this.getAuthHeader(request);
        return validateToken(token)
                .bodyToMono(String.class).flatMap(response -> {
                    if (!response.contains("Token is valid")) {
                        return this.onError(exchange, "Authorization header is invalid", HttpStatus.UNAUTHORIZED);
                    } else {
                        return chain.filter(exchange);
                    }
                });
    }

    private WebClient.ResponseSpec validateToken(String token) {
    String appURl = applicationInfoManager.getInfo().getHomePageUrl();
        return client.post()
                .uri(appURl +"/auth/validate")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .body(Mono.just(new TokenRequest(token)), TokenRequest.class)
                .retrieve();
    }

    private Mono<Void> onError(ServerWebExchange exchange, String err, HttpStatus httpStatus) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(httpStatus);
        return response.setComplete();
    }

    private String getAuthHeader(ServerHttpRequest request) {
        return request.getHeaders().getOrEmpty("Authorization").get(0);
    }

    private boolean isAuthMissing(ServerHttpRequest request) {
        return !request.getHeaders().containsKey("Authorization");
    }

    private Predicate<ServerHttpRequest> isSecured() {
        return request -> openApiEndpoints
                .stream()
                .noneMatch(uri -> request.getURI().getPath().contains(uri));
    }

}
