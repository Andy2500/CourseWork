package ru.hse.coursework.models.Service;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@XmlRootElement
public class DefaultClass implements Serializable {


    public void setOperationOutput(boolean operationOutput) {
        this.operationOutput = operationOutput;
    }

    public void setToken(String token) {
        this.token = token;
    }

    private Boolean operationOutput;
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

    public boolean isOperationOutput() {
        return operationOutput;
    }

}
