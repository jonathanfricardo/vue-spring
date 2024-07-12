package app.repositories;

import app.models.Offer;

import java.util.List;

public interface OffersRepository {
    List<Offer> findAll();

    Offer findById(long id);

    Offer save(Offer offer);

    Offer deleteById(long id);
}
