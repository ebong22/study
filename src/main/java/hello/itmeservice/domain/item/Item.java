package hello.itmeservice.domain.item;

import lombok.Data;

@Data // Data 롬복 사용하면 게터, 세터, requiredArgumentConstructor, toString 모두 만들어주기 때문에 아주 주의가 필요함. 만약 쓸거라면 뭐뭐 사용되는건지 확인 잘하고 사용하기
public class Item {

    private Long id;
    private String itemName;
    private Integer price; //int 아닌 intger인 경우는 null 가능성이 있다고 염두해 두는 것
    private Integer quantity; //int 아닌 intger인 경우는 null 가능성이 있다고 염두해 두는 것 (int는 null 못들어감)

    public Item() {
    }

    public Item(String itemName, Integer price, Integer quantity) {
        this.itemName = itemName;
        this.price = price;
        this.quantity = quantity;
    }
}
