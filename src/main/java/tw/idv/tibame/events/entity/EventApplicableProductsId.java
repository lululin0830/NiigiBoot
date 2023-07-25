package tw.idv.tibame.events.entity;

import java.io.Serializable;

import lombok.Data;

@Data
public class EventApplicableProductsId implements Serializable {
   
	private static final long serialVersionUID = 1L;
	
	private String eventId;
    private Integer productId;

}
