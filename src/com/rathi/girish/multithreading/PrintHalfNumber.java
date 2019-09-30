package com.rathi.girish.multithreading;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;


public class PrintHalfNumber {

public static void main(String[] args) {
		
		Printer sp = new Printer();
		
		ExecutorService executor = Executors.newFixedThreadPool(2);
		executor.submit(new SecondHalfProducer(sp, 10));
		executor.submit(new FirstHalfProducer(sp , 10));
		executor.shutdown();
	}

}


class Printer {
	
	Semaphore secondHalf = new Semaphore(0);
	Semaphore firstHalf = new Semaphore(1);
	
	public void printSecondHalf(int num) {
		try {
			secondHalf.acquire();
		}catch(InterruptedException exception) {
			
		}
		for(int i = (num/2) + 1 ; i <= num ; i++ ) {
			System.out.println("Thread 2 - Number: " + i);
		}
		
		firstHalf.release();
	}
	
	public void printFirstHalf(int num) {
		try {
			firstHalf.acquire();
		}catch(InterruptedException exception) {
			
		}
		for(int i = 1 ; i <= num/2 ; i++) {
			System.out.println("Thread 1 - Number: " + i);
		}
		
			secondHalf.release();
	}
	
}

class SecondHalfProducer implements Runnable {
	
	Printer sp;
	int index;
	
	SecondHalfProducer(Printer sp , int index) {
		this.sp = sp;
		this.index = index;
	}
	
	@Override
	public void run() {
		sp.printSecondHalf(index);
	}
}

class FirstHalfProducer implements Runnable{
	Printer sp;
	int index;
	
	FirstHalfProducer(Printer sp , int index) {
		this.sp = sp;
		this.index = index;
	}
	
	@Override
	public void run() {
			sp.printFirstHalf(index);
	}
}
