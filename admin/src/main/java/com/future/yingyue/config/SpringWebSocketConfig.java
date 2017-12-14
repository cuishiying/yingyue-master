package com.future.yingyue.config;

import com.future.yingyue.push.HandShake;
import com.future.yingyue.push.MyWebSocketHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebMvc
@EnableWebSocket
public class SpringWebSocketConfig extends WebMvcConfigurerAdapter implements WebSocketConfigurer {


    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(WebSocketPushHandler(), "/ws").addInterceptors(new HandShake());
        registry.addHandler(WebSocketPushHandler(), "/ws/sockjs").addInterceptors(new HandShake()).withSockJS();
    }

    @Bean
    public MyWebSocketHandler WebSocketPushHandler(){
        return new MyWebSocketHandler();
    }
}
