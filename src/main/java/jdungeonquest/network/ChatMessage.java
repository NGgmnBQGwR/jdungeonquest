package jdungeonquest.network;

import jdungeonquest.enums.NetworkMessageType;

public class ChatMessage extends Message{
    String message;
    String author;

    ChatMessage() {
    }

    public ChatMessage(String text, String clientName) {
        msgType = NetworkMessageType.ChatMessage;
        message = text;
        author = clientName;
    }
}
