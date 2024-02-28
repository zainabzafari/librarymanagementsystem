package libraryBackend;

public class Member {
    private String memberId;
    private String firstName;
    private String lastName;
    private String address;
    private String phoneNo;
    private String email;
    private String username;
    private String password;
    
    // Constructors
    public Member(String firstName, String lastName,  String email, String phoneNo, String address) {
    	this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.phoneNo = phoneNo;
        this.email = email;
    }
    
    public Member(String firstName, String lastName,  String email, String phoneNo, String address, String username, String password) {
    	this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.phoneNo = phoneNo;
        this.email = email;
        this.username = username;
        this.password = password;
    }

    public Member(String memberId, String firstName, String lastName,  String email, String phoneNo, String address) {
        this.memberId = memberId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.phoneNo = phoneNo;
        this.email = email;
    }

    // Getters and setters
    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}

