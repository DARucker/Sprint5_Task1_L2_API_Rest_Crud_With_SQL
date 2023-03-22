package cat.itacademy.barcelonactiva.rucker.dario.s05.t01.n02.dto;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class Flowerdto {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    int id;
    @NotEmpty(message = "name can't be empty")
    @Pattern(regexp = "[a-zA-Z _]{3,15}", message = "minimum 3 and maximun 15. Only letters or spaces allowed")
    String name;
    @NotEmpty (message = "country can't be empty")
    String country;

    private String flowerType;

    private final List<String> countrys = Arrays.asList("Austria", "Belgium", "Bulgaria",
            "Croatia", "Cyprus", "Czech Republic", "Denmark", "Estonia", "Finland",
            "France", "Germany", "Greece", "Hungary", "Ireland", "Italy", "Latvia",
            "Lithuania", "Luxembourg", "Malta", "Netherlands", "Poland", "Portugal",
            "Romania", "Slovakia", "Slovenia", "Spain", "Sweden");

}
