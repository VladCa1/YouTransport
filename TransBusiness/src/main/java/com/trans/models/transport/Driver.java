package com.trans.models.transport;

import javax.imageio.ImageIO;
import javax.persistence.*;

import com.trans.models.agent.FTPAgent;
import lombok.Getter;
import lombok.Setter;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

@Entity
public class Driver {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Getter
	private Long id;
	
	@Getter
	@Setter
	private String nationality;
	
	@Getter
	@Setter
	private int age;
	
	@Getter
	@Setter
	private int experience;

	@Getter
	@Setter
	private String firstName;

	@Getter
	@Setter
	private  String lastName;

	@Getter
	@Setter
	private boolean assigned;

	@Getter
	@Setter
	private String token;

	@Column(length = Integer.MAX_VALUE)
	private byte[] imageBytes;

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
