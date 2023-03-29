package cat.itacademy.barcelonactiva.rucker.dario.s05.t01.n02.dto;

import io.swagger.v3.oas.annotations.media.Schema;
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
    @Schema(description = "This is the id of the flower. The id is autogenerated by the database")
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    int id;
    @Schema(description = "This field is the NAME of the flower", example = "rose", required = true)
    @NotEmpty(message = "name can't be empty")
    @Pattern(regexp = "[a-zA-Z _]{3,15}", message = "minimum 3 and maximun 15. Only letters or spaces allowed")
    String name;
    @Schema(description = "This field is the NAME of the COUNTRY and must be written in english", example = "Spain", required = true)
    @NotEmpty (message = "country can't be empty")
    String country;
    @Schema(description = "This field will be returned by the method entityToDto. It is used to indicate if the country belongs to the EU", example = "EU")
    private String flowerType;
    @Schema(description = "This array contains the list of the countrys of the EU and it is provided by the administrator")
    private final List<String> countrys = Arrays.asList("Austria", "Belgium", "Bulgaria",
            "Croatia", "Cyprus", "Czech Republic", "Denmark", "Estonia", "Finland",
            "France", "Germany", "Greece", "Hungary", "Ireland", "Italy", "Latvia",
            "Lithuania", "Luxembourg", "Malta", "Netherlands", "Poland", "Portugal",
            "Romania", "Slovakia", "Slovenia", "Spain", "Sweden");

}
