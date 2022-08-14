package ggc.exceptions;

public class SamePartnerKeyException extends Exception {

    private final String _id;

    public SamePartnerKeyException(String id){
        _id = id;
    }

    public String getId(){
        return _id;
    }

}
