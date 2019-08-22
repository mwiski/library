package com.kodilla.library.controller;

public class CannotPerformActionException extends Exception {
    public CannotPerformActionException(String message) {
        super(message);
    }
}
