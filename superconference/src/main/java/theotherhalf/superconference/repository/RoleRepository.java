package theotherhalf.superconference.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import theotherhalf.superconference.domain.Conference;
import theotherhalf.superconference.domain.ENUMERATION_ROLES;
import theotherhalf.superconference.domain.CMSUser;
import theotherhalf.superconference.domain.UserClaims;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoleRepository extends SystemRepository<UserClaims, Long>
{
    @Query("SELECT c FROM UserClaims c WHERE c.conference = :cid AND c.user = :user AND c.role = :role")
    Optional<UserClaims> findByConferenceAndUserAndRole(@Param("cid") Conference conference, @Param("user") CMSUser user, @Param("role") ENUMERATION_ROLES role);

    @Query("SELECT c FROM UserClaims c WHERE c.conference = :cid AND c.role = :role")
    List<UserClaims> findByConferenceAndRole(@Param("cid") Conference conference, @Param("role") ENUMERATION_ROLES role);

    @Query("SELECT c from UserClaims c WHERE c.conference = :cid")
    List<UserClaims> findByConference(@Param("cid") Conference conference);
}
