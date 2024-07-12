package app.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class Bid {


    @Id
    private long id;
    private double bidValue;

    @ManyToOne
    @JsonIgnoreProperties({"bids"})
    private Offer associatedOffer;

    public Bid(long id, double value) {
        this.id = id;
        this.bidValue = value;
    }

    public Bid() {

    }

    /**
     * Associates the given offer with this bid, if not yet associated
     * @param offer     provide null to dissociate
     *                  the currently associated offer
     * @return          whether a new association has been added
     */
    public boolean associateOffer(Offer offer) {
        if(this.associatedOffer != null) {
            if(this.associatedOffer.equals(offer)) {
                //no change required
                return false;
            }
            return false;
        }

        if(offer == null ) {
            //no change required
            return false;
        }

        //updates both sides of the association
        this.setAssociatedOffer(offer);
        offer.associateBid(this);
        return true;
    }

    public static Bid createSampleBid(int pId) {

        double randomValue = Math.round((Math.random() * 1000) * 100) / 100.0;

        return new Bid(pId, randomValue);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public double getBidValue() {
        return bidValue;
    }

    public void setBidValue(double bidValue) {
        this.bidValue = bidValue;
    }

    public Offer getAssociatedOffer() {
        return associatedOffer;
    }

    public void setAssociatedOffer(Offer associatedOffer) {
        this.associatedOffer = associatedOffer;
    }
}
