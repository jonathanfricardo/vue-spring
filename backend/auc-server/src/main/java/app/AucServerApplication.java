package app;

import app.models.Bid;
import app.models.Offer;
import app.models.User;
import app.repositories.BidsRepositoryJPA;
import app.repositories.OffersRepositoryJPA;
import app.repositories.UserRepositoryJPA;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLOutput;
import java.util.List;

@SpringBootApplication
public class AucServerApplication implements CommandLineRunner {

    private final OffersRepositoryJPA offersRepositoryJPA;
    private final BidsRepositoryJPA bidsRepositoryJPA;

    private final UserRepositoryJPA userRepositoryJPA;

    public AucServerApplication(OffersRepositoryJPA offersRepositoryJPA, BidsRepositoryJPA bidsRepositoryJPA, UserRepositoryJPA userRepositoryJPA) {
        this.offersRepositoryJPA = offersRepositoryJPA;
        this.bidsRepositoryJPA = bidsRepositoryJPA;
        this.userRepositoryJPA = userRepositoryJPA;
    }

    public static void main(String[] args) {
        SpringApplication.run(AucServerApplication.class, args);
    }

    public void createInitialOffers(){
        List<Offer> offers = this.offersRepositoryJPA.findAll();
        List<Bid> bids = this.bidsRepositoryJPA.findAll();

        if(offers.size() >  0 || bids.size() > 0) return;

        System.out.println("Configuring some initial offer data");

        for (int i = 0; i < 9; i++) {
            Offer offer = Offer.createSampleOffer(i);
            Bid bid = Bid.createSampleBid(i);
            offer = this.offersRepositoryJPA.save(offer);
            bid = this.bidsRepositoryJPA.save(bid);

            offer.setValueHighestBid(bid.getBidValue());

            offer.associateBid(bid);
            
        }
    }

//    public void createInitialUsers(){
//        List<User> users = this.userRepositoryJPA.findAll();
//
//        if (users.size() > 0) return;
//
//        System.out.println("Configuring some initial user data");
//
//        for (User user : User.createSampleUsers()){
//            this.userRepositoryJPA.save(user);
//        }
//    }


    @Override
    @Transactional
    public void run(String... args) {
        System.out.println("Running CommandLine Startup");
        this.createInitialOffers();
//        this.createInitialUsers();
    }
}
