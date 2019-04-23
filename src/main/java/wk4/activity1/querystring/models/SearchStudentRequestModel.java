package wk4.activity1.querystring.models;

/*
   This Model is NOT CORRECT! DO NOT DO THIS ON YOUR HOMEWORK! THIS IS JUST FOR DEMONSTRATION PURPOSES
 */

public class SearchStudentRequestModel {
   private String email;
   private String sessionID;
   private int id;
   private String firstName;
   private String lastName;
   private String standing;

   public SearchStudentRequestModel() {

   }

   public String getEmail() {
      return email;
   }

   public void setEmail(String email) {
      this.email = email;
   }

   public String getSessionID() {
      return sessionID;
   }

   public void setSessionID(String sessionID) {
      this.sessionID = sessionID;
   }

   public int getId() {
      return id;
   }

   public void setId(int id) {
      this.id = id;
   }

   public String getFirstName() {
      return firstName;
   }

   public void setFirstName(String firstName) {
      this.firstName = firstName;
   }

   public String getLastName() {
      return lastName;
   }

   public void setLastName(String lastName) {
      this.lastName = lastName;
   }

   public String getStanding() {
      return standing;
   }

   public void setStanding(String standing) {
      this.standing = standing;
   }
}
