package com.text.assessment;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.AbstractMap;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Map;
import java.util.Optional;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;

public class TextAnalyzer {
	public static void main(String[] args) {
		try {
			Path path = Paths.get("src", "main", "java", "com", "text", "assessment", "data.txt");
			System.out.println("Looking for file at: " + path.toAbsolutePath());

			String content = Files.readString(path);

			Scanner scanner = new Scanner(System.in);
			System.out.println("Enter keywords (comma separated):");
			String[] keywords = scanner.nextLine().split(",");
			Set<String> normalizedKeywords = Arrays.stream(keywords).map(String::trim).map(String::toLowerCase)
					.collect(Collectors.toSet());

			Optional<String> bestParagraph = findParagraphWithHighestDensity(content, normalizedKeywords);

			// Output results
			if (bestParagraph.isEmpty()) {
				System.out.println("No paragraph sufficiently matches the keyword criteria.");
			} else {
				System.out.println("Paragraph with the highest keyword density:");
				System.out.println(bestParagraph.get());
			}
		} catch (IOException e) {
			System.out.println("Error reading file: " + e.getMessage());
		}
	}

	public static Optional<String> findParagraphWithHighestDensity(String content, Set<String> keywords) {
		return Arrays.stream(content.split("\\n\\n")) // Split into paragraphs by double newline
				.map(String::strip) // Strip leading and trailing whitespace
				.map(paragraph -> new AbstractMap.SimpleEntry<>(paragraph, calculateDensity(paragraph, keywords)))
				.max(Comparator.comparingDouble(Map.Entry::getValue)).filter(entry -> entry.getValue() > 0)
				.map(Map.Entry::getKey);
	}

	public static double calculateDensity(String paragraph, Set<String> keywords) {
		String[] words = paragraph.toLowerCase().replaceAll("[^a-zA-Z\\s]", "").split("\\s+");
		long keywordCount = Arrays.stream(words).filter(keywords::contains).count();
		return (double) keywordCount / words.length;
	}

}
