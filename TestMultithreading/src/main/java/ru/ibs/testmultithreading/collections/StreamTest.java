package ru.ibs.testmultithreading.collections;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.IntStream;

public class StreamTest {

	public void testStream() {
		for (int i = 0; i < 100000; i++) {
			Set<Object> set = Collections.synchronizedSet(new HashSet<>());
			int sum = IntStream.of(1, 5, 6, 4, 3, 2, 1, 1, 2, 5, 6, 4, 3, 2, 3, 2, 3, 7, 8, 7, 6, 8, 5, 4, 3, 2, 2, 2, 3, 4, 5, 6, 7, 8).parallel().map(d -> set.add(d) ? d : 0).sum();
			if (sum != 36) {
				System.out.println("sum=" + sum + "!");
			}
		}
	}
}
