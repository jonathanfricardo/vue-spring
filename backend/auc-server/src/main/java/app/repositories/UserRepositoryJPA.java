package app.repositories;

import app.exeptions.BadRequestException;
import app.models.Offer;
import app.models.User;
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
public class UserRepositoryJPA implements EntityRepository<User> {

    @PersistenceContext
    protected EntityManager entityManager;

    @Override
    public User save(User user) {
        return this.entityManager.merge(user);
    }

    @Override
    public List<User> findAll() {
        TypedQuery<User> query =
                this.entityManager.createQuery(
                        "select a from User a", User.class);
        return query.getResultList();
    }

    @Override
    public User findById(long id) {
        return this.entityManager.find(User.class, id);
    }

    @Override
    public User deleteById(long id) {
        User user = this.findById(id);

        this.entityManager.remove(user);

        user.setName("Truly delete this user");
        return user;
    }

    @Override
    public List<User> findByQuery(String jpqlName, Object ...params) {
        TypedQuery<User> query = this.entityManager.createNamedQuery(jpqlName, User.class);

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
