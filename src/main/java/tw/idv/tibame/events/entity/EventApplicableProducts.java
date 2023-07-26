package tw.idv.tibame.events.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
@IdClass(EventApplicableProductsId.class)
public class EventApplicableProducts {

    @Id
    @Column(name = "eventId")
    private String eventId;

    @Id
    @Column(name = "productId")
    private Integer productId;

    @ManyToOne
    @JoinColumn(name = "eventId", insertable = false, updatable = false)
    private EventSingleThreshold eventSingleThreshold;

}
