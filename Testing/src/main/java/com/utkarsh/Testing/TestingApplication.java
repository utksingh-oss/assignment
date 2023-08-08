package com.utkarsh.Testing;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Comparator;

@SpringBootApplication
public class TestingApplication implements CommandLineRunner {
	public static void main(String[] args) {
		SpringApplication.run(TestingApplication.class, args);
	}

	public static Integer[] sortTheArray(Integer[] array){
		Arrays.sort(array, Comparator.reverseOrder());
		return array;
	}

	@Override
	public void run(String... args) throws Exception {
		Integer[] array =new Integer[]{99, 12, -8, 12, 34, 110, 0, 121, 66, -110 };
		Integer[] descSortedArray = sortTheArray(array);
		System.out.println("Reverse sorted array: ");
		System.out.println(Arrays.toString(descSortedArray));
	}
}
