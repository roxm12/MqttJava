import java.util.ArrayList;

public class MainTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
	/*
		String broker="tcp://basserd2.iptime.org:1884";
		String clientName="solJava";
		String userName="hansol";
		String pwd="1231";
		Thread t=new Thread(new Mqtt(broker,clientName,userName,pwd,new jsonMessageForRPI()));
		t.start();
		/*
		ArrayList<Thread> threads = new ArrayList<Thread>();
		 for(int i=0;i<10;i++){
			 Thread t=new Thread(new Mqtt(broker,clientName+Integer.toString(i)
			 ,userName+Integer.toString(i),pwd+Integer.toString(i)));
			 t.start();
			threads.add(t);
		 }*/
		String broker="tcp://ec2-13-124-126-132.ap-northeast-2.compute.amazonaws.com:1883";
		String clientName="solJava";
		String userName="hansol";
		String pwd="1231";
		jsonMessage jmsg=new jsonMessageForRPI();
		Thread t=new Thread(new Mqtt(broker,clientName,userName,pwd,jmsg));
        t.start();		
		System.out.println(jmsg.getString());
		

	}
}
