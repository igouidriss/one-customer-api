package com.sbm.rcu.service.mapper;

import com.sbm.rcu.domain.ExposedOneCustomer;
import java.util.ArrayList;
import java.util.List;
import org.bson.Document;

public class ExposedOneCustomerMapper {

    /**
     * Convertit un Document MongoDB brut en un ExposedOneCustomer,
     * en ne gardant que les champs métier utiles.
     */
    public static ExposedOneCustomer fromDocument(Document doc) {
        ExposedOneCustomer exposed = new ExposedOneCustomer();

        // 1) Récupérer la partie "payload" dans golden_record.payload
        Document goldenRecord = doc.get("golden_record", Document.class);
        if (goldenRecord != null) {
            Document payload = goldenRecord.get("payload", Document.class);
            if (payload != null) {
                exposed.setLastName(payload.getString("lastName"));
                exposed.setFirstName(payload.getString("firstName"));
                exposed.setBirthDate(payload.getString("birthDate"));
                exposed.setLang(payload.getString("lang"));
                exposed.setIsVip(payload.getBoolean("isVip"));

                // phones
                List<Document> phoneDocs = (List<Document>) payload.get("phones");
                if (phoneDocs != null) {
                    List<ExposedOneCustomer.Phone> phoneList = new ArrayList<>();
                    for (Document phoneDoc : phoneDocs) {
                        ExposedOneCustomer.Phone p = new ExposedOneCustomer.Phone();
                        p.setType(phoneDoc.getString("type"));
                        p.setNumber(phoneDoc.getString("number"));
                        phoneList.add(p);
                    }
                    exposed.setPhones(phoneList);
                }

                // emails
                List<Document> emailDocs = (List<Document>) payload.get("emails");
                if (emailDocs != null) {
                    List<ExposedOneCustomer.Email> emailList = new ArrayList<>();
                    for (Document emailDoc : emailDocs) {
                        ExposedOneCustomer.Email e = new ExposedOneCustomer.Email();
                        e.setType(emailDoc.getString("type"));
                        e.setValue(emailDoc.getString("value"));
                        emailList.add(e);
                    }
                    exposed.setEmails(emailList);
                }

                // addresses
                List<Document> addressDocs = (List<Document>) payload.get("addresses");
                if (addressDocs != null) {
                    List<ExposedOneCustomer.Address> addressList = new ArrayList<>();
                    for (Document addressDoc : addressDocs) {
                        ExposedOneCustomer.Address a = new ExposedOneCustomer.Address();
                        a.setType(addressDoc.getString("type"));
                        a.setZipCode(addressDoc.getString("zipCode"));
                        a.setCity(addressDoc.getString("city"));
                        a.setCountry(addressDoc.getString("country"));
                        a.setLine1(addressDoc.getString("line1"));
                        a.setLine2(addressDoc.getString("line2"));
                        a.setLine3(addressDoc.getString("line3"));
                        addressList.add(a);
                    }
                    exposed.setAddresses(addressList);
                }
            }
        }

        // 2) Récupérer la liste "hotel"
        List<Document> hotelDocs = (List<Document>) doc.get("hotel");
        if (hotelDocs != null) {
            List<ExposedOneCustomer.HotelEntry> hotelList = new ArrayList<>();
            for (Document hDoc : hotelDocs) {
                // la partie "info" contient date, totalAmount, etc.
                Document info = hDoc.get("info", Document.class);
                if (info != null) {
                    ExposedOneCustomer.HotelEntry he = new ExposedOneCustomer.HotelEntry();
                    // On peut par exemple reprendre "info.hotel.name" comme "name"
                    Document hotelNested = info.get("hotel", Document.class);

                    // => he.setName(...) : si vous voulez un champ racine "name"
                    //    ou vous lisez depuis info.hotel.name
                    if (hotelNested != null) {
                        he.setName(hotelNested.getString("name"));
                        // ... ou vous pouvez renommer ce champ si besoin
                        ExposedOneCustomer.HotelNested hn = new ExposedOneCustomer.HotelNested();
                        hn.setName(hotelNested.getString("name"));
                        hn.setId(hotelNested.getString("id"));
                        he.setHotel(hn);
                    }

                    he.setDate(info.getString("date"));
                    he.setTotalAmount(safeDouble(info.get("totalAmount")));
                    he.setClientId(info.getString("clientId"));
                    he.setLeaveDate(info.getString("leaveDate"));
                    he.setGuestCount(info.getInteger("guestCount"));
                    he.setArrivalDate(info.getString("arrivalDate"));

                    // cancelled
                    Document cancelledDoc = hDoc.get("cancelled", Document.class);
                    if (cancelledDoc != null) {
                        ExposedOneCustomer.HotelCancelled hc = new ExposedOneCustomer.HotelCancelled();
                        hc.setCancel_reason(cancelledDoc.getString("cancel_reason"));
                        hc.setIs_it_cancelled(cancelledDoc.getBoolean("is_it_cancelled", false));
                        hc.setCancel_date(cancelledDoc.getString("cancel_date"));
                        he.setCancelled(hc);
                    }

                    hotelList.add(he);
                }
            }
            exposed.setHotel(hotelList);
        }

        // 3) Récupérer la liste "restauration"
        List<Document> restoDocs = (List<Document>) doc.get("restauration");
        if (restoDocs != null) {
            List<ExposedOneCustomer.RestaurationEntry> restoList = new ArrayList<>();
            for (Document rDoc : restoDocs) {
                // la partie "info" contient date, depositAmount, totalAmount...
                Document info = rDoc.get("info", Document.class);
                if (info != null) {
                    ExposedOneCustomer.RestaurationEntry re = new ExposedOneCustomer.RestaurationEntry();
                    Document restaurant = info.get("restaurant", Document.class);
                    if (restaurant != null) {
                        re.setName(restaurant.getString("name"));
                    }

                    re.setDate(info.getString("date"));
                    re.setDepositAmount(safeDouble(info.get("depositAmount")));
                    re.setTotalAmount(safeDouble(info.get("totalAmount")));
                    re.setClientId(info.getString("clientId"));
                    re.setShift(info.getString("shift"));
                    re.setGuestCount(info.getInteger("guestCount"));
                    re.setArrivalDate(info.getString("arrivalDate"));

                    restoList.add(re);
                }
            }
            exposed.setRestauration(restoList);
        }

        // 4) Retourner l'objet "propre"
        return exposed;
    }

    /**
     * Petit utilitaire pour convertir un Object en Double
     * (éviter les ClassCastExceptions si c'est un int).
     */
    private static Double safeDouble(Object value) {
        if (value == null) return null;
        if (value instanceof Number) {
            return ((Number) value).doubleValue();
        }
        return null;
    }
}
