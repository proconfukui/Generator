package proconfukui;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;

public class Main {
	static Scanner scanner = new Scanner(System.in);
	static final String path = "/Users/utautage/problem.json"; // 環境に応じて書き換える
	
	public static void main(String[] args) throws IOException {
		int size = 0;
		do {
			System.out.print("フィールドのサイズ(4~24の偶数)? ");
			String input = scanner.next();
			try {
				size = Integer.parseInt(input);
			} catch (NumberFormatException e) {}
		} while (size % 2 != 0 || size < 4 || size > 24);
		int[] entities = new int[size * size];
		for (int index = 0; index < size * size; index += 2) {
			entities[index] = index / 2 + 1;
			entities[index + 1] = index / 2 + 1;
		}
		for (int index1 = 0; index1 < size * size; index1++) {
			int index2 = (int)(Math.random() * size * size);
			int temp = entities[index1];
			entities[index1] = entities[index2];
			entities[index2] = temp;
		}
		JsonGenerator generator;
		generator = new JsonFactory().createGenerator(new File(path), JsonEncoding.UTF8);
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
			generator.writeArray(entities, y * size, size);
		}
		generator.writeEndArray();
		generator.writeEndObject();
		generator.writeEndObject();
		generator.writeEndObject();
		generator.flush();
		System.out.println(path + " に書き込み完了");
	}
}
