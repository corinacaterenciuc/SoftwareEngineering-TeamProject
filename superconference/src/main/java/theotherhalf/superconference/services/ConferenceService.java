package theotherhalf.superconference.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import theotherhalf.superconference.domain.Conference;
import theotherhalf.superconference.domain.User;
import theotherhalf.superconference.repository.ConferenceRepository;
import theotherhalf.superconference.repository.RoleRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class ConferenceService
{
    @Autowired
    private ConferenceRepository repository;

    public ConferenceService()
    {

    }


    public boolean add(Conference conference)
    {
        if (null == conference.getID())
        {
            conference.setID(new Random().nextLong());
        }
        this.repository.save(conference);
        return true;
        //return this.repository.(user);
    }

    public boolean delete(Long ID)
    {
        this.repository.deleteById(ID);
        return true;
    }

/*    @Transactional
    public boolean update(Long ID, Conference conference)
    {
        Conference toUpdate = this.repository.findById(ID).orElse(null);
        if(null == toUpdate)
        {
            return false;
        }
        toUpdate.setID(conference.getID());
        toUpdate.setTitle(conference.getTitle());
        toUpdate.setDescription(conference.getDescription());
        toUpdate.setFirstDeadline(conference.getFirstDeadline());
        toUpdate.setSecondDeadline(conference.getSecondDeadline());
        toUpdate.setThirdDeadline(conference.getThirdDeadline());
        return true;
    }*/

    public List<Conference> getAll()
    {
        return this.repository.findAll();
    }

    public Optional<Conference> findById(Long conferenceID)
    {
        return this.repository.findById(conferenceID);
    }
}
