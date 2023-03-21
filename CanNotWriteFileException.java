package edu.najah.cap.ex;

public class CanNotWriteFileException extends  Exception{
    public CanNotWriteFileException(String errorMessage) {
        super(errorMessage);
    }
}
