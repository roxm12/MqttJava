
public class mainTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String broker="tcp://basserd2.iptime.org:1884";
		String clientName="solJava";
		String userName="hansol";
		String pwd="1231";
		Mqtt mqtt=new Mqtt(broker,clientName,userName,pwd);
		mqtt.init();
		mqtt.addTopic("/sol");
		mqtt.subscribe("/sol", 0);
		mqtt.publish("hello from here",0);
		sleep(5000);
		mqtt.disconnect();

	}
	static void sleep(int time){

        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            }
        }
	}
