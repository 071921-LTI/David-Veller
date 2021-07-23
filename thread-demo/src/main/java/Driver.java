
public class Driver {

	public static void main(String[] args) {
		
		Runnable job = () -> {
			System.out.println("This is a lambda expression");
		};
		
		Thread helloThread = new HelloThread();
		helloThread.start();
		
		Thread byeThread = new Thread(new ByeThread());
		//byeThread.start();
		
		Thread t3 = new Thread(job);
		
		t3.start();
		byeThread.start();
		
		try {
			helloThread.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("end of main");
		
	}
	
}

class HelloThread extends Thread{
	@Override
	public void run() {
		//Thread behavior
		
		System.out.println("hello thread has started");
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("hello");
		
	}
}

class ByeThread implements Runnable{

	@Override
	public void run() {
		// TODO Auto-generated method stub
		System.out.println("Goodbye thread");
		
	}
}