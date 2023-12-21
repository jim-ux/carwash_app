package pe.devteam.carwash.utils;

import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogEvent;
import javafx.stage.Stage;


public class EasyAlert {

    private String alertTitle;
    private String alertBody;
    private Alert.AlertType alertType;

    private Alert alert;

    public EasyAlert(String alertTitle, String alertBody, Alert.AlertType alertType){

        this.alertTitle = alertTitle;
        this.alertBody = alertBody;
        this.alertType = alertType;
        this.Build();
    }

    private void Build(){

        Alert alert = new Alert(this.alertType);
        alert.setTitle(Constants.SYSTEM_NAME);
        alert.setHeaderText(this.alertTitle);
        alert.setContentText(this.alertBody);
        this.alert = alert;
    }

    public void Show(){
        this.alert.show();
    }

    public void ShowWithActions(EasyAlertEvents callback){

        alert.setOnCloseRequest(new EventHandler<DialogEvent>() {
            @Override
            public void handle(DialogEvent event) {
                callback.onOkOrDismiss();
            }
        });

        this.alert.showAndWait().ifPresent(response->{

            if(response.getButtonData() == ButtonBar.ButtonData.OK_DONE){
                callback.onOkOrDismiss();
            }
        });
    }


}
