package ru.mkilord.tacos;

import lombok.extern.log4j.Log4j2;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.mkilord.tacos.data.IngredientRepository;
import ru.mkilord.tacos.data.UserRepository;
import ru.mkilord.tacos.entites.Ingredient;
import ru.mkilord.tacos.entites.User;

import java.util.Optional;

import static ru.mkilord.tacos.entites.Ingredient.Type;

@Log4j2
@SpringBootApplication
public class TacoCloudApplication {
    public static void main(String[] args) {
        SpringApplication.run(TacoCloudApplication.class, args);
    }

    @Bean
    public CommandLineRunner dataLoader(IngredientRepository repo, UserRepository userRepo, PasswordEncoder passEncoder) {
        return args -> {
            repo.save(new Ingredient("FLTO", "Flour Tortilla", Type.WRAP));
            repo.save(new Ingredient("COTO", "Corn Tortilla", Type.WRAP));
            repo.save(new Ingredient("GRBF", "Ground Beef", Type.PROTEIN));
            repo.save(new Ingredient("CARN", "Carnitas", Type.PROTEIN));
            repo.save(new Ingredient("TMTO", "Diced Tomatoes", Type.VEGGIES));
            repo.save(new Ingredient("LETC", "Lettuce", Type.VEGGIES));
            repo.save(new Ingredient("CHED", "Cheddar", Type.CHEESE));
            repo.save(new Ingredient("JACK", "Monterrey Jack", Type.CHEESE));
            repo.save(new Ingredient("SLSA", "Salsa", Type.SAUCE));
            repo.save(new Ingredient("SRCR", "Sour Cream", Type.SAUCE));

            //Todo Тут не хорошо, добавляется админ пользователь, но с нулевым адресом.
            //Угроза безопасности
            Optional.ofNullable(System.getenv("ADMIN"))
                    .ifPresentOrElse(s -> {
                        userRepo.save(new User("Admin", passEncoder.encode(s),
                                "", "", "", "", "", ""));
                        log.info(String.format("User ADMIN Account login: Admin; password: %s;", s));
                    }, () -> log.warn("The environment variable does not define the administrator password!"));
        };
    }
}
