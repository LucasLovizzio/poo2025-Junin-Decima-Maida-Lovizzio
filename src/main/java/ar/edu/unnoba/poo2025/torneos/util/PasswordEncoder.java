package ar.edu.unnoba.poo2025.torneos.util;

import com.password4j.types.Bcrypt;
import com.password4j.BcryptFunction;
import com.password4j.Password;

public class PasswordEncoder {

    private final String SHARED_SECRET = "POO2025";
    private final int LOG_ROUNDS = 15;

    public String encode(String rawPassword) {
        BcryptFunction bcrypt = BcryptFunction.getInstance(Bcrypt.B, LOG_ROUNDS);
        return Password.hash(rawPassword)
                .addPepper(SHARED_SECRET)
                .with(bcrypt).getResult();
    }
    public boolean verify(String rawPassword,String encodedPassword) {
        BcryptFunction bcrypt = BcryptFunction.getInstance(Bcrypt.B, LOG_ROUNDS);
        return Password.check(rawPassword, encodedPassword)
                .addPepper(SHARED_SECRET)
                .with(bcrypt);
    }

}
