package com.sbm.rcu.domain;

import java.util.List;

/**
 * Un "exposé" minimal de votre document MongoDB, ne contenant que les champs métier
 * nécessaires pour la restitution JSON. Tous les champs hors scope (ex: metadata, _id...)
 * sont omis pour obtenir un JSON propre et réduit.
 */
public class ExposedOneCustomer {

    // --- Partie "payload" depuis golden_record.payload ---
    private String lastName; // ex: "IGOULALENE"
    private String firstName; // ex: "Robert"
    private String birthDate; // ex: "1980-01-01"
    private String lang; // ex: "IT"
    private List<Phone> phones;
    private List<Email> emails;
    private List<Address> addresses;
    private Boolean isVip; // ex: true

    // --- Partie "hotel" ---
    private List<HotelEntry> hotel;

    // --- Partie "restauration" ---
    private List<RestaurationEntry> restauration;

    // --- Getters / Setters ---

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public List<Phone> getPhones() {
        return phones;
    }

    public void setPhones(List<Phone> phones) {
        this.phones = phones;
    }

    public List<Email> getEmails() {
        return emails;
    }

    public void setEmails(List<Email> emails) {
        this.emails = emails;
    }

    public List<Address> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<Address> addresses) {
        this.addresses = addresses;
    }

    public Boolean getIsVip() {
        return isVip;
    }

    public void setIsVip(Boolean isVip) {
        this.isVip = isVip;
    }

    public List<HotelEntry> getHotel() {
        return hotel;
    }

    public void setHotel(List<HotelEntry> hotel) {
        this.hotel = hotel;
    }

    public List<RestaurationEntry> getRestauration() {
        return restauration;
    }

    public void setRestauration(List<RestaurationEntry> restauration) {
        this.restauration = restauration;
    }

    /**
     * Sous-classe pour représenter un téléphone dans la partie "phones" du payload.
     */
    public static class Phone {

        private String type; // ex: "PRO" ou "HOME"
        private String number; // ex: "+33-123-456"

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getNumber() {
            return number;
        }

        public void setNumber(String number) {
            this.number = number;
        }
    }

    /**
     * Sous-classe pour représenter un email dans la partie "emails" du payload.
     */
    public static class Email {

        private String type; // ex: "PRO" ou "HOME"
        private String value; // ex: "john.doe@company.com"

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }

    /**
     * Sous-classe pour représenter une adresse dans la partie "addresses" du payload.
     */
    public static class Address {

        private String type; // ex: "HOME"
        private String zipCode; // ex: "98000"
        private String city; // ex: "Monaco"
        private String country; // ex: "MC"
        private String line1;
        private String line2;
        private String line3;

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getZipCode() {
            return zipCode;
        }

        public void setZipCode(String zipCode) {
            this.zipCode = zipCode;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public String getLine1() {
            return line1;
        }

        public void setLine1(String line1) {
            this.line1 = line1;
        }

        public String getLine2() {
            return line2;
        }

        public void setLine2(String line2) {
            this.line2 = line2;
        }

        public String getLine3() {
            return line3;
        }

        public void setLine3(String line3) {
            this.line3 = line3;
        }
    }

    // ---------------------------------------------------------------
    //                      HOTEL
    // ---------------------------------------------------------------

    /**
     * Sous-classe pour chaque réservation “hotel”.
     * Inclut le champ "cancelled" s'il existe, ainsi que l'objet interne "hotel".
     */
    public static class HotelEntry {

        private String name; // ex: 'HERMITAGE'
        private String date; // ex: '2025-02-09'
        private Double totalAmount; // ex: 793
        //        private String clientId; // ex: 'opera_client_130'
        private String leaveDate; // ex: '2025-02-12T18:36:28.998866'
        //        private HotelNested hotel; // { name: 'HERMITAGE', id: '...' }
        private Integer guestCount; // ex: 1
        private String arrivalDate; // ex: '2025-02-10T18:36:28.998831'

        private HotelCancelled cancelled; // { cancel_reason: '...', is_it_cancelled: true, ... }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public Double getTotalAmount() {
            return totalAmount;
        }

        public void setTotalAmount(Double totalAmount) {
            this.totalAmount = totalAmount;
        }

        /*public String getClientId() {
            return clientId;
        }

        public void setClientId(String clientId) {
            this.clientId = clientId;
        }*/

        public String getLeaveDate() {
            return leaveDate;
        }

        public void setLeaveDate(String leaveDate) {
            this.leaveDate = leaveDate;
        }

        /*        public HotelNested getHotel() {
            return hotel;
        }

        public void setHotel(HotelNested hotel) {
            this.hotel = hotel;
        }*/

        public Integer getGuestCount() {
            return guestCount;
        }

        public void setGuestCount(Integer guestCount) {
            this.guestCount = guestCount;
        }

        public String getArrivalDate() {
            return arrivalDate;
        }

        public void setArrivalDate(String arrivalDate) {
            this.arrivalDate = arrivalDate;
        }

        public HotelCancelled getCancelled() {
            return cancelled;
        }

        public void setCancelled(HotelCancelled cancelled) {
            this.cancelled = cancelled;
        }
    }

    /**
     * Sous-classe pour représenter la portion "hotel" -> "info.hotel" (ex: { name, id }).
     */
    public static class HotelNested {

        private String name;
        private String id;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
    }

    /**
     * Sous-classe pour représenter l'objet "cancelled" dans la section hotel.
     */
    public static class HotelCancelled {

        private String cancel_reason; // ex: "Client request"
        private boolean is_it_cancelled; // ex: true
        private String cancel_date; // ex: "2025-02-09T18:37:13.334589"

        public String getCancel_reason() {
            return cancel_reason;
        }

        public void setCancel_reason(String cancel_reason) {
            this.cancel_reason = cancel_reason;
        }

        public boolean isIs_it_cancelled() {
            return is_it_cancelled;
        }

        public void setIs_it_cancelled(boolean is_it_cancelled) {
            this.is_it_cancelled = is_it_cancelled;
        }

        public String getCancel_date() {
            return cancel_date;
        }

        public void setCancel_date(String cancel_date) {
            this.cancel_date = cancel_date;
        }
    }

    // ---------------------------------------------------------------
    //                      RESTAURATION
    // ---------------------------------------------------------------

    /**
     * Sous-classe pour chaque réservation “restauration”.
     */
    public static class RestaurationEntry {

        private String name; // ex: 'COYA'
        private String date; // ex: '2025-02-09'
        private Double depositAmount; // ex: 12
        private Double totalAmount; // ex: 244
        private String clientId; // ex: 'seven_rooms_client_127'
        private String shift; // ex: 'DINNER'
        private Integer guestCount; // ex: 3
        private String arrivalDate; // ex: '2025-02-10T18:36:16.902882'

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public Double getDepositAmount() {
            return depositAmount;
        }

        public void setDepositAmount(Double depositAmount) {
            this.depositAmount = depositAmount;
        }

        public Double getTotalAmount() {
            return totalAmount;
        }

        public void setTotalAmount(Double totalAmount) {
            this.totalAmount = totalAmount;
        }

        public String getClientId() {
            return clientId;
        }

        public void setClientId(String clientId) {
            this.clientId = clientId;
        }

        public String getShift() {
            return shift;
        }

        public void setShift(String shift) {
            this.shift = shift;
        }

        public Integer getGuestCount() {
            return guestCount;
        }

        public void setGuestCount(Integer guestCount) {
            this.guestCount = guestCount;
        }

        public String getArrivalDate() {
            return arrivalDate;
        }

        public void setArrivalDate(String arrivalDate) {
            this.arrivalDate = arrivalDate;
        }
    }
}
