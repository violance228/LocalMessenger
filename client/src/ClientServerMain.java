import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

public class ClientServerMain extends Application {

    private Stage primaryStage;
    private BorderPane rootLayout;

    @Override
    public void start(Stage primaryStage) throws Exception{

        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Local Messenger");
//        this.primaryStage.getIcons().add(new Image("file:resources/image/icon.png"));

        initRootLayout();
    }

    public void initRootLayout() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(ClientServerMain.class.getResource("view/RootLayout.fxml"));
            rootLayout = (BorderPane) loader.load();

            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showChatView() throws IOException {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(ClientServerMain.class.getResource("view/ChatView.fxml.fxml"));
        AnchorPane personeOverview = (AnchorPane) loader.load();

        rootLayout.setCenter(personeOverview);

        ChatController controller = loader.getController();
        controller.setMain(this);
    }

    public Stage getPrimaryStage() {

        return primaryStage;
    }

    public static void main(String[] args) {
        launch(args);
    }
}

