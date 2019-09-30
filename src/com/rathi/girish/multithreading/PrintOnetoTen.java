package com.rathi.girish.multithreading;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;


public class PrintOnetoTen {

public static void main(String[] args) {
		
		SharedPrinter sp = new SharedPrinter();
		
		ExecutorService executor = Executors.newFixedThreadPool(2);
		executor.submit(new EvenNumberProducer(sp, 10));
		executor.submit(new OddNumberProducer(sp , 10));
		executor.shutdown();
	}

}


class SharedPrinter {
	
	Semaphore semEven = new Semaphore(0);
	Semaphore semOdd = new Semaphore(1);
	
	public void printEvenNumber(int num) {
		try {
			semEven.acquire();
		}catch(InterruptedException exception) {
			
		}
		System.out.println(num);
		semOdd.release();
	}
	
	public void printOddNumber(int num) {
		try {
			semOdd.acquire();
		}catch(InterruptedException exception) {
			
		}
		System.out.println(num);
			semEven.release();
	}
	
}

class EvenNumberProducer implements Runnable {
	
	SharedPrinter sp;
	int index;
	
	EvenNumberProducer(SharedPrinter sp , int index) {
		this.sp = sp;
		this.index = index;
	}
	
	@Override
	public void run() {
		for(int i = 2 ; i <= index ; i = i + 2 ) {
			sp.printEvenNumber(i);
		}
	}
}

class OddNumberProducer implements Runnable{
	SharedPrinter sp;
	int index;
	
	OddNumberProducer(SharedPrinter sp , int index) {
		this.sp = sp;
		this.index = index;
	}
	
	@Override
	public void run() {
		for(int i = 1 ; i <= index ; i = i + 2) {
			sp.printOddNumber(i);
		}
	}
}