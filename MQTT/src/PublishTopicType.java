
public enum PublishTopicType {
   FULLDATAREQUEST("FullDataRequest"),
   CONTROLREQUEST("ControlRequest"),
   FULLDATARESPOND("FullDataRespond"),
   CONTROLRESPOND("ControlRespond");
 

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
