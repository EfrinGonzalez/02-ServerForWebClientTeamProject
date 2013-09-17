package dk.edu.itu.serialization;

import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "cal")
public class Tasks {
	  @XmlElementWrapper(name = "tasks")
	  @XmlElement(name = "task")
	 // @XmlElement(name = "description");
	  public List<Task> tasks;
	  

}
