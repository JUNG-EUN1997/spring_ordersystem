package beyondProjectForOrdersystem.product.domain;

import beyondProjectForOrdersystem.common.domain.BaseTimeEntity;
import beyondProjectForOrdersystem.product.dto.ProductResDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Stack;

@Builder
@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Product extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    @Column(nullable = false)
    private String category;
    @Column(nullable = false)
    private Integer price;
    private Integer stockQuantity;
    private String imagePath;

    public ProductResDto fromEntity(){
        return ProductResDto.builder()
                .id(this.id)
                .name(this.name)
                .category(this.category)
                .price(this.price)
                .stockQuantity(this.stockQuantity)
                .imagePath(this.imagePath)
                .build();
    }

    public void updateImagePath(String imagePath){
        this.imagePath = imagePath;
    }

}
