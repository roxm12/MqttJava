import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttAsyncClient;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

public class Mqtt implements MqttCallback{
	private static String broker;
	private static String clientId;
	private static String userName;
	private static String pwd;
	private static MqttAsyncClient client;
    private static MqttMessage message;
    private static MemoryPersistence persistence;
    private static MqttConnectOptions connOpts;//extra SSL, TLS setting  
    private static List<String> topics;
    
    
    public Mqtt(String broker,String clientId,String userName,String pwd){
    	this.broker=broker;
    	this.clientId=clientId;
    	this.userName=userName;
    	this.pwd=pwd;
    	topics=new ArrayList<String>();
    }
    public void addTopic(String topic){
    	topics.add(topic);
    }
    public void init(){
    	try {
    		persistence=new MemoryPersistence();
			client=new MqttAsyncClient(broker, clientId,persistence);
			client.setCallback(this);//Asyn-->Call back 
			connOpts=new MqttConnectOptions();
			connOpts.setUserName(userName);
			connOpts.setPassword(pwd.toCharArray());
			connOpts.setCleanSession(true);
			System.out.println("Connecting to broker:"+broker);
			client.connect(connOpts,null,new IMqttActionListener() {
				@Override
				public void onSuccess(IMqttToken arg0) {
					// TODO Auto-generated method stub
					System.out.println("Connection completed");
				}
				@Override
				public void onFailure(IMqttToken arg0, Throwable arg1) {
					// TODO Auto-generated method stub
					System.out.println("Connection failed "+arg1.getCause());
				}
			});
			message=new MqttMessage();
		} catch (MqttException e) {
			// TODO Auto-generated catch block
			System.out.println("reason "+e.getReasonCode());
            System.out.println("msg "+e.getMessage());
            System.out.println("loc "+e.getLocalizedMessage());
            System.out.println("cause "+e.getCause());
            System.out.println("excep "+e);
			e.printStackTrace();
		}
    	try {//왜 여기서 재워야 하는지...
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
          e.printStackTrace();
        }
    }
    public void disconnect(){
    	try {
			client.disconnect();
			client.close();
		} catch (MqttException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	System.out.println("Disconnected!!");
    }
    public void publish(String msg, int qos){
    	message.setQos(qos);
    	message.setPayload(msg.getBytes());
    	try {
			client.publish(topics.get(0), message);
		} catch (MqttException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	
    }
    public void subscribe(String topic, int qos){
    	topics.add(topic);
    	try {
			client.subscribe(topic,qos);
		} catch (MqttException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    public void unsubscribe(String topic){
    	Iterator<String> it=topics.iterator();
    	while(it.hasNext()){
    		String temp=it.next();
    		if(temp.equals(topic))it.remove();
    	}
    	try {
			client.unsubscribe(topic);
		} catch (MqttException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
	@Override
	public void connectionLost(Throwable arg0) {
		// TODO Auto-generated method stub
		System.out.println("Lost connection."+arg0.getCause());
	}

	@Override
	public void deliveryComplete(IMqttDeliveryToken arg0) {
		// TODO Auto-generated method stub
		System.out.println("Message with "+arg0+" delivered");
	}

	@Override
	public void messageArrived(String arg0, MqttMessage arg1) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("Message arrived : " + new String(arg1.getPayload(), "UTF-8"));
		}

}
