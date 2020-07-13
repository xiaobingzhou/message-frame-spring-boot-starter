package com.github.xiaobingzhou.messageframe.autoconfigure;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "sdmp.message-frame")
public class MessageFrameConfigurationProperties {

    private boolean exposeRequest = true;

    public boolean isExposeRequest() {
        return exposeRequest;
    }

    public void setExposeRequest(boolean exposeRequest) {
        this.exposeRequest = exposeRequest;
    }
}
