package br.com.nlw.events.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "tbl_subscription")
@Getter
@Setter
public class Subscription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "subscription_number")
    private Integer subcriptionNumber;

    @ManyToOne // muitas incrições para o mesmo evento
    @JoinColumn(name = "event_id")
    private Event event;

    @ManyToOne // multiplas Inscrições para um mesmo usuário
    @JoinColumn(name = "subscribed_user_id")
    private User subscriber;

    @ManyToOne
    @JoinColumn(name = "indication_user_id", nullable = true) // caso onde ninguém indicou
    private User indication;
}
