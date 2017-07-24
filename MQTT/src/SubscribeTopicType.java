
public enum SubscribeTopicType {
	   
	   FULLDATARESPOND("fullDataRespond\\*"),
	   CONTROLDEVICERESPOND("controlDeviceRespond\\*");

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
