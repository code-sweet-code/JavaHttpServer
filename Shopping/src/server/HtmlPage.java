package server;

public abstract class HtmlPage {
	protected StringBuilder content;
	public HtmlPage()
	{
		content = new StringBuilder();
	}
	protected void generateFrame(StringBuilder builder){
		builder.append("<!DOCTYPE html>"
				+ "<html>"
				+ "<head>"
				+ "<meta charset='utf-8'/>"
				+ "<meta name='viewport' content='width=device-width, initial-scale=1, user-scalable=no' />"
				//+ "<link rel='stylesheet' href='https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css' integrity='sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u' crossorigin='anonymous'/>"
				//+ "<link rel='stylesheet' href='https://ajax.googleapis.com/ajax/libs/jquerymobile/1.4.5/jquery.mobile.min.css' />"
				+ "<title>康康的购物单</title>"
				+ "<script src='https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js'></script>"
				//+ "<script src='https://ajax.googleapis.com/ajax/libs/jquerymobile/1.4.5/jquery.mobile.min.js'></script>"
				//+ "<script src='https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js' integrity='sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa' crossorigin='anonymous'></script>"
				
				+ "</head>"
				+ "<body class='container'>"
				+ "</body>"
				+ "</html>");
	}
	
	protected abstract int getContentLength();

	protected abstract String getContent();

}
