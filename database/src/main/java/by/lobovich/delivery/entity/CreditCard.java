package by.lobovich.delivery.entity;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Entity
@Table(name = "credit_cards")
public class CreditCard extends BaseEntity {

    @Column(name = "number")
    private String cardNumber;

    @Column(name = "date")
    private String cardDate;

    @Column(name = "cvv")
    private String cardCvv;

    @ManyToOne
    @JoinColumn(name = "personal_info_id")
    private PersonalInfo personalInfo;

    public String getCardInfo() {
        return addDelimiter(cardNumber) + "\t" + cardDate;
    }

    private String addDelimiter(String s) {
        StringBuilder sb = new StringBuilder(s);
        String delimiter = " ";
        for (int i = 4; i < s.length(); ) {
            sb.insert(i, delimiter);
            i += 5;
        }
        return sb.toString();
    }

}

