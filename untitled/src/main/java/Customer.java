import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import jakarta.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "customer")
@Getter
@Setter
@ToString
public class Customer implements Serializable{
    private static final long serialVersionId = 1L;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name="firstName", unique = true, nullable = false)
    private String firstName;

    @Column(name="lastName", unique = true, nullable = false)
    private String lastName;
}
