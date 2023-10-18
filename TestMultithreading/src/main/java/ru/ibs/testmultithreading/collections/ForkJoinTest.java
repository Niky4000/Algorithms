package ru.ibs.testmultithreading.collections;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

public class ForkJoinTest {

	public void testForkJoin() {
		List<Long> list = new ArrayList<>();
		for (int i = 0; i < 100000; i++) {
			list.add(4L);
		}
		System.out.println("Simple result = " + computeDirectly(list) + "!");
		ForkJoinPool forkJoinPool = new ForkJoinPool();
		class LongComputer extends RecursiveTask<Long> {

			private final List<Long> subList;
			private static final int THRESHOLD = 1000;

			public LongComputer(List<Long> subList) {
				this.subList = subList;
			}

			@Override
			protected Long compute() {
				if (subList.size() < THRESHOLD) {
					return computeDirectly(subList);
				} else {
					int middle = subList.size() / 2;
					LongComputer left = new LongComputer(subList.subList(0, middle));
					LongComputer right = new LongComputer(subList.subList(middle, subList.size()));
					ForkJoinTask<Long> fork = left.fork();
					Long compute = right.compute();
					Long join = fork.join();
					return compute + join;
				}
			}

		}
		LongComputer task = new LongComputer(list);
		forkJoinPool.execute(task);
		Long result = task.join();
		System.out.println("result = " + result + "!");
	}

	private Long computeDirectly(List<Long> subList) {
		Long sum = 0L;
		for (Long n : subList) {
			sum += n;
		}
		return sum;
	}
}
