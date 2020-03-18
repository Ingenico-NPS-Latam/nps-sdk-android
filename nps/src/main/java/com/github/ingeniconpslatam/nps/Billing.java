package com.github.ingeniconpslatam.nps;

import org.ksoap2.serialization.SoapObject;

/**
 * Created by fernando on 3/29/17.
 */

public class Billing {
    Person person;
    Address address;

    static public String IDTYPE_PASSPORT = "100";
    static public String IDTYPE_AR_DNI = "200";
    static public String IDTYPE_AR_CEDULA_IDENTIDAD = "201";
    static public String IDTYPE_AR_LIBRETA_CIVICA = "202";
    static public String IDTYPE_AR_LIBRETA_ENROLAMIENTO = "203";
    static public String IDTYPE_AR_CUIL = "204";
    static public String IDTYPE_AR_CUIT = "205";
    static public String IDTYPE_BR_RG = "300";
    static public String IDTYPE_BR_TITULO_ELEITOR = "301";
    static public String IDTYPE_BR_CPF = "302";
    static public String IDTYPE_BR_CNPJ = "303";
    static public String IDTYPE_US_SSN = "400";
    static public String IDTYPE_CO_CEDULA_CIUDADANIA = "500";
    static public String IDTYPE_CO_TARJETA_IDENTIDAD = "501";
    static public String IDTYPE_CO_CEDULA_EXTRANJERIA = "502";

    public Person getPerson() {
        if(this.person == null) {
            this.person = this.new Person();
        }
        return this.person;
    }
    public Address getAddress() {
        if(this.address == null) {
            this.address = this.new Address();
        }
        return this.address;
    }


    public class Person extends SoapObject implements Hydratable{

        private String firstName = "";
        private String lastName = "";
        private String phoneNumber1 = "";
        private String phoneNumber2 = "";
        private String gender = "";
        private String dateOfBirth = "";
        private String nationality = "";
        private String idNumber = "";
        private String idType = "";


        public Person setFirstName(String firstName) {
            this.firstName = firstName;
            this.addProperty("FirstName",firstName);
            return this;
        }
        public Person setLastName(String lastName) {
            this.lastName = lastName;
            this.addProperty("LastName",lastName);
            return this;
        }
        public Person setPhoneNumber1(String phoneNumber1) {
            this.phoneNumber1 = phoneNumber1;
            this.addProperty("PhoneNumber1",phoneNumber1);
            return this;
        }
        public Person setPhoneNumber2(String phoneNumber2) {
            this.phoneNumber2 = phoneNumber2;
            this.addProperty("PhoneNumber2",phoneNumber2);
            return this;
        }

        public Person setGender(String gender) {
            this.gender = gender;
            this.addProperty("Gender",gender);
            return this;
        }
        public Person setDateOfBirth(String dateOfBirth) {
            this.dateOfBirth = dateOfBirth;
            this.addProperty("DateOfBirth",dateOfBirth);
            return this;
        }
        public Person setNationality(String nationality) {
            this.nationality = nationality;
            this.addProperty("Nationality",nationality);
            return this;
        }
        public Person setIdNumber(String idNumber) {
            this.idNumber = idNumber;
            this.addProperty("IDNumber",idNumber);
            return this;
        }
        public Person setIdType(String idType) {
            this.idType = idType;
            this.addProperty("IDType", idType);
            return this;
        }

        public String getFirstName() {
            return this.firstName;
        }
        public String getLastName() {
            return this.lastName;
        }
        public String getPhoneNumber1() {
            return this.phoneNumber1;
        }
        public String getPhoneNumber2() {
            return this.phoneNumber2;
        }
        public String getGender() {
            return this.gender;
        }
        public String getDateOfBirth() {
            return dateOfBirth;
        }
        public String getNationality() {
            return nationality;
        }
        public String getIdNumber() {
            return idNumber;
        }
        public String getIdType() {
            return this.idType;
        }


        @Override
        public void hydrate(SoapObject data) {
            this.setFirstName(data.getPrimitivePropertySafelyAsString("FirstName"));
            this.setLastName(data.getPrimitivePropertySafelyAsString("LastName"));
            this.setPhoneNumber1(data.getPrimitivePropertySafelyAsString("PhoneNumber1"));
            this.setPhoneNumber2(data.getPrimitivePropertySafelyAsString("PhoneNumber2"));
            this.setGender(data.getPrimitivePropertySafelyAsString("Gender"));
            this.setDateOfBirth(data.getPrimitivePropertySafelyAsString("DateOfBirth"));
            this.setNationality(data.getPrimitivePropertySafelyAsString("Nationality"));
            this.setIdNumber(data.getPrimitivePropertySafelyAsString("IDNumber"));
            this.setIdType(data.getPrimitivePropertySafelyAsString("IDType"));
        }
    }

    public class Address extends SoapObject implements Hydratable{

        private String street = "";
        private String houseNumber = "";
        private String additionalInfo = "";
        private String city = "";
        private String stateProvince = "";
        private String country = "";
        private String zipCode = "";


        public Address setStreet(String street) {
            this.street = street;
            this.addProperty("Street", street);
            return this;
        }
        public Address setHouseNumber(String houseNumber) {
            this.houseNumber = houseNumber;
            this.addProperty("HouseNumber", houseNumber);
            return this;
        }
        public Address setAdditionalInfo(String additionalInfo) {
            this.additionalInfo = additionalInfo;
            this.addProperty("AdditionalInfo", additionalInfo);
            return this;
        }
        public Address setCity(String city) {
            this.city = city;
            this.addProperty("City", city);
            return this;
        }
        public Address setStateProvince(String stateProvince) {
            this.stateProvince = stateProvince;
            this.addProperty("StateProvince", stateProvince);
            return this;
        }
        public Address setCountry(String country) {
            this.country = country;
            this.addProperty("Country", country);
            return this;
        }
        public Address setZipCode(String zipCode) {
            this.zipCode = zipCode;
            this.addProperty("ZipCode", zipCode);
            return this;
        }

        public String getStreet() {
            return this.street;
        }
        public String getHouseNumber() {
            return this.houseNumber;
        }
        public String getAdditionalInfo() {
            return this.additionalInfo;
        }
        public String getCity() {
            return this.city;
        }
        public String getStateProvince() {
            return this.stateProvince;
        }
        public String getCountry() {
            return this.country;
        }
        public String getZipCode() {
            return this.zipCode;
        }

        @Override
        public void hydrate(SoapObject data) {
            this.setStreet(data.getPropertySafelyAsString("Street"));
            this.setHouseNumber(data.getPropertySafelyAsString("HouseNumber"));
            this.setAdditionalInfo(data.getPropertySafelyAsString("AdditionalInfo"));
            this.setCity(data.getPropertySafelyAsString("City"));
            this.setStateProvince(data.getPropertySafelyAsString("StateProvince"));
            this.setCountry(data.getPropertySafelyAsString("Country"));
            this.setZipCode(data.getPropertySafelyAsString("ZipCode"));
        }
    }
}
