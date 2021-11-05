public class BetterPerson {
   private final String lastName;
   private final String firstName;
   private final String middleName;
   private final String salutation;
   private final String suffix;
   private final String streetAddress;
   private final String city;
   private final String country;
   private final boolean isFemale;
   private final boolean isEmployed;
   private final boolean isHomewOwner;

   public BetterPerson(String newLastName, String newFirstName,
                 String newMiddleName, String newSalutation, 
                 String newSuffix, String newStreetAddress, 
                 String newCity, String newCountry, boolean newIsFemale,
                 boolean newIsEmployed, boolean newIsHomeOwner) {
      this.lastName = newLastName;
      this.firstName = newFirstName;
      this.middleName = newMiddleName;
      this.salutation = newSalutation;
      this.suffix = newSuffix;
      this.streetAddress = newStreetAddress;
      this.city = newCity;
      this.country = newCountry;
      this.isFemale = newIsFemale;
      this.isEmployed = newIsEmployed;
      this.isHomewOwner = newIsHomeOwner;
   }

   public static class PersonBuilder {
      private String nestedLastName;
      private String nestedFirstName;
      private String nestedMiddleName;
      private String nestedSalutation;
      private String nestedSuffix;
      private String nestedStreetAddress;
      private String nestedCity;
      private String nestedCountry;
      private boolean nestedIsFemale;
      private boolean nestedIsEmployed;
      private boolean nestedIsHomeOwner;

      public PersonBuilder(
         final String newFirstName,
         final String newCity,
         final String newCountry) {
         this.nestedFirstName = newFirstName;
         this.nestedCity = newCity;
         this.nestedCountry = newCountry;
      }

      public PersonBuilder lastName(String newLastName) {
         this.nestedLastName = newLastName;
         return this;
      }

      public PersonBuilder firstName(String newFirstName) {
         this.nestedFirstName = newFirstName;
         return this;
      }

      public PersonBuilder middleName(String newMiddleName) {
         this.nestedMiddleName = newMiddleName;
         return this;
      }

      public PersonBuilder salutation(String newSalutation) {
         this.nestedSalutation = newSalutation;
         return this;
      }

      public PersonBuilder suffix(String newSuffix) {
         this.nestedSuffix = newSuffix;
         return this;
      }

      public PersonBuilder streetAddress(String newStreetAddress) {
         this.nestedStreetAddress = newStreetAddress;
         return this;
      }

      public PersonBuilder city(String newCity) {
         this.nestedCity = newCity;
         return this;
      }

      public PersonBuilder state(String newCountry) {
         this.nestedCountry = newCountry;
         return this;
      }

      public PersonBuilder isFemale(boolean newIsFemale) {
         this.nestedIsFemale = newIsFemale;
         return this;
      }

      public PersonBuilder isEmployed(boolean newIsEmployed) {
         this.nestedIsEmployed = newIsEmployed;
         return this;
      }

      public PersonBuilder isHomeOwner(boolean newIsHomeOwner) {
         this.nestedIsHomeOwner = newIsHomeOwner;
         return this;
      }

      public BetterPerson createPerson() {
         return new BetterPerson(
            nestedLastName, nestedFirstName, nestedMiddleName,
            nestedSalutation, nestedSuffix,
            nestedStreetAddress, nestedCity, nestedCountry,
            nestedIsFemale, nestedIsEmployed, nestedIsHomeOwner);
      }
   }
}


    @Test
    public void testBuilder() throws Exception {
        BetterPerson p = new BetterPerson.PersonBuilder("John", "London", "UK")
            .lastName("Smith")
            .isFemale(false).createPerson();
        assertTrue(true);
    }