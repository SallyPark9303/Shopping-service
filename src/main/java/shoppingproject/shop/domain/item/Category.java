package shoppingproject.shop.domain.item;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import javax.swing.text.StyleContext;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Category {
    @jakarta.persistence.Id
    @GeneratedValue
    @Column(name = "category_id")
    private long id;
    private long name; // 카테고리 이름
    @OneToOne(mappedBy = "category", fetch = FetchType.LAZY)
    private Item item;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="parent_id")
    private  Category parent;
    @OneToMany(mappedBy = "parent")
    private List<Category> child = new ArrayList<>();

    // 연관관계 메서드
    public void addChildCategory(Category cate){
        this.child.add(cate);
        cate.setParent(this);
    }
}
