
public class TunnelTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		// create the tunnel, all cars will be
		// passed the same resource	
		Tunnel portTunnel = new Tunnel(10);

		for(int j = 1; j <= 20; j++){
			// each car thread that is created must // use the same resource - portTunnel
			new Thread(new Car(portTunnel,j)).start();
			
			// It will give the thread some time and execute in the right moment
			try {
				Thread.sleep(200);
			} catch (Exception e) {
				
			}
		}

	}
}

// Class Car
class Car implements Runnable {
	// var declaration
	private Tunnel t;
	private int num;
	
	
	// Constructor
	public Car(Tunnel t1, int n) {
		t = t1;
		num = n;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		
		t.waitTunnel(num);
				
		// you should make the thread
		// sleep for 10 seconds here
		try {
				Thread.sleep(10000);
			} catch (InterruptedException e) {
				// TODO: handle exception
				System.out.println("Car "+ num +" is crossing the tunnel");
			}		
		
		t.signalTunnel(num);
	
	}
}

// Class Tunnel
class Tunnel{
	// var declaration
	private int count;
	
	// Constructor
	public Tunnel(int c) {
		//initial count value;
		count = c;
	}
	
	// critical resources
	public synchronized void waitTunnel(int n) {
		// if count is zero the process must wait
		while(count == 0)
			try{
				wait();
			} catch(InterruptedException e){
				
			}

		 // otherwise the process can continue
		 count--;
		 System.out.println("Car " +n+ " +  has Entered. " +  count + " spaces left in tunnel.");

	}
	
	public synchronized void signalTunnel(int n) {
		count++;
    	System.out.println("Car "+n+" has left. " +  count + " spaces left in tunnel.");		
		notify();
	}
}
