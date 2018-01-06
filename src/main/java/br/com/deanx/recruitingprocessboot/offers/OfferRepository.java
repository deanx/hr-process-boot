package br.com.deanx.recruitingprocessboot.offers;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface OfferRepository extends CrudRepository<Offer, Long> {
    List<Offer> findById(long id);

    Offer save(Offer offer);
}
