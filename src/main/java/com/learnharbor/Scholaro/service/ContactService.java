package com.learnharbor.Scholaro.service;

import com.learnharbor.Scholaro.constants.ScholaroConstants;
import com.learnharbor.Scholaro.model.Contact;
import com.learnharbor.Scholaro.repository.ContactRepository;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class ContactService {

    ContactRepository contactRepository;

    @Autowired
    public ContactService(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    public boolean saveMessageDetails(Contact contact){

         contact.setStatus(ScholaroConstants.OPEN);
         Contact result = contactRepository.save(contact);
         boolean isSaved = false;
          if(result != null && result.getContactId() > 0){
              isSaved = true;
          }
          return isSaved;
    }

    public List<Contact> findMsgsWithOpenStatus(){
        List<Contact> msgs = contactRepository.findByStatus(ScholaroConstants.OPEN);
        return msgs;
    }

    public boolean updateMsgStatus(int contactId){
        boolean isUpdated = false;
        Optional<Contact> contact = contactRepository.findById(contactId);
        contact.ifPresent(contact1 -> {
            contact1.setStatus(ScholaroConstants.CLOSE);
        });
        Contact result = contactRepository.save(contact.get());
        if(result != null && result.getUpdatedBy() != null){
            isUpdated = true;
        }
        return isUpdated;
    }
}
