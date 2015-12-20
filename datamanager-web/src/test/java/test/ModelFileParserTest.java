package test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cn.edu.tsinghua.cess.modelfile.parser.ModelFileHandler;
import cn.edu.tsinghua.cess.modelfile.parser.ModelFileParser;
import cn.edu.tsinghua.cess.modelfile.parser.handler.impl.MockHandler;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:applicationContext.xml")
public class ModelFileParserTest {
	
	@Test
	public void test() {
		ModelFileHandler handler = new MockHandler();
		ModelFileParser parser = new ModelFileParser();
		parser.setModelFileHandler(handler);
		
		parser.parse("D:/nfs6");
	}

}
