package app.repositories;

import app.models.Bid;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
@Transactional
public class BidsRepositoryJPA implements  EntityRepository {

    @PersistenceContext
    protected EntityManager entityManager;

    @Override
    public List<Bid> findAll() {
        TypedQuery<Bid> query =
                this.entityManager.createQuery(
                        "select a from Bid a", Bid.class);

        return query.getResultList();
    }

    @Override
    public Bid findById(long id) {
        return this.entityManager.find(Bid.class, id);
    }

    @Override
    public Bid save(Object bid) {
        return this.entityManager.merge((Bid) bid);
    }

    @Override
    public Bid deleteById(long id) {
        Bid deletedBid = findById(id);

        this.entityManager.remove(deletedBid);

        return deletedBid;
    }

    @Override
    public List findByQuery(String jpqlName, Object... params) {
        return null;
    }
}
