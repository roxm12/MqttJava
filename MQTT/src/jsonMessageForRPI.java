import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


public class jsonMessageForRPI implements jsonMessage {

		private JSONObject jsonobj;
		private JSONParser parser;
		public jsonMessageForRPI() {
		    jsonobj=new JSONObject();
		    parser=new JSONParser();
		    this.dataInit();
		}
		@Override
		public String getString(){
			return this.jsonobj.toJSONString();
			
		}
	
/**
 * Json으로 정의할 수 있는 device의 attr들이 어떤 것이 더 있을까?를 생각해봐야 함.
 */
	@Override
	public void dataInit() {
		// TODO Auto-generated method stub
		//이런식으로 하는 것보다 Builder를 이용하는 것이 낫다.	
		//tempobj.put("device_id", "id");
		JSONObject tempobj=new JSONObject();
		JSONArray temparr=new JSONArray();
		tempobj.put("name", "k");
		tempobj.put("value",new Integer(0));
		tempobj.put("type","led");
		temparr.add(tempobj);
		
		tempobj=new JSONObject();
		tempobj.put("name", "b");
		tempobj.put("value",new Integer(1));
		tempobj.put("type","ultrasonic");
		temparr.add(tempobj);
		
		tempobj=new JSONObject();		
		tempobj.put("name", "c");
		tempobj.put("value",new Integer(2));
		tempobj.put("type","infrared");
		temparr.add(tempobj);
		jsonobj.put("sensor_list",temparr);
	}
	/**
	 * get에서는 그냥 format에 맞춰 jsonobj를 만들어서 string으로 보내면 된다.
	 */
@Override
public String getFullDataRequestMsg() {
	// TODO Auto-generated method stub
	return "FullDataRequest";
}

@Override
public String parseFullDataResqpondMsg(String msg) {
	JSONObject tempobj=null;
	try {
		 tempobj=(JSONObject)this.parser.parse(msg);
		JSONArray temparr=(JSONArray)tempobj.get("sensor_list");
		System.out.println(temparr.toJSONString());
	} catch (ParseException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	return tempobj.toJSONString();
	// TODO Auto-generated method stub

}
/**
 * Parser를 이용하자.
 */
@Override
public String parseControlResqpondMsg(String msg) {
	// TODO Auto-generated method stub
	JSONObject tempobj=null;
	try {
		 tempobj=(JSONObject)this.parser.parse(msg);
		JSONArray temparr=(JSONArray)tempobj.get("sensor_list");
		System.out.println(temparr.toJSONString());
	} catch (ParseException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	return tempobj.toJSONString();
 }
/**
 * 여기서 control의 대상을 어떻게 지정해 줄 수 있는지 생각해봐야함.
 */
@Override
public String getControlRequestMsg() {
	// TODO Auto-generated method stub
	JSONObject tempobj=new JSONObject();
	JSONArray temparr=new JSONArray();
	tempobj.put("name", "k");
	tempobj.put("value",new Integer(0));
	tempobj.put("type","led");
	temparr.add(tempobj);
	
	tempobj=new JSONObject();
	tempobj.put("name", "b");
	tempobj.put("value",new Integer(1));
	tempobj.put("type","ultrasonic");
	temparr.add(tempobj);
	
	tempobj=new JSONObject();		
	tempobj.put("name", "c");
	tempobj.put("value",new Integer(2));
	tempobj.put("type","infrared");
	temparr.add(tempobj);
	jsonobj.put("sensor_list",temparr);
	return jsonobj.toJSONString();
}

}

