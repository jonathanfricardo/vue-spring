package app.rest;

import app.exeptions.PreConditionFailedException;
import app.exeptions.ResourceNotFOundException;
import app.models.Bid;
import app.repositories.BidsRepositoryJPA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
public class BidsController {

    @Autowired
    private BidsRepositoryJPA bidsRepositoryJPA;

    public BidsController(BidsRepositoryJPA bidsRepositoryJPA) {
        this.bidsRepositoryJPA = bidsRepositoryJPA;
    }

    @RequestMapping("/bids")
    public List<Bid> getAllBids() {return bidsRepositoryJPA.findAll();}

    @GetMapping("/bids/{id}")
    public Bid getBid(@PathVariable long id) {

        Bid bid = this.bidsRepositoryJPA.findById(id);

        if(bid == null) {
            throw new ResourceNotFOundException("not found id: " + id);
        }

        return bid;
    }

    @PostMapping("/bids")
    public ResponseEntity<Bid> createBid(@RequestBody Bid bid) {
        Bid savedBid = this.bidsRepositoryJPA.save(bid);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedBid.getId())
                .toUri();
        return ResponseEntity.created(location).body(bid);
    }

    @PutMapping("/bids/{id}")
    public Bid updateBid(@PathVariable long id, @RequestBody Bid bid) {

        if(id != bid.getId()) {
            throw new PreConditionFailedException("pre condition id: " + id);
        }

        if(this.bidsRepositoryJPA.findById(id) == null) {
            throw new ResourceNotFOundException("given id: " + id + "is not found");
        }

        return bidsRepositoryJPA.save(bid);
    }

    @DeleteMapping("/bids/{id}")
    public Bid deletedBid(@PathVariable long id) {

        if(this.bidsRepositoryJPA.findById(id) != null) {
            return this.bidsRepositoryJPA.deleteById(id);
        } else {
            throw new ResourceNotFOundException("not found id: " + id);
        }

    }
}
