package util;

import java.io.Serializable;

public class Request implements Serializable {
    private Action action;
    private Object data;

    public Request(Action action, Object data) {
        this.data = data;
        this.action = action;
    }

    public Request(Action action) {
        this.data = null;
        this.action = action;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public Action getAction() {
        return action;
    }

    public void setAction(Action action) {
        this.action = action;
    }
}
