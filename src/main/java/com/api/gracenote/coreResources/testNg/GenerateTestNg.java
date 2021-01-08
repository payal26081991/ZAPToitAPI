/**
 * 
 */
package com.api.gracenote.coreResources.testNg;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.reflections.Reflections;
import org.reflections.scanners.ResourcesScanner;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.reflections.util.FilterBuilder;
import org.testng.TestNG;
import org.testng.xml.XmlClass;
import org.testng.xml.XmlInclude;
import org.testng.xml.XmlSuite;
import org.testng.xml.XmlTest;

import com.api.gracenote.bo.TestCaseBO;
import com.api.gracenote.constants.ConfigPropertiesConstants;
import com.api.gracenote.coreResources.fileReaders.PropertyReader;

/**
 * @author kunal.ashar
 *
 */
public class GenerateTestNg {

	private static Logger logger = Logger.getLogger(GenerateTestNg.class);

	/**
	 * @description generates dynamic testNg as per suite type provided by user
	 * 
	 */
	@SuppressWarnings({ "deprecation", "rawtypes" })
	public TestNG generateTestNgXmlFile(List<TestCaseBO> testCaseList) {

		// Generating dynamic TestNg file
		TestNG		testNg	= new TestNG();

		// creating .xml suite and naming it with suiteType given in config file
		XmlSuite	suite	= new XmlSuite();
		suite.setName(PropertyReader.getConfigProperty(ConfigPropertiesConstants.SUITE_TYPE) + ".xml");
		int threadCount = Integer.parseInt(PropertyReader.getConfigProperty(ConfigPropertiesConstants.THREAD_COUNT));
		suite.setThreadCount(threadCount);
		suite.setParallel("methods");

		// preparing listeners list
		List<String> listeners = new ArrayList<String>();
		listeners.add(PropertyReader.getConfigProperty(ConfigPropertiesConstants.TESTCASE_LISTENER_LOCATION));

		// adding listener to suite file
		suite.setListeners(listeners);

		// creating.xml test
		XmlTest test = new XmlTest(suite);
		test.setName(PropertyReader.getConfigProperty(ConfigPropertiesConstants.SUITE_TYPE));

		// creating a list of classes containing test methods
		List<XmlClass>	classList	= new ArrayList<XmlClass>();

		// get all classes containing testcases
		Reflections		reflections	= getAllTestcaseClass();
		Set<Class<?>>	allClasses	= reflections.getSubTypesOf(Object.class);

		// iterating over all classes
		for (Class c : allClasses) {

			XmlClass cls = new XmlClass();
			cls.setName(c.getName());

			// getting all methods from a class
			Method[]			allMethods		= c.getDeclaredMethods();

			List<XmlInclude>	includeMethods	= new ArrayList<XmlInclude>();

			// iterating over all methods and matching with methods from testcase excel
			for (Method m : allMethods) {
				String methodName = m.getName();

				for (TestCaseBO s : testCaseList) {
					String testCaseName = s.getTestCaseName();

					// adding methods to execution list if matched
					if (testCaseName.equalsIgnoreCase(methodName)) {
						includeMethods.add(new XmlInclude(methodName));
					}
				}

				// adding method to class
				cls.setIncludedMethods(includeMethods);
			}

			// adding class to class list
			if (includeMethods.size() > 0) {
				classList.add(cls);
			}
		}

		// adding all classes to test
		test.setXmlClasses(classList);

		// creating list of test and adding above test object to it
		List<XmlTest> testList = new ArrayList<XmlTest>();
		testList.add(test);

		// adding testList to suite
		suite.setTests(testList);
		logger.warn("Generated XML File\n" + suite.toXml());

		// creating list of suite and adding above suite to it
		List<XmlSuite> suiteList = new ArrayList<XmlSuite>();
		suiteList.add(suite);

		// adding suiteList to testng object
		testNg.setXmlSuites(suiteList);

		// ignoring testNg default listeners
		testNg.setUseDefaultListeners(false);

		return testNg;
	}

	/**
	 * @description used to find all testcase classes
	 * @return
	 */
	private static Reflections getAllTestcaseClass() {
		Reflections reflections = new Reflections(new ConfigurationBuilder().setScanners(new SubTypesScanner(false), new ResourcesScanner())
		        .setUrls(ClasspathHelper.forClassLoader(ClasspathHelper.classLoaders(new ClassLoader[0])))
		        .filterInputsBy(new FilterBuilder()
		                .include(FilterBuilder.prefix(PropertyReader.getConfigProperty(ConfigPropertiesConstants.TESTCASES_LOCATION)))));
		return reflections;
	}

}
