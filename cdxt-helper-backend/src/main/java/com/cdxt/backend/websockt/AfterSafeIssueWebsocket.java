package com.cdxt.backend.websockt;

import com.alibaba.fastjson.JSONObject;
import com.cdxt.backend.enums.AfsWssMsgTypeEnum;
import com.cdxt.backend.service.UserService;
import com.cdxt.common.enums.ResponseMsgType;
import com.cdxt.common.exception.ResponseCommonException;
import com.cdxt.common.pojo.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RestController;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.atomic.AtomicInteger;

/*
 * @Description: 问题实时数据传输websocket服务
 * @Author: TengGang
 * @Date: 2020/8/27 11:04
 **/
@ServerEndpoint("/wss/afs/issue/{uid}")
@Slf4j
@RestController
public class AfterSafeIssueWebsocket {

    @Autowired
    UserService userService;

    //静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
    private static AtomicInteger onlineNum = new AtomicInteger();
    //concurrent包的线程安全Set，用来存放每个客户端对应的WebSocketServer对象。
    private static ConcurrentHashMap<String, Session> sessionPools = new ConcurrentHashMap<>();

    /**
     * 连接建立成功调用的方法
     */
    @OnOpen
    public void onOpen(@PathParam("uid") String uid, Session session) {
        sessionPools.put(uid, session);
        addOnlineCount();
        JSONObject message = new JSONObject();
        String trueName = userService.getTrueName(uid);
        message.put("type", AfsWssMsgTypeEnum.LOG);
        message.put("data", StringUtils.isEmpty(trueName)? uid: trueName  + "用户进入问题处理系统");
        try {
            sendMessage(message);
        } catch (IOException e) {
            log.error("wss: //wss//afs//issue 建立连接发送消息失败",e);
        }
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose(@PathParam("uid") String uid) {
        sessionPools.remove(uid);
        JSONObject message = new JSONObject();
        message.put("type",AfsWssMsgTypeEnum.LOG);
        String trueName = userService.getTrueName(uid);
        message.put("data",StringUtils.isEmpty(trueName)? uid: trueName + "用户离开问题处理系统");
        try {
            sendMessage(message);
        } catch (IOException e) {
            log.error("wss: //wss//afs//issue 关闭连接发送消息失败",e);
        }
    }

    /**
     * 收到客户端消息后调用的方法
     *
     * @param message 客户端发送过来的消息
     */
    @OnMessage
    public void onMessage(String message) {
        log.info("收到消息" + message);
        System.out.println();
    }

    /**
     * 发生错误时调用
     */
    @OnError
    public void onError(Session session, Throwable throwable) {
        log.error("wss 连接发送错误 ",throwable);
    }

    /**
     * 广播消息
     * @param data
     * @throws IOException
     */
    public void sendMessage(Object data) throws IOException {
        if (!Optional.ofNullable(data).isPresent()){
            return;
        }
        ResultVO  resultVO = new ResultVO();
        resultVO.setCode(HttpStatus.OK.value());
        resultVO.setMsg(ResponseMsgType.OK.msg());
        resultVO.setData(data);
        String message = JSONObject.toJSONString(resultVO);
        //发送消息
        for (Session session : sessionPools.values()){
            if(session != null){
                synchronized (session) {
                    session.getBasicRemote().sendText(message);
                }
            }
        }
    }

    public static void addOnlineCount(){
        onlineNum.incrementAndGet();
    }

    public static void subOnlineCount() {
        onlineNum.decrementAndGet();
    }

}
