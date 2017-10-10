package server;

import java.io.IOException;

public class DeleteAccess implements Accessable {

	@Override
	public void serve(Connection connection) {
		String place = connection.getValue("place");
		ShoppingListModel shoplistM = new ShoppingListModel();
		shoplistM.loadFromFile();
		shoplistM.deleteListByPlace(place);
		shoplistM.saveToFile();
		
		try {
			connection.redirect("index");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
