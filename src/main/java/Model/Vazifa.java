package Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Vazifa {
    private Integer id;
    private String nomi;
    private Integer foydalanuvchi_id;
    private boolean status;
    private LocalDate localDate;
    private LocalTime localTime;
    private String daraja;
    private Integer bonus;
}
