package hello.itemservice.domain.item;

import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
//scriptAssert보단 이런 처리는 그냥 메소드에서 해주는게 바람직
//@ScriptAssert(lang="javascript", script = "_this.price * _this.quantity <= 10000")
public class ItemBackup4V3 {

    @NotNull(groups = UpdateCheck.class) //수정시에만 적용
    private Long id;

    // 빈값 + 공백만있는 경우를 허용하지 않음
    @NotBlank(groups = { SaveCheck.class, UpdateCheck.class} )
    private String itemName;

    @NotNull(groups = { SaveCheck.class, UpdateCheck.class} )
    @Range(min = 1000, max = 1000000, groups = { SaveCheck.class, UpdateCheck.class} )
    private Integer price;

    @NotNull (groups = { SaveCheck.class, UpdateCheck.class} )
    @Max(value = 9999, groups = { SaveCheck.class} )
    private Integer quantity;

    public ItemBackup4V3() {
    }

    public ItemBackup4V3(String itemName, Integer price, Integer quantity) {
        this.itemName = itemName;
        this.price = price;
        this.quantity = quantity;
    }
}
