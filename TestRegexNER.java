
import org.apache.tika.Tika;
import org.apache.tika.config.TikaConfig;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.ner.NamedEntityParser;
import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class TestRegexNER {
	public static void main(String[] args)throws Exception {
		String text = "Hey, Lets meet on this Sunday or MONDAY because i am busy on Saturday,Wednesday,Friday,thursday";
        System.setProperty(NamedEntityParser.SYS_PROP_NER_IMPL, "org.apache.tika.parser.ner.regex.RegexNERecogniser");

        Tika tika = new Tika(new TikaConfig("D:\\599 CD\\tika\\tika-ner-resources\\tika-config.xml"));
        Metadata md = new Metadata();
        tika.parse(new ByteArrayInputStream(text.getBytes(StandardCharsets.UTF_8)), md);

        Set<String> days = new HashSet<>(Arrays.asList(md.getValues("NER_WEEK_DAY")));
        System.out.println("Hello");

	}

}
