package app.repositories;

import app.models.Offer;
import app.exeptions.BadRequestException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.util.List;

@Primary
@Repository
@Transactional
public class OffersRepositoryJPA implements EntityRepository {

    @PersistenceContext
    protected EntityManager entityManager;

    @Override
    public List<Offer> findAll() {
        TypedQuery<Offer> query =
                this.entityManager.createQuery(
                        "select a from Offer a", Offer.class);

        return query.getResultList();
    }

    @Override
    public Offer findById(long id) {
        return this.entityManager.find(Offer.class, id);
    }



    @Override
    public Offer save(Object offer) {
        return this.entityManager.merge((Offer) offer);
    }

    @Override
    public Offer deleteById(long id) {
        Offer deletedOffer = findById(id);

        this.entityManager.remove(deletedOffer);

        return deletedOffer;
    }

//    @Override
//    public List<Offer> findByQuery(String jpqlName, Object... params) {
//        TypedQuery<Offer> query = this.entityManager.createQuery(jpqlName, Offer.class);
//
//        if(query == null) {
//            throw new BadRequestException("No query found with name: " + jpqlName);
//        }
//
//        for (int i = 0; i < params.length; i++) {
//            query.setParameter(i+1, params[i]);
//        }
//
//        return query.getResultList();
//    }

    @Override
    public List<Offer> findByQuery(String jpqlName, Object ...params) {
        TypedQuery<Offer> query = this.entityManager.createNamedQuery(jpqlName, Offer.class);

        if(query == null) {
            throw new BadRequestException("No query found with name: " + jpqlName);
        }

        // resolve all parameter values into the query
        for (int i = 0; i < params.length; i++) {
            query.setParameter(i+1, params[i]);
        }

        // execute the query and return the result
        return query.getResultList();
    }
}
