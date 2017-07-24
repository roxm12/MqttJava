import java.util.ArrayList;

public class MainTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
	/*
		String broker="tcp://basserd2.iptime.org:1884";
		String clientName="solJava";
		String userName="hansol";
		String pwd="1231";
		
		ArrayList<Thread> threads = new ArrayList<Thread>();
		 for(int i=0;i<10;i++){
			 Thread t=new Thread(new Mqtt(broker,clientName+Integer.toString(i)
			 ,userName+Integer.toString(i),pwd+Integer.toString(i)));
			 t.start();
			threads.add(t);
		 }*/
		
		for(SubscribeTopicType type:SubscribeTopicType.values()){
			
			System.out.println(type);
			System.out.println(type.ordinal());
		}

	}
}
