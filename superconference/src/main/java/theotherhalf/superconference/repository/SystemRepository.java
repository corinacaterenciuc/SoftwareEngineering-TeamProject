package theotherhalf.superconference.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import theotherhalf.superconference.domain.CMSUser;

import java.util.List;
import java.util.UUID;

@NoRepositoryBean
public interface SystemRepository<E, ID> extends JpaRepository<E, ID>
{
}
