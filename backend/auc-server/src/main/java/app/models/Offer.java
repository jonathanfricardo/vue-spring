package app.models;


import app.exeptions.BadRequestException;
import app.rest.OfferSummaryView;
import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;

import java.util.Date;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

@NamedQueries({
        @NamedQuery(name="Offer_find_by_status",
            query = "select o from Offer o where o.status = ?1"),
        @NamedQuery(name="Offer_find_by_title",
            query = "select o from Offer o where o.title like concat('%', ?1, '%')"),
        @NamedQuery(name="Offer_find_by_status_and_minBidValue",
            query = "select o from Offer o where o.status = ?1 and o.valueHighestBid > ?2")
})
@Entity
public class Offer {

    @JsonView(OfferSummaryView.class)
    @SequenceGenerator(name="Offer_ids", initialValue = 30000)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "Author_ids")
    @Id
    private  int id;
    @JsonView(OfferSummaryView.class)
    private String title;
    private String description;
    @JsonView(OfferSummaryView.class)
    @Enumerated(EnumType.STRING)
    private Status status;

    private Date sellDate;
    private  Double valueHighestBid;


    @OneToMany(cascade = CascadeType.REMOVE)
    @JsonIncludeProperties({"id", "bidValue"})
    private Set<Bid> bids = new HashSet<>();


    public enum Status {
        NEW,
        FOR_SALE,
        SOLD,
        PAID,
        DELIVERED,
        CLOSED,
        EXPIRED,
        WITHDRAWN;
    }

    public Offer(){

    }

    public static Status getStatusValue(String status) {
        try {
            return Status.valueOf(status);
        }catch (Exception e) {
            throw new BadRequestException("status=" + status + " doesnt exist");
        }

    }

    @JsonCreator
    public Offer(
            @JsonProperty("id") int id,
            @JsonProperty("title") String title,
            @JsonProperty("description") String description,
            @JsonProperty("status") Status status,
            @JsonProperty("sellDate") Date sellDate,
            @JsonProperty("valueHighestBid") double valueHighestBid
    ) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.status = status;
        this.sellDate = sellDate;
        this.valueHighestBid = valueHighestBid;
    }

    /**
     * Associates the given bid the this offer, if not yet associated
     * and only if the value of the bid exceeds the current highest bid on this offer
     * @param bid
     * @return whether a new association has been added
     */
    public boolean associateBid(Bid bid) {
        if (bid == null || this.bids.contains(bid)) {
            //no change required
            return false;
        }

        //update both sides of the association
        this.bids.add(bid);
        bid.associateOffer(this);
        return true;
    }

    /**
     * Dissociates the given bid from this offer, if associated
     * @param bid
     * @return whether an existing association has been removed
     */
    public boolean dissociateBid(Bid bid) {
        if (bid == null || !this.getBids().contains(bid)) {
            //no change required
            return false;
        }

        //update both sides of the association
        this.getBids().remove(bid);
        bid.setAssociatedOffer(null);
        return true;
    }

    public static Offer createSampleOffer(int pId){

        //create random status
        Status[] statuses = Status.values();
        Random random = new Random();

        Status status = statuses[random.nextInt(statuses.length)];

        //create random date
        Date currentDate = new Date();
        int randomDays = random.nextInt(365);
        Date randomDate = new Date(currentDate.getTime() + randomDays * 24 * 60 * 60 * 1000);

        //random value bid
        Double randomValueHighestBid = status == Status.NEW ? 0 : Math.round((Math.random() * 1000) * 100) /100.0;

        //return new offer
        return new Offer(
                pId,
                "Title for Offer " + pId,
                "Description for Offer " + pId,
                status,
                randomDate,
                randomValueHighestBid
        );
    }

    public static Offer copyConstructor(Offer offer) {
        if (offer == null) {
            return null;
        }

        Offer copy = new Offer(
                offer.id,
                offer.title,
                offer.description,
                offer.status,
                new Date(offer.sellDate.getTime()),
                offer.valueHighestBid
        );
        return copy;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public Status getStatus() {
        return status;
    }

    public Date getSellDate() {
        return sellDate;
    }

    public Double getValueHighestBid() {
        return valueHighestBid;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Set<Bid> getBids() {
        return bids;
    }

    public void setBids(Set<Bid> bids) {
        this.bids = bids;
    }
    public void setValueHighestBid(Double valueHighestBid) {
        this.valueHighestBid = valueHighestBid;
    }
}
