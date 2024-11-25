package ru.evanemo.st_user.exception;

public class AlreadyExistsException extends RuntimeException{
  public AlreadyExistsException(String message){
    super(message);
  }
}
