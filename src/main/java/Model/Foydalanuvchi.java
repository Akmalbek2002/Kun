package Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Foydalanuvchi {
   private String ism;
   private String familiya;
   private String tel_raqam;
   private String manzili;
   private String login;
   private String parol;
}
