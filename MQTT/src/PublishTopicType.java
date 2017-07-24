
public enum PublishTopicType {
   FULLDATAREQUEST("fullDataRequest\\"),
   CONTROLDEVICEREQUEST("controlDeviceRequest\\");
 

   private final String name;
	private PublishTopicType(String s){
		this.name=s;
	}
	public  boolean equalName(String otherName){
		return name.equals(otherName);
	}
	public String toString(){
		return this.name;
	}

}
