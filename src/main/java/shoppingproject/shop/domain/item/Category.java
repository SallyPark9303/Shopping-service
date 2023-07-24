package shoppingproject.shop.domain.item;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Nationalized;

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
    @Nationalized
    private String name; // 카테고리 이름
    @OneToMany(mappedBy = "category")
    private List<Item> item;
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
