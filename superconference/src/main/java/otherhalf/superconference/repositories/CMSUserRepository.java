package otherhalf.superconference.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import otherhalf.superconference.entities.CMSUser;

@Repository
public interface CMSUserRepository extends JpaRepository<CMSUser, Integer>
{
    CMSUser findByEmail(String email);
}
