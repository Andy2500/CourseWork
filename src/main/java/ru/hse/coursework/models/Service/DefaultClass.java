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

    public Boolean getOperationOutput() {
        return operationOutput;
    }

    public void setOperationOutput(Boolean operationOutput) {
        this.operationOutput = operationOutput;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
