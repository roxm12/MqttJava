
public interface jsonMessage {

	public void dataInit();
	public String getFullDataRequestMsg();
	public String getControlRequestMsg();//Control의 적용 범위를 어디서 지정을 해야 하는 것인가?
	public String parseFullDataResqpondMsg(String msg);
	public String parseControlResqpondMsg(String msg);

	public String getString();
}
