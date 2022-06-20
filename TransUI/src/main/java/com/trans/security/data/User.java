package com.trans.security.data;

import javax.imageio.ImageIO;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import lombok.Getter;
import lombok.Setter;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

@Entity(name = "applicationUsers")
public class User {
	
	public static final String QUERY_ALL_EMAILS = "allEmails";
	
	@Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE ,generator="sequence")
    @SequenceGenerator(name="sequence", sequenceName="sequence", allocationSize=1)
	@Getter
    private Long id;
   
    @Getter
    @Setter
    @Column(nullable = false, unique = true)
    private String username;

    @Getter
    @Setter
    @Column(nullable = false)
    private String password;
    
    @Getter
    @Setter
    @Column(nullable = false, unique = true)
    private String email;
    
    @Getter
    @Setter
    @Column(nullable = false, unique = true)
    private String token;
    
    @Getter
    @Setter
    private String firstName;
    
    @Getter
    @Setter
    private String lastName;
    
    @Getter
    @Setter
    private String userType;
    
    @Getter
    @Setter
    private String companyInfo;

    @Getter
    @Setter
    private String phoneNumber;

    @Column(length = Integer.MAX_VALUE)
    private byte[] imageBytes;
        
    public User() {
    	
    }
    
    public User(String username, String password, String email) {
    	this.username = username;
    	this.password = new BCryptPasswordEncoder().encode(password);
    	this.email = email;
    	Double randomNumber = Math.random();
    	this.token = new BCryptPasswordEncoder(31).encode(randomNumber.toString() + System.currentTimeMillis() + username);
    }
    
    public User(String username, String password, String email, String firstName, String lastName, String userType, String companyInfo) {
    	this(username, password, email);
    	this.firstName = firstName;
    	this.lastName = lastName;
    	this.userType = userType;
    	this.companyInfo = companyInfo;
    	
    }

    public BufferedImage getImage() throws IOException {
        if(imageBytes != null){
            InputStream in = new ByteArrayInputStream(imageBytes);
            return ImageIO.read(in);
        }else{
            return null;
        }

    }

    public void setImage(BufferedImage image) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ImageIO.write(image, "PNG" /* for instance */, out);
        imageBytes = out.toByteArray();
    }
    
}
