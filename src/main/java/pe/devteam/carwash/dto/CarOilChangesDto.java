package pe.devteam.carwash.dto;

import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;

@Getter @Setter
public class CarOilChangesDto {

    private int id;
    private Timestamp date;
    private String carRegist;
    private int actualKm;
    private int nextKm;

}
