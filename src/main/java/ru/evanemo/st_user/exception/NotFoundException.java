package ru.evanemo.st_user.exception;


public class NotFoundException extends RuntimeException{
  public final static String USR_BY_EMAIL = "User with email: %s not found";
  public final static String USR_BY_ID = "User with id: %s not found";
  public final static String GROUP_BY_ID = "Group with id: %s not found";
  public NotFoundException(String message){
    super(message);
  }
}
