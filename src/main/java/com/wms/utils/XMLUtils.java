package com.wms.utils;

import javax.xml.bind.*;
import javax.xml.namespace.QName;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamWriter;
import java.io.ByteArrayOutputStream;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;

/**
 * Created by duyot on 7/11/2016.
 */
public class XMLUtils {
    public static StringWriter sw = new StringWriter();


    public static void main(String[] args) {

    }

    public static String objectToXMLString(Object object){
        try {
            JAXBContext jaxbContext   = JAXBContext.newInstance(object.getClass());
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            jaxbMarshaller.marshal(object,sw);
            return sw.toString();
        } catch (JAXBException e) {
            e.printStackTrace();
            return "";
        }
    }
    public static <T> String objectToSOAPXMLString(T t,Class objectClass,String wrapperName,String wrapperMethod,String attribute){
        QName root = new QName("return");
        JAXBElement<T> je = new JAXBElement<T>(root, objectClass, t);

        XMLOutputFactory xof = null;
        XMLStreamWriter xsw = null;
        ByteArrayOutputStream out =new ByteArrayOutputStream();
        try {
            xof = XMLOutputFactory.newFactory();
            xsw = xof.createXMLStreamWriter(out);
            xsw.writeStartDocument();
            xsw.writeStartElement("Soap", "Envelope", "http://schemas.xmlsoap.org/soap/envelope/");
            xsw.writeStartElement("Soap", "Body", "http://schemas.xmlsoap.org/soap/envelope/");
            xsw.writeStartElement(wrapperName, wrapperMethod, attribute);

            JAXBContext jc = JAXBContext.newInstance(objectClass);
            Marshaller marshaller = jc.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FRAGMENT, true);
            marshaller.marshal(je, xsw);
            xsw.writeEndDocument();

            return out.toString();

        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }finally {
            try {
                xsw.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }



    public static <T> T xmlSOAPToObject(String xmlContent,String rootTag,Class objectClass){
        XMLInputFactory xmlInputFactory = XMLInputFactory.newFactory();
        Reader readerXML = new StringReader(xmlContent);
        XMLStreamReader xsr = null;
        try {
            xsr = xmlInputFactory.createXMLStreamReader(readerXML);

            xsr.nextTag();
            while(!xsr.getLocalName().equals(rootTag)) {
                xsr.nextTag();
            }

            JAXBContext jc = JAXBContext.newInstance(objectClass);
            Unmarshaller unmarshaller = jc.createUnmarshaller();
            JAXBElement<T> jb = unmarshaller.unmarshal(xsr, objectClass);

            return jb.getValue();

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }finally {
            try {
                xsr.close();
                readerXML.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    public static <T> T xmlToObject(String xmlContent,Class objectClass){
        JAXBContext jaxbContext = null;
        try {
            jaxbContext = JAXBContext.newInstance(objectClass);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

            StringReader reader = new StringReader(xmlContent);
            return (T) unmarshaller.unmarshal(reader);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return null;
    }

//    public static void main(String[] args) {
////        RegisterRequest registerRequest = new RegisterRequest();
////        registerRequest.setUserName("duyot");
////        registerRequest.setApplication("viettalk");
////        registerRequest.setChannel("chanel_a");
////        registerRequest.setMsisdn("84975114692");
////        registerRequest.setPackageName("V2");
////        registerRequest.setNote("note");
////        registerRequest.setUserIP("127.0.0.1");
////        String xmlRR = XMLUtils.objectToXMLString(registerRequest);
////        System.out.println(xmlRR);
////        System.out.println(xmlToObject(xmlRR).getUserName());
//        User u = new User("duyot",1,2);
//        String userXML = XMLUtils.objectToXMLString(u);
//        System.out.println(userXML);
//        User userFromXML = XMLUtils.xmlToObject(userXML,User.class);
//
//    }
}
