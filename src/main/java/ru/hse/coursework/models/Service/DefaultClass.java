package ru.hse.coursework.models.Service;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@XmlRootElement
public class DefaultClass implements Serializable {

    private Boolean operationOutput;
    private String message;

    public DefaultClass() {
    }

    public DefaultClass(boolean operationOutput, String message) {
        this.operationOutput = operationOutput;
        this.message = message;
    }

    public boolean getOperationOutput() {
        return operationOutput;
    }

    public String getToken() {
        return message;
    }

    public boolean isOperationOutput() {
        return operationOutput;
    }

    public void setOperationOutput(boolean operationOutput) {
        this.operationOutput = operationOutput;
    }

    public void setToken(String message) {
        this.message = message;
    }

}
