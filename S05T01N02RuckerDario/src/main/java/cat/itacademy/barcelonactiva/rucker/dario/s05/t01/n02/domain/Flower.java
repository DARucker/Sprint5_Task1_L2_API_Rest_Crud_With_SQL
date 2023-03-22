package cat.itacademy.barcelonactiva.rucker.dario.s05.t01.n02.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.NotEmpty;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "flower")
public class Flower {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    int id;

    String name;

    String country;

}
