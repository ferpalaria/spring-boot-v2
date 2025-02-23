package br.com.nlw.events.repository;

import br.com.nlw.events.dto.SubscriptionRankingItem;
import br.com.nlw.events.model.Event;
import br.com.nlw.events.model.Subscription;
import br.com.nlw.events.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SubscriptionRepo extends CrudRepository<Subscription, Integer> {

    /**
     * findBy recebe nomes e atributos
     */
    public Subscription findByEventAndSubscriber(Event evt, User user);

    /**
     *     Como ele não tem um padrão tipo o FindBy - tenho que criar uma consulta customizada com o @Query
     *     Como foi uma query que foi copiada do MySQL usa o parametro nativeQuery = true
     */
    @Query(value = "select count(subscription_number) as quantidade, indication_user_id, user_name "+
            " from tbl_subscription inner join tbl_user " +
            " on tbl_subscription.indication_user_id = tbl_user.user_id "+
            " where indication_user_id is not null "+
            "   and event_id = :eventId" + // parametrização do eventId
            " group by indication_user_id "+
            " order by quantidade desc", nativeQuery = true)
    public List<SubscriptionRankingItem> generateRanking(@Param("eventId") Integer eventId);

}
