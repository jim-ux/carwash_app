package pe.devteam.carwash.entities;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CarEntity {

    private int id;
    private String registration;
    private String brand;
    private String model;
    private String color;

}
