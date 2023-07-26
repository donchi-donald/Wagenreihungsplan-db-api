package api.wagenreihungsplandb.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import java.util.List;


public class Waggon {

    private Integer number;
    private List<String> sections;

    @XmlElement(name = "number")
    public Integer getNumber() {
        return number;
    }
    @XmlElementWrapper(name = "sections")
    @XmlElement(name = "identifier")
    public List<String> getSections() {
        return sections;
    }

    @Override
    public String toString() {
        return "Waggon{" +
                "number=" + number +
                ", sections=" + sections +
                '}';
    }
}
