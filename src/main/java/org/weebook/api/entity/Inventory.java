package org.weebook.api.entity;

import jakarta.persistence.*;
import lombok.*;
import org.weebook.api.entity.audit.DateAudit;

import java.util.Arrays;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "inventory")
public class Inventory extends DateAudit {
    @Id
    @Column(name = "\"skuCode\"", nullable = false, length = Integer.MAX_VALUE)
    private String skuCode;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "status", length = Integer.MAX_VALUE)
    @Enumerated(EnumType.STRING)
    private Status status;

    @OneToOne(mappedBy = "skuCode")
    private Book book;

    @Getter
    @RequiredArgsConstructor
    public enum Status {
        IN_STOCK(1, "Còn hàng"),
        OUT_OF_STOCK(0, "Hết hàng"),
        INCOMING(-1, "Đang nhập về"),
        ON_HOLD(-2, "Ngừng bán");

        private final Integer value;
        private final String description;

        public static Status getStatus(int value) {
            return Arrays.stream(Status.values())
                    .filter(status -> status.value == value)
                    .findFirst()
                    .orElse(null);
        }
    }
}