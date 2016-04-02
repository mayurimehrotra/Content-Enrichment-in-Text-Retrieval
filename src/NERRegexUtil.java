
import org.apache.tika.Tika;
import org.apache.tika.config.TikaConfig;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.ner.NamedEntityParser;
import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class NERRegexUtil {

	public static HashMap<String,Set<String>> extractNER(String text) throws Exception{
		//String text = "Hey, Lets meet on this Sunday or MONDAY because i am busy on Saturday,Wednesday,Friday,thursday";
        System.setProperty(NamedEntityParser.SYS_PROP_NER_IMPL, "org.apache.tika.parser.ner.regex.RegexNERecogniser");

        Tika tika = new Tika(new TikaConfig("tika-config.xml"));
        Metadata md = new Metadata();
        tika.parse(new ByteArrayInputStream(text.getBytes(StandardCharsets.UTF_8)), md);

        Set<String> TEMPERATURE = new HashSet<>(Arrays.asList(md.getValues("NER_TEMPERATURE")));
        Set<String> WEIGHT = new HashSet<>(Arrays.asList(md.getValues("NER_WEIGHT")));
        Set<String> TIME = new HashSet<>(Arrays.asList(md.getValues("NER_TIME")));
        Set<String> DERIVED_UNITS = new HashSet<>(Arrays.asList(md.getValues("NER_DERIVED_UNITS")));
        Set<String> HEIGHT = new HashSet<>(Arrays.asList(md.getValues("NER_HEIGHT")));
        Set<String> EARTH_REALM = new HashSet<>(Arrays.asList(md.getValues("NER_EARTH_REALM")));
        Set<String> NONLIVING_SUBSTANCES = new HashSet<>(Arrays.asList(md.getValues("NER_NONLIVING_SUBSTANCES")));
        Set<String> PHYSICAL_PROCESSES = new HashSet<>(Arrays.asList(md.getValues("NER_PHYSICAL_PROCESSES")));
        Set<String> PHYSICAL_PROPERTIES = new HashSet<>(Arrays.asList(md.getValues("NER_PHYSICAL_PROPERTIES")));
        Set<String> LIVING_SUBSTANCES = new HashSet<>(Arrays.asList(md.getValues("NER_LIVING_SUBSTANCES")));
        
        HashMap<String,Set<String>> setEntities = new HashMap<String,Set<String>>();
        setEntities.put("TEMPERATURE", TEMPERATURE);
        setEntities.put("WEIGHT", WEIGHT);
        setEntities.put("TIME", TIME);
        setEntities.put("DERIVED_UNITS", DERIVED_UNITS);
        setEntities.put("HEIGHT", HEIGHT);
        setEntities.put("EARTH_REALM", EARTH_REALM);
        setEntities.put("NONLIVING_SUBSTANCES", NONLIVING_SUBSTANCES);
        setEntities.put("PHYSICAL_PROCESSES", PHYSICAL_PROCESSES);
        setEntities.put("PHYSICAL_PROPERTIES", PHYSICAL_PROPERTIES);
        setEntities.put("LIVING_SUBSTANCES", LIVING_SUBSTANCES);
        
        return setEntities;
		
	}
	public static void main(String[] args)throws Exception {

	}

}

