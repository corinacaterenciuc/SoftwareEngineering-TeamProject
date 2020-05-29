package theotherhalf.superconference.repository;

import org.springframework.stereotype.Repository;
import theotherhalf.superconference.domain.AdminUser;
import theotherhalf.superconference.domain.CMSUser;
import theotherhalf.superconference.domain.UserClaims;

import javax.persistence.Table;

@Repository
@Table(name = "admins")
public interface AdminRepository extends SystemRepository<AdminUser, String>
{
    AdminUser findAdminUserByEmail(String email);
}
