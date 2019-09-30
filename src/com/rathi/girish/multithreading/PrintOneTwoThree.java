		package com.rathi.girish.multithreading;
		
		import java.util.concurrent.ExecutorService;
		import java.util.concurrent.Executors;
		import java.util.concurrent.Semaphore;
		
		
		public class PrintOneTwoThree {
		
		public static void main(String[] args) {
				
				Printers sp = new Printers();
				
				ExecutorService executor = Executors.newFixedThreadPool(3);
				executor.submit(new FirstNumberProducer(sp, 9));
				executor.submit(new SecondNumberProducer(sp , 9));
				executor.submit(new ThirdNumberProducer(sp , 9));
				executor.shutdown();
			}
		
		}
		
		
		class Printers {
			
			Semaphore first = new Semaphore(1);
			Semaphore second = new Semaphore(0);
			Semaphore third = new Semaphore(0);
			
			public void printFirstNumber() {
				try {
					first.acquire();
				}catch(InterruptedException exception) {
					
				}
				System.out.print("1");
					second.release();
			}
			
			public void printSecondNumber() {
				try {
					second.acquire();
				}catch(InterruptedException exception) {
					
				}
				System.out.print("2");
				third.release();
			}
			
			public void printThirdNumber() {
				try {
					third.acquire();
				}catch(InterruptedException exception) {
					
				}
				System.out.print("3");
				first.release();
			}
			
		}
		
		class FirstNumberProducer implements Runnable {
			
			Printers sp;
			int index;
			
			FirstNumberProducer(Printers sp , int index) {
				this.sp = sp;
				this.index = index;
			}
			
			@Override
			public void run() {
				for(int i = 1 ; i <= index ; i = i + 3 ) {
					sp.printFirstNumber();
				}
			}
		}
		
		class SecondNumberProducer implements Runnable{
			Printers sp;
			int index;
			
			SecondNumberProducer(Printers sp , int index) {
				this.sp = sp;
				this.index = index;
			}
			
			@Override
			public void run() {
				for(int i = 2 ; i <= index ; i = i + 3) {
					sp.printSecondNumber();
				}
			}
		}
		
		class ThirdNumberProducer implements Runnable{
			Printers sp;
			int index;
			
			ThirdNumberProducer(Printers sp , int index) {
				this.sp = sp;
				this.index = index;
			}
			
			@Override
			public void run() {
				for(int i = 3 ; i <= index ; i = i + 3) {
					sp.printThirdNumber();
				}
			}
		}