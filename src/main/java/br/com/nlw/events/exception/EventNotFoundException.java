package br.com.nlw.events.exception;

public class EventNotFoundException extends RuntimeException{

    public EventNotFoundException(String msg){
        super(msg); // invocar o construtor da classe pai
    }

}
