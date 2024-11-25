package vnpost.technology.entity;

import javax.persistence.*;

@Entity
@Table(name = "addresses")
public class Address {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Integer id;

        @Column(nullable = false)
        private String receiverName;

        @Column(nullable = false)
        private String receiverPhone;

        @Column(nullable = false)
        private String receiverAddress;

        @ManyToOne
        @JoinColumn(name = "user_id", nullable = false)
        private User user;

    }

