package com.wizeline.maven.LearningJava.controller;

import com.wizeline.maven.LearningJava.model.BookBean;
import com.wizeline.maven.LearningJava.model.PublicApiBean;
import com.wizeline.maven.LearningJava.model.XmlBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Date;
import java.util.Map;
import java.util.logging.Logger;

import static com.wizeline.maven.LearningJava.utils.Utils.convertToMap;

@RequestMapping("/jaxb")
@RestController
public class JAXBController {

    @Autowired
    RestTemplate restTemplate;

    private static final Logger LOGGER = Logger.getLogger(JAXBController.class.getName());
    @GetMapping("/getXML")
    public ResponseEntity<XmlBean> loginUser(){
        LOGGER.info("Test JAXB");
        XmlBean response = new XmlBean();
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Content-Type", "application/json; charset=UTF-8");
        try{
            marshallBook();
            response.setData(unmarshall("./book.xml"));
        } catch (JAXBException | IOException e){
            e.printStackTrace();
        }
        return new ResponseEntity<XmlBean>(response, responseHeaders, HttpStatus.OK);
    }

    @GetMapping("/getXMLAPIPublica")
    public ResponseEntity<XmlBean> listUsers(@RequestParam String id){
        LOGGER.info("Consumo de API publica https://jsonplaceholder.typicode.com/todos/" + id);
        XmlBean response = new XmlBean();
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Content-Type", "application/json; charset=UTF-8");
        HttpEntity <String> entity = new HttpEntity<String>(responseHeaders);
        String data = restTemplate.exchange("https://jsonplaceholder.typicode.com/todos/"+id , HttpMethod.GET, entity, String.class).getBody();

        try{
            marshallPublicApi(data);
            response.setData(unmarshallPublicApi("./usersApi.xml"));
        } catch (JAXBException | IOException e){
            e.printStackTrace();
        }
        return new ResponseEntity<XmlBean>(response, responseHeaders, HttpStatus.OK);
    }

    public void marshallBook() throws JAXBException, IOException {
        BookBean book = new BookBean();
        book.setId(1L);
        book.setName("Book 1");
        book.setAuthor("Author 1");
        book.setDate(new Date());
        JAXBContext context = JAXBContext.newInstance(BookBean.class);
        Marshaller mar = context.createMarshaller();
        mar.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        mar.marshal(book, new File("./book.xml"));
    }

    public void marshallPublicApi(String data) throws JAXBException, IOException {
        PublicApiBean api = new PublicApiBean();
        Map<String, String> apimap = convertToMap(data);
        api.setUserId(Long.parseLong(apimap.get("userId")));
        api.setId(Long.parseLong(apimap.get("id")));
        api.setTitle(apimap.get("title"));
        api.setCompleted(Boolean.parseBoolean(apimap.get("completed")));
        JAXBContext context = JAXBContext.newInstance(PublicApiBean.class);
        Marshaller mar = context.createMarshaller();
        mar.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        mar.marshal(api, new File("./usersApi.xml"));
    }

    public BookBean unmarshall(String fileName) throws JAXBException, IOException {
        JAXBContext context = JAXBContext.newInstance(BookBean.class);
        return (BookBean) context.createUnmarshaller().unmarshal(new FileReader(fileName));
    }

    public PublicApiBean unmarshallPublicApi(String fileName) throws JAXBException, IOException {
        JAXBContext context = JAXBContext.newInstance(PublicApiBean.class);
        return (PublicApiBean) context.createUnmarshaller().unmarshal(new FileReader(fileName));
    }
}
