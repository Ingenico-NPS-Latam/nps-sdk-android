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


    public class Person extends SoapObject {
        public Person setFirstName(String v) {
            this.addProperty("FirstName",v);
            return this;
        }
        public Person setLastName(String v) {
            this.addProperty("LastName",v);
            return this;
        }
        public Person setPhoneNumber1(String v) {
            this.addProperty("PhoneNumber1",v);
            return this;
        }
        public Person setPhoneNumber2(String v) {
            this.addProperty("PhoneNumber2",v);
            return this;
        }
        public Person setGender(String v) {
            this.addProperty("Gender",v);
            return this;
        }
        public Person setDateOfBirth(String v) {
            this.addProperty("DateOfBirth",v);
            return this;
        }
        public Person setNationality(String v) {
            this.addProperty("Nationality",v);
            return this;
        }
        public Person setIdNumber(String v) {
            this.addProperty("IDNumber",v);
            return this;
        }
        public Person setIdType(String v) {
            this.addProperty("IDType", v);
            return this;
        }

        public String getFirstName() {
            return this.hasProperty("FirstName") ? this.getPropertyAsString("FirstName") : "";
        }
        public String getLastName() {
            return this.hasProperty("LastName") ? this.getPropertyAsString("LastName") : "";
        }
        public String getPhoneNumber1() {
            return this.hasProperty("PhoneNumber1") ? this.getPropertyAsString("PhoneNumber1") : "";
        }
        public String getPhoneNumber2() {
            return this.hasProperty("PhoneNumber2") ? this.getPropertyAsString("PhoneNumber2") : "";
        }
        public String getGender() {
            return this.hasProperty("Gender") ? this.getPropertyAsString("Gender") : "";
        }
        public String getDateOfBirth() {
            return this.hasProperty("Nationality") ? this.getPropertyAsString("DateOfBirth") : "";
        }
        public String getNationality() {
            return this.hasProperty("FirstName") ? this.getPropertyAsString("Nationality") : "";
        }
        public String getIdNumber() {
            return this.hasProperty("IDNumber") ? this.getPropertyAsString("IDNumber") : "";
        }
        public String getIdType() {
            return this.hasProperty("IDType") ? this.getPropertyAsString("IDType") : "";
        }

    }

    public class Address extends SoapObject {
        public Address setStreet(String v) {
            this.addProperty("Street", v);
            return this;
        }
        public Address setHouseNumber(String v) {
            this.addProperty("HouseNumber", v);
            return this;
        }
        public Address setAdditionalInfo(String v) {
            this.addProperty("AdditionalInfo", v);
            return this;
        }
        public Address setCity(String v) {
            this.addProperty("City", v);
            return this;
        }
        public Address setStateProvince(String v) {
            this.addProperty("StateProvince", v);
            return this;
        }
        public Address setCountry(String v) {
            this.addProperty("Country", v);
            return this;
        }
        public Address setZipCode(String v) {
            this.addProperty("ZipCode", v);
            return this;
        }

        public String getStreet() {
            return this.hasProperty("Street") ? this.getPropertyAsString("Street") : "";
        }
        public String getHouseNumber() {
            return this.hasProperty("Street") ? this.getPropertyAsString("HouseNumber"): "";
        }
        public String getAdditionalInfo() {
            return this.hasProperty("Street") ? this.getPropertyAsString("AdditionalInfo"): "";
        }
        public String getCity() {
            return this.hasProperty("Street") ? this.getPropertyAsString("City"): "";
        }
        public String getStateProvince() {
            return this.hasProperty("Street") ? this.getPropertyAsString("StateProvince"): "";
        }
        public String getCountry() {
            return this.hasProperty("Street") ? this.getPropertyAsString("Country"): "";
        }
        public String getZipCode() {
            return this.hasProperty("Street") ? this.getPropertyAsString("ZipCode"): "";
        }


    }
}
