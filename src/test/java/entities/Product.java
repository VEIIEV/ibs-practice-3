package entities;

import lombok.*;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    private String name;
    private String type;
    private boolean exotic;

}