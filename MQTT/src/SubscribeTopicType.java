
public enum SubscribeTopicType {
	   
	   FULLDATARESPOND("/FullDataRespond"),
	   CONTROLRESPOND("/ControlRespond");

	   private final String name;
		private SubscribeTopicType(String s){
			this.name=s;
		}
		public  boolean equalName(String otherName){
			return name.equals(otherName);
		}
		public String toString(){
			return this.name;
		}

}
