package Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Hisobot {
    private Integer id;
    private int foydalanuvchi_id;
    private double kun_bali;
    private LocalDate localDate;
}
