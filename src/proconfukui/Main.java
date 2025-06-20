package proconfukui;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;

public class Main {
	static Scanner scanner = new Scanner(System.in);
	static final String path = "/Users/utautage/output.json"; // 環境に応じて書き換える
	public static void main(String[] args) throws IOException {
		int size = 0;
		do {
			System.out.print("フィールドのサイズ(4~24の偶数)? ");
			String input = scanner.next();
			try {
				size = Integer.parseInt(input);
			} catch (NumberFormatException e) {}
		} while (size % 2 != 0 || size < 4 || size > 24);
		ArrayList<Integer> entities = new ArrayList<Integer>(size * size);
		for (int number = 1; number <= size * size / 2; number++) {
			entities.add(number);
			entities.add(number);
		}
		Collections.shuffle(entities);
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
				generator.writeNumber(entities.get(y * size + x));
			}
			generator.writeEndArray();
		}
		generator.writeEndArray();
		generator.writeEndObject();
		generator.writeEndObject();
		generator.writeEndObject();
		generator.flush();
		System.out.println(path + " に書き込み完了");
	}
}
