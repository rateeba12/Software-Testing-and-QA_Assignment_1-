package edu.najah.cap.ex;

public class cannotWriteFileException extends  Exception{
    public cannotWriteFileException(String errorMessage) {
        super(errorMessage);
    }
}
