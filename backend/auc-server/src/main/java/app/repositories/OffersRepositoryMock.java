package app.repositories;

import app.models.Offer;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

@Repository
public class OffersRepositoryMock implements OffersRepository {

    private int amountOfOffers = 7;
    private List<Offer> offerList;

    public OffersRepositoryMock(List<Offer> offerList) {
        this.offerList = offerList != null ? offerList : new ArrayList<>();
        createMockData();
        System.out.println(offerList);
    }

    public void createMockData(){
        for (int i = 1; i < amountOfOffers + 1; i++) {
            this.offerList.add(Offer.createSampleOffer(i));
        }
    }


    @Override
    public List<Offer> findAll() {
        return offerList;
    }

    @Override
    public Offer findById(long id) {

        Predicate<? super Offer> predicate = offer -> offer.getId() == id;
        return offerList.stream().filter(predicate).findFirst().orElse(null);
    }

    @Override
    public Offer save(Offer offer) {
        
        Offer oldOffer = findById(offer.getId());

        //updates offer in list
        if (oldOffer != null) {
            int index = this.offerList.indexOf(oldOffer);
            this.offerList.set(index, offer);
        }

        //adds new offer to list when id equals to 0
        if(offer.getId() == 0 ) {
            //add 1 because offers start at 1 (and not 0)
            offer.setId(amountOfOffers+1);
            Offer tempOffer = Offer.createSampleOffer(offer.getId());
            this.offerList.add(amountOfOffers, tempOffer);
            amountOfOffers++;
            return tempOffer;
        }

        //index out of bound
        if (offer.getId() > amountOfOffers) {
            this.offerList.add(amountOfOffers, offer);
            amountOfOffers++;
        }

        return offer;
    }

    @Override
    public Offer deleteById(long id) {

        if(id < 0 || id > this.offerList.size()){
            //invalid index
            return null;
        }

        Offer deletedOffer = findById(id);

        this.offerList.remove(deletedOffer);

        this.amountOfOffers--;

        return deletedOffer;
    }


}
