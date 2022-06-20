package com.trans.services;

import com.trans.security.data.User;
import com.trans.security.data.UserDetails;
import com.trans.security.data.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails getUserForGoodsOfferId(Long parameter) throws Exception {

        WebClient client = WebClient.create();

        String uri = "localhost:9061/offer/goods/token/" + parameter.toString();

        String token;

        ResponseEntity<String> response = client.get()
                .uri(uriBuilder -> uriBuilder.path(uri)
                        .build())
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .toEntity(String.class)
                .block();
        if(response.getBody() != null) {
            token = response.getBody();
        }else{
            throw new Exception("Can not get response");
        }

        User user = userRepository.findByToken(token);

        UserDetails userDetails = new UserDetails();
        userDetails.setCompanyDetails(user.getCompanyInfo());
        userDetails.setPhotoDetails(user.getImage());
        userDetails.setEmail(user.getEmail());
        userDetails.setPhoneNumber(user.getPhoneNumber());
        userDetails.setFirstName(user.getFirstName());
        userDetails.setLastName(user.getLastName());

        return userDetails;
    }

    @Override
    public UserDetails getUserForTransportOfferId(Long parameter) throws Exception {
        WebClient client = WebClient.create();

        String uri = "localhost:9061/offer/transport/token/" + parameter.toString();

        String token;

        ResponseEntity<String> response = client.get()
                .uri(uriBuilder -> uriBuilder.path(uri)
                        .build())
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .toEntity(String.class)
                .block();
        if(response.getBody() != null) {
            token = response.getBody();
        }else{
            throw new Exception("Can not get response");
        }

        User user = userRepository.findByToken(token);

        UserDetails userDetails = new UserDetails();
        userDetails.setCompanyDetails(user.getCompanyInfo());
        userDetails.setPhotoDetails(user.getImage());
        userDetails.setEmail(user.getEmail());
        userDetails.setPhoneNumber(user.getPhoneNumber());
        userDetails.setFirstName(user.getFirstName());
        userDetails.setLastName(user.getLastName());

        return userDetails;
    }
}
