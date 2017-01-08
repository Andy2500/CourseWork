package ru.hse.coursework.models.Service;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@XmlRootElement
public class DefaultClass implements Serializable {
    private boolean operationOutput;
    private String token;

    public DefaultClass() {
    }

    public DefaultClass(boolean operationOutput, String token) {
        this.operationOutput = operationOutput;
        this.token = token;
    }

    public boolean getOperationOutput() {
        return operationOutput;
    }

    public String getToken() {
        return token;
    }
}
