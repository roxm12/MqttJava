
public enum PublishTopicType {
	CONTROLRESPOND("/ControlRespond"),
   FULLDATARESPOND("/FullDataRespond"),
   FULLDATAREQUEST("/FullDataRequest"),
   CONTROLREQUEST("/ControlRequest")
   ;
 

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
