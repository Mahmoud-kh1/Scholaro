package com.learnharbor.Scholaro.service;

import com.learnharbor.Scholaro.model.Contact;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ContactService {


    public boolean saveMessageDetails(Contact contact){
         boolean isSaved = true;
         // TODO - Need to presist the data into DB

         log.info(contact.toString());
         return isSaved;
    }
}
