module pe.devteam.carwash {
    requires javafx.controls;
    requires javafx.fxml;

    requires java.sql;
    requires static lombok;
    requires jBCrypt;


    opens pe.devteam.carwash to javafx.fxml;
    exports pe.devteam.carwash;
    exports pe.devteam.carwash.controllers;
    exports pe.devteam.carwash.controllers.cars;
    exports pe.devteam.carwash.controllers.oilchanges;

    opens pe.devteam.carwash.controllers to javafx.fxml;
    opens pe.devteam.carwash.controllers.cars to javafx.fxml;
    opens pe.devteam.carwash.controllers.oilchanges to javafx.fxml;
    opens pe.devteam.carwash.entities to javafx.base;
    opens pe.devteam.carwash.dto to javafx.base;

}
