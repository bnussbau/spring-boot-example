package at.ac.univie.a0908270.nncloud.db;

import at.ac.univie.a00908270.vinnsl.schema.*;
import org.springframework.data.annotation.Id;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@XmlRootElement
public class Vinnsl {

    @Id
    public String identifier;

    public String comment;

    @XmlElement
    public Description description;

    @XmlElement
    public Definition definition;

    @XmlElement
    public Dataschema data;

    @XmlElement
    public Instanceschema instance;

    @XmlElement
    public Trainingresultschema trainingresult;

    @XmlElement
    public Resultschema result;

    public Vinnsl(String comment) {
        this.comment = comment;
    }

	public Vinnsl() {

	}


    @Override
    public String toString() {
        return String.format(
                "VinnslDefinition[identifier=%s, definition='%s']",
                identifier, definition);
    }

}
