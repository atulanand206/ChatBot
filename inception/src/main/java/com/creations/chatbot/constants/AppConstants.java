package com.creations.chatbot.constants;

import com.creations.tools.Constants;

public interface AppConstants extends Constants {

    public static final String ChatBotID = "Self123";
    public static final String ChatBotName = "Name";

    public static final String URL_GET_API_CHAT= "http://www.personalityforge.com/api/chat/";
    public static final String LOCALHOST = "http://localhost:9000/";
    public static final String API_KEY = "6nt5d1nJHkqbkphe";
    public static final String CHAT_BOT_ID = "63906";
    public static final String EXTERNAL_ID = "abc";
    public static final String DEFAULT_TAG = "Chat Bot";
    public static final String CONTENT_TYPE = "Content-Type";
    public static final String CONTENT_TYPE_JSON = "application/json";

    String URL_GET_AIRSPACE = AppConstants.LOCALHOST + AppConstants.ENDPOINT.AIRSPACE;

    public static class Request {
        public static final String API_KEY = "apiKey";
        public static final String CHAT_BOT_ID = "chatBotID";
        public static final String EXTERNAL_ID = "externalID";
        public static final String MESSAGE = "message";
    }

    public static class ENDPOINT {
        public static final String AIRSPACE = "airspace";
    }
    public static final String CHAT_API = "Fetching chatbot reply";
}
