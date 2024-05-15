package com.user.assessment.functional;

import static com.task.assessment.testutils.TestUtils.businessTestFile;
import static com.task.assessment.testutils.TestUtils.currentTest;
import static com.task.assessment.testutils.TestUtils.yakshaAssert;

import java.io.IOException;
import java.util.Set;

import org.junit.jupiter.api.Test;

import com.text.assessment.TextAnalyzer;

public class FunctionalTests {

	@Test
	public void testCalculateDensity() throws IOException {
		String paragraph = "Hello world, hello everyone. Today is a great day to say hello.";
		Set<String> keywords = Set.of("hello", "today");
		double expected = 4.0 / 12.0;
		double actual = TextAnalyzer.calculateDensity(paragraph, keywords);
		System.out.println(expected);
		System.out.println(actual);
		yakshaAssert(currentTest(), expected == actual ? "true" : "false", businessTestFile);
	}
}
