package kom.komowo.xmltestujemytwo;

import kom.komowo.xmltestujemytwo.xml.XMLService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class XmlTestujemyTwoApplication implements CommandLineRunner {

	@Autowired
	private XMLService xmlService;


	public static void main(String[] args) {
		SpringApplication.run(XmlTestujemyTwoApplication.class, args);




	}

	@Override
	public void run(String... args) throws Exception {
		//xmlService.drzewoXml();
	}
}
