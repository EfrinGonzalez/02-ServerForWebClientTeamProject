package dk.edu.itu.serialization;

import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "cal")
public class Cal {
	  @XmlElementWrapper(name = "tasks")
	  @XmlElement(name = "task")	 
	  public List<Task> tasks;
	  

}
