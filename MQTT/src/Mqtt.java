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
import org.json.simple.JSONObject;

public class Mqtt implements MqttCallback,Runnable{
	private  String broker;
	private  String clientId;
	private  String userName;
	private  String pwd;
	private  MqttAsyncClient client;
    private  MqttMessage message;
    private  MemoryPersistence persistence;
    private  MqttConnectOptions connOpts;//extra SSL, TLS setting  
    private  List<String> pubTopics;
    private List<String> subTopics;
    
    public static SubscribeTopicType topicType;
    
    public Mqtt(String broker,String clientId,String userName,String pwd){
    	this.broker=broker;
    	this.clientId=clientId;
    	this.userName=userName;
    	this.pwd=pwd;
    	pubTopics=new ArrayList<String>();
    	subTopics=new ArrayList<String>();
    	
    
    }
  
    
    public void addSubTopic(String topic){
    	subTopics.add(topic);
    }
    public void addPubTopic(String topic){
    	pubTopics.add(topic);
    }
    public String getuserName(){
    	return this.userName;
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
    public void topicsInit(){
    	for(SubscribeTopicType type:SubscribeTopicType.values()){
    		subTopics.add(type.toString());
    	}
    	for(PublishTopicType type:PublishTopicType.values()){
    		pubTopics.add(type.toString());
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
    	System.out.println(this.clientId+": Disconnected!!");
    }
    /**
     * 
     * @param msg -->이것을 Json으로 바꾸면 된다.
     * @param qos
     * @param type FDReq FDRes CDReq CDRes topic type을 정한다.
      */
    public void publish(String msg, int qos,PublishTopicType type){
    	message.setQos(qos);
    	message.setPayload(msg.getBytes());
    	try {
			client.publish(pubTopics.get(type.ordinal()), message);
		} catch (MqttException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    public void subscribe(String topic, int qos){
    	subTopics.add(topic);
    	try {
			client.subscribe(topic,qos);
		} catch (MqttException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    public void unsubscribe(String topic){
    	Iterator<String> it=subTopics.iterator();
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
		System.out.println(this.clientId+": Lost connection."+arg0.getCause());
	}

	@Override
	public void deliveryComplete(IMqttDeliveryToken arg0) {
		// TODO Auto-generated method stub
		try {
			System.out.println("Message with "+arg0.getMessage().toString()+
					" delivered from"+this.clientId);
		} catch (MqttException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Message가 도착한 경우의 routine을 thread로 만들어야 할 것 같다.
	 */
	@Override
	public void messageArrived(String topic, MqttMessage arg1) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("Message arrived : " + new String(arg1.getPayload(), "UTF-8")
				+" with token:"+topic
				+" to "+this.clientId);
		/*DB 저장 같은 루틴*/
		 //this.publish("YEEPP",0); 서버측에서 MQTT로 메시지를 받는 경우--RP로부터 받는 경우임. --> 이를 다시 가공해서 device에 보내주어야 한다.
		}
	
	public void shutoffSubscribing(){
		Thread.currentThread().interrupt();
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		this.init();
		this.subscribe("/sol",0);
		while(!Thread.currentThread().isInterrupted()){
			try{
				Thread.sleep(1000);
			}catch(InterruptedException e){
				e.printStackTrace();
				Thread.currentThread().interrupt();
			}
			
		}
		this.disconnect();
	}

}
