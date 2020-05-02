package com.creations.inception.constants;

import com.creations.tools.NetworkConstants;

public interface AppConstants extends NetworkConstants {

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

    interface Request {
        String API_KEY = "apiKey";
        String CHAT_BOT_ID = "chatBotID";
        String EXTERNAL_ID = "externalID";
        String MESSAGE = "message";
        String KEY = "key";
    }

    interface ENDPOINT {
        String AIRSPACE = "airspace";
        String POSTS = "posts";
        String BLOGS = "blogs";
    }

    String URL_BLOGGER = "https://www.googleapis.com/blogger/v3/";
    String API_KEY_BLOGGER = "AIzaSyBcsl4pv7brf-JMiqpb_MyTsA6ggGgzRQ4";
    String BLOG_ID = "4827629343886704615";
    String URL_BLOGGER_GET_POSTS = URL_BLOGGER + ENDPOINT.BLOGS + '/' + BLOG_ID + '/' + ENDPOINT.POSTS + '?' + Request.KEY + '=' + API_KEY_BLOGGER;
    String URL_GET_AIRSPACE = AppConstants.LOCALHOST + AppConstants.ENDPOINT.AIRSPACE;

    String URL_WORD_POOL_HOSTNAME = "http://192.168.0.154:8080";
    String URL_WORD_POOL_ENDPOINT = "/words/valid?word=%s";
    String URL_WORD_BOARD_ENDPOINT = "/boards";
    String URL_WORD_SCORE_ENDPOINT = "/scores/game";
    String URL_WORD_SCORES_ENDPOINT = "/scores";
    String URL_WORD_POOL = URL_WORD_POOL_HOSTNAME + URL_WORD_POOL_ENDPOINT;
    String URL_WORD_BOARD = URL_WORD_POOL_HOSTNAME + URL_WORD_BOARD_ENDPOINT;
    String URL_WORD_SCORE = URL_WORD_POOL_HOSTNAME + URL_WORD_SCORE_ENDPOINT;
    String URL_WORD_SCORES = URL_WORD_POOL_HOSTNAME + URL_WORD_SCORES_ENDPOINT;

    String CHAT_API = "Fetching chatbot reply";
}
