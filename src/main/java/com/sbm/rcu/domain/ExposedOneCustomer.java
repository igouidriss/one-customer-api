package com.sbm.rcu.domain;

import java.util.List;

public class ExposedOneCustomer {

    private String lastName;
    private String firstName;
    private String birthDate;
    private String lang;
    private List<Phone> phones;
    private List<Email> emails;
    private List<Address> addresses;
    private Boolean isVip;
    private List<HotelEntry> hotel;
    private List<RestaurationEntry> restauration;

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

    public static class Phone {

        private String type;
        private String number;

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

    public static class Email {

        private String type;
        private String value;

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

    public static class Address {

        private String type;
        private String zipCode;
        private String city;
        private String country;
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

    public static class HotelEntry {

        private String name;
        private String date;
        private Double totalAmount;
        private String leaveDate;
        private Integer guestCount;
        private String arrivalDate;
        private HotelCancelled cancelled;

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

        public String getLeaveDate() {
            return leaveDate;
        }

        public void setLeaveDate(String leaveDate) {
            this.leaveDate = leaveDate;
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

        public HotelCancelled getCancelled() {
            return cancelled;
        }

        public void setCancelled(HotelCancelled cancelled) {
            this.cancelled = cancelled;
        }
    }

    public static class HotelCancelled {

        private String cancel_reason;
        private boolean is_it_cancelled;
        private String cancel_date;

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

    public static class RestaurationEntry {

        private String name;
        private String date;
        private Double depositAmount;
        private Double totalAmount;
        private String clientId;
        private String shift;
        private Integer guestCount;
        private String arrivalDate;

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
