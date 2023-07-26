package api.wagenreihungsplandb.service;

import api.wagenreihungsplandb.model.Station;
import api.wagenreihungsplandb.model.WagenstandsanzeigerResponse;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Service
public class WagenstandService {
    private static final String XML_FILES_DIRECTORY = "data"; // Pfad zum Verzeichnis mit den XML-Dateien

    public WagenstandsanzeigerResponse getGleisabschnitte(String ril100, int trainNumber, int wagenNumber) {
        File xmlFile = selectXmlFile(ril100);
        if (xmlFile == null || !xmlFile.exists()) {
            return null;
        }
        try {
            Station station = readWagenstandDataFromFile(xmlFile);
            if(station !=null){
                List<String> gleisabschnitte = getGleisabschnitteFromData(station, trainNumber, wagenNumber);
                WagenstandsanzeigerResponse response = new WagenstandsanzeigerResponse();
                response.setSections(gleisabschnitte);
                return response;
            }else{
                return null;
            }

        } catch (JAXBException e) {
            e.printStackTrace();
            return null;
        }
    }

    private File selectXmlFile(String ril100) {
        File xmlFilesDirectory = new File(XML_FILES_DIRECTORY);
        File[] xmlFiles = xmlFilesDirectory.listFiles((dir, name) -> name.startsWith(ril100) && name.endsWith(".xml"));

        if (xmlFiles != null && xmlFiles.length > 0) {
            return xmlFiles[0];
        }

        return null;
    }

    private Station readWagenstandDataFromFile(File xmlFile) throws JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(Station.class);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        return (Station) unmarshaller.unmarshal(xmlFile);
    }

    private List<String> getGleisabschnitteFromData(Station station, int trainNumber, int wagenNumber) {
        List<String> gleisabschnitte = new ArrayList<>();
        station.getTracks().forEach(track -> {
            track.getTrains().forEach(train -> {
                if (train.getTrainNumbers().contains(trainNumber)){
                    train.getWaggons().forEach(waggon -> {
                        if (waggon.getNumber() == wagenNumber) {
                            gleisabschnitte.addAll(waggon.getSections());
                        }
                    });
                }
            });
        });
        return gleisabschnitte;
    }
}
