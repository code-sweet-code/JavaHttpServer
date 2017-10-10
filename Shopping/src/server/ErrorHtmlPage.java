package server;

public class ErrorHtmlPage extends HtmlPage {

	private StringBuilder content;
	
	public ErrorHtmlPage() {
		content = new StringBuilder();
		String body = "Sorry, page is not found";
		super.generateFrame(content);
		content.insert(content.indexOf("<body>")+6, body);
	}
	
	@Override
	protected int getContentLength() {
		return this.content.toString().length();
	}

	@Override
	protected String getContent() {
		return this.content.toString();
	}

}
