package com.im.imparty.websocket;


import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import javax.websocket.Session;
import java.io.IOException;
import java.util.List;

@Getter
@Slf4j
public class WebsocketSessionImpl implements WebsocketSession {

    private Session session;

    private String userName;

    private List<String> roleList;

    private WebsocketSessionManager sessionManager;

    public WebsocketSessionImpl(Session session, String userName) {
        this.session = session;
        this.userName = userName;
    }

    public WebsocketSessionImpl(Session session, String userName, List<String> roleList) {
        this.session = session;
        this.userName = userName;
        this.roleList = roleList;
    }

    public void setRoleList(List<String> roleList) {
        this.roleList = roleList;
    }

    public WebsocketSessionImpl(Session session, String userName, WebsocketSessionManager sessionManager) {
        this.session = session;
        this.userName = userName;
        this.sessionManager = sessionManager;
    }

    @Override
    public void sendMessage(String msg) throws IOException {
        session.getBasicRemote().sendText(msg);
    }

    @Override
    public void close() {
        try {
            session.close();
        } catch (IOException e) {
            log.error("关闭session失败");
        }
    }
}