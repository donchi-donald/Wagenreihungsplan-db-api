package api.wagenreihungsplandb.model;



import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import java.util.List;

public class Track {

    private List<Train> trains ;
    @XmlElementWrapper(name = "trains")
    @XmlElement(name = "train")
    public List<Train> getTrains() {
        return trains;
    }

    @Override
    public String toString() {
        return "Track{" +
                "trains=" + trains +
                '}';
    }
}
