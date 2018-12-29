package com.example.signin.utility;

import com.example.signin.messageInfo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class allMessageInfo {
    private static List<messageInfo> messages = new ArrayList<>();
    private static String classID = "";

    public static void setClassID(String classID) {
        allMessageInfo.classID = classID;
    }

    public static String getClassID() {
        return classID;
    }

    public static List<messageInfo> getMessages() {
        return messages;
    }

    public static void setMessages(List<messageInfo> messages) {
        Collections.sort(messages);
        allMessageInfo.messages = messages;
    }
}
