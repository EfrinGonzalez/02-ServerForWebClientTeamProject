package dk.edu.itu.serialization;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "task")
public class Task {

    @XmlAttribute
    public String id;

    @XmlAttribute
    public String name;
    
    @XmlAttribute
    public String date;
    
    public String description;
    public String attendants;
}
