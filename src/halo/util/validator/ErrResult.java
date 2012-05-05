package halo.util.validator;

public class ErrResult {

    private String name;

    private String msg;

    public ErrResult(String name, String msg) {
        super();
        this.name = name;
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    public String getName() {
        return name;
    }
}