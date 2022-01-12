package ro.zvlad.donate.exceptions;

public class GeneralException extends RuntimeException {
    private int code;
    public GeneralException(String info, int code) {
        super(info);
        this.code=code;
    }
    public int getCode(){
        return code;
    }
}

