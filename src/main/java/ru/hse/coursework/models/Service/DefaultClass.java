package ru.hse.coursework.models.Service;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.node.ObjectNode;

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

    /*
    public ObjectNode getJSONNode()
    {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode Node = mapper.createObjectNode();
        Node.put("token",token);
        Node.put("operationOutput",operationOutput.toString());
        return Node;
    }
    */

    public String getJSON()
    {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode Node = mapper.createObjectNode();
        Node.put("token",token);
        Node.put("operationOutput",operationOutput.toString());
        try {
            return mapper.writeValueAsString(Node);
        }catch (Exception ex)
        {
            return null;
        }
    }

    @Override
    public String toString() {
        return  "{\"defaultClass\":" + this.getJSON() + "}";
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
