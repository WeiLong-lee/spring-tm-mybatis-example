/**
 * 
 */
package com.example.mybatis.util;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.exception.InvalidConfigurationException;
import org.mybatis.generator.exception.XMLParserException;
import org.mybatis.generator.internal.DefaultShellCallback;

/**
 * @author yangliu
 *
 */
public class GenerateMybatisCode {
	
	public static void main(String[] args) {
		
		//
		List<String> warnings = new ArrayList<String>();
		   boolean overwrite = true;
		   //GenMain.class.getResource("generatorConfig.xml").getFile()
		   File configFile = new File(GenerateMybatisCode.class.getResource("/generatorConfig.xml").getFile());
		   ConfigurationParser cp = new ConfigurationParser(warnings);
		   Configuration config;
		try {
			config = cp.parseConfiguration(configFile);
			  DefaultShellCallback callback = new DefaultShellCallback(overwrite);
			   MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, callback, warnings);
			   myBatisGenerator.generate(null);
		} catch (IOException | XMLParserException | InvalidConfigurationException | SQLException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("========================generate the code sucess.==================");
		 
	}

}
