package proconfukui;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;

class X {
	int startsAt;
	static class Problem {
		static class Field {
			int size;
			int[][] entities;
		}
	}
}

public class Main {
	static Scanner scanner = new Scanner(System.in);
	public static void main(String[] args) throws IOException {
		int size = 0;
		do {
			System.out.print("フィールドのサイズ(4~24の偶数)? ");
			String input = scanner.next();
			try {
				size = Integer.parseInt(input);
			} catch (NumberFormatException e) {}
		} while (size % 2 != 0 || size < 4 || size > 24);
		System.out.println();
		
		ArrayList<Integer> entities = new ArrayList<Integer>(size * size);
		for (int number = 1; number <= size * size / 2; number++) {
			entities.add(number);
			entities.add(number);
		}
		Collections.shuffle(entities);
		
		String path = "/Users/utautage/output.json"; // 環境に応じて書き換える
		JsonGenerator generator = new JsonFactory().createGenerator(new File(path), JsonEncoding.UTF8);
		generator.useDefaultPrettyPrinter();
		generator.writeStartObject();
		generator.writeNumberField("startsAt", 0);
		generator.writeFieldName("problem");
		generator.writeStartObject();
		generator.writeFieldName("field");
		generator.writeStartObject();
		generator.writeNumberField("size", size);
		generator.writeFieldName("entities");
		generator.writeStartArray();
		for (int y = 0; y < size; y++) {
			generator.writeStartArray();
			for (int x = 0; x < size; x++) {
				int number = entities.get(y * size + x);
				generator.writeNumber(number);
				System.out.print(String.format("% 4d", number));
			}
			generator.writeEndArray();
			System.out.println();
		}
		System.out.println();
		generator.writeEndArray();
		generator.writeEndObject();
		generator.writeEndObject();
		generator.writeEndObject();
		generator.flush();
		System.out.println(path + " に書き込み完了");
	}
}
