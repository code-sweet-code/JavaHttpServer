package server;
import java.io.IOException;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class Shopping extends Application {
	
	private MainController maincontroller = new MainController();
	private TextField port;
	private Label status;
    
    @Override
    public void start(Stage primaryStage) {
    	initController();
    	Button launchbtn = new Button();
        launchbtn.setText("启动");
        launchbtn.setOnAction(new EventHandler<ActionEvent>() {
 
            @Override
            public void handle(ActionEvent event) {
            	LaunchServer();
            }
        });
        
        Button stopbtn = new Button();
        stopbtn.setText("停止");
        stopbtn.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				closeSocket();
				
			}
        	
        });
        
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));
        Text scenetitle = new Text("设置参数");
        grid.add(scenetitle, 0, 0, 2, 1);

        Label labelport = new Label("端口:");
        grid.add(labelport, 0, 1);

        TextField portfield = new TextField();
        port = portfield;
        grid.add(portfield, 1, 1);
        
        Label notification = new Label();
        status = notification;
        grid.add(notification, 0, 2, 2, 1);
        
        grid.add(launchbtn, 0, 3);
        grid.add(stopbtn, 1, 3);
        Scene scene = new Scene(grid, 300, 250);
        
        primaryStage.setTitle("康康的购物单");
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>(){

			@Override
			public void handle(WindowEvent event) {
				closeSocket();
			}
        	
        });
        primaryStage.setScene(scene);
        primaryStage.show();
    }
 private void initController() {
	maincontroller = new MainController();
	maincontroller.bindAccess("", new IndexAccess());
	maincontroller.bindAccess("index", new IndexAccess());
	maincontroller.bindAccess("edit", new EditPageAccess());
	maincontroller.bindAccess("save", new SaveAccess());
	maincontroller.bindAccess("delete", new DeleteAccess());
	maincontroller.setUICallback(this);
	}
protected void closeSocket() {
		if(maincontroller != null){
			maincontroller.closeSocket();
			status.setText("停止成功");
		}
		
	}
 
 
protected void LaunchServer() {
		if(!maincontroller.isRunning()){
		try {
			maincontroller.startListen(Integer.valueOf(port.getText()));
			status.setText("启动成功");
		} catch (NumberFormatException e) {
			status.setText("端口必须是数字 7000~8000");
			e.printStackTrace();
		} catch (IOException e) {
			status.setText("端口打开失败，换个端口");
			e.printStackTrace();
		}
		
		}else{
			status.setText("服务器已经启动");
		}
			
	}


public static void main(String[] args) {
        launch(args);
    }
}

