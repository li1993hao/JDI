package com.tiptimes.tp.common;

/**
 * @author haoli
 */
public class Message {
    public String message;
    public int code;
    
    public Message(String message, int code){
    	this.message = message;
    	this.code = code;
    }
    
    static Message obtainMessage(String message){
    	return new Message(message, 0);
    }
    
    static Message obtainMessage(String message, int code){
    	return new Message(message, code);
    	
    }
}
