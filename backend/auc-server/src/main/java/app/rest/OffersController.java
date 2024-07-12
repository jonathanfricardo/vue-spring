package app.rest;

import app.exeptions.BadRequestException;
import app.exeptions.PreConditionFailedException;
import app.exeptions.ResourceNotFOundException;
import app.models.Bid;
import app.models.Offer;
import app.repositories.BidsRepositoryJPA;
import app.repositories.OffersRepositoryJPA;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
public class OffersController {

    @Autowired
    private OffersRepositoryJPA offersRepositoryMock;
    private BidsRepositoryJPA bidsRepositoryJPA;

    public OffersController(OffersRepositoryJPA offersRepositoryJPA, BidsRepositoryJPA bidsRepositoryJPA) {
        this.offersRepositoryMock = offersRepositoryJPA;
        this.bidsRepositoryJPA = bidsRepositoryJPA;
    }


    @GetMapping("/offers")
    public List<Offer> getAllOffers(
            @RequestParam(name = "status", required = false) String status,
            @RequestParam(name = "name", required = false) String name,
            @RequestParam(name = "minBidValue", required = false) Double minBidValue
            ){

        if (status == null && name != null && minBidValue != null) {
            throw new BadRequestException("Illegal combination of parameters");
        }

        if (status != null && name != null && minBidValue != null){
            throw new BadRequestException("Illegal combination of parameters");
        }

        if (status != null && name != null && minBidValue == null){
            throw new BadRequestException("Illegal combination of parameters");
        }

        //check which request parameters have been provided
        if (status != null && name == null && minBidValue == null ){
            return offersRepositoryMock.findByQuery("Offer_find_by_status", Offer.getStatusValue(status));
        } else if (status == null && name != null && minBidValue == null) {
            return offersRepositoryMock.findByQuery("Offer_find_by_title", name);
        } else if (status != null && name == null && minBidValue != null) {
            return offersRepositoryMock.findByQuery("Offer_find_by_status_and_minBidValue", Offer.getStatusValue(status), minBidValue);
        }

        //normal request
        return offersRepositoryMock.findAll();
    }

    @GetMapping("/offers/{id}")
    public Offer getUser(@PathVariable long id){

        Offer offer = this.offersRepositoryMock.findById(id);

        if(offer == null) {
            throw new ResourceNotFOundException("not found id:" + id);
        }

        return offer;
    }

    @PostMapping("/offers")
    public ResponseEntity<Offer> createOffer(@RequestBody Offer offer){
        Offer savedOffer = this.offersRepositoryMock.save(offer);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedOffer.getId())
                .toUri();
        return ResponseEntity.created(location).body(offer);
    }

    @PutMapping("/offers/{id}")
    public Offer updateOffer(@PathVariable long id, @RequestBody Offer offer) {

            if(id != offer.getId()) {
            //if the offers id's don't match, we throw an exception
            throw new PreConditionFailedException("pre condition id:"+id);
            }

            if(this.offersRepositoryMock.findById(id) == null) {
                //if the given id is 0 or bigger than the size, we throw an error
                throw new ResourceNotFOundException("given id: " + id + " is not found");
            }

        return this.offersRepositoryMock.save(offer);
    }

    @DeleteMapping("/offers/{id}")
    public Offer deleteUser(@PathVariable long id) {

        if(this.offersRepositoryMock.findById(id) != null) {
            return this.offersRepositoryMock.deleteById(id);
        } else {
            throw new ResourceNotFOundException("not found id:" + id);
        }

    }

    @PostMapping("/offers/{offerId}/bids")
    public Offer addBidToOffer(@RequestBody Bid bid, @PathVariable long offerId) {

        Offer offer = offersRepositoryMock.findById(offerId);

        Bid newBid = new Bid(this.bidsRepositoryJPA.findAll().size() + 1, bid.getBidValue());
        this.bidsRepositoryJPA.save(newBid);

        if(offer.getStatus() != Offer.Status.FOR_SALE) {
            throw new PreConditionFailedException("This offer is not for sale!");
        }

        if(offer.getValueHighestBid() > bid.getBidValue()) {
            throw new PreConditionFailedException("Bid with value: " + bid.getBidValue() + " does not beat latest bid on offer: " + offerId);
        }


        //add bid to offer and set it's new highest value bid
        offer.associateBid(newBid);
        newBid.associateOffer(offer);
        offer.setValueHighestBid(newBid.getBidValue());

        this.offersRepositoryMock.save(offer);
        this.bidsRepositoryJPA.save(newBid);

        return offer;
    }

    @GetMapping("/offers/summary")
    @JsonView(OfferSummaryView.class)
    public List<Offer> getSummaryOfOffers(){
        return this.offersRepositoryMock.findAll();
    }
}