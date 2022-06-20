package com.trans.services;

import com.trans.security.data.UserDetails;

public interface UserService {

    UserDetails getUserForGoodsOfferId(Long parameter) throws Exception;

    UserDetails getUserForTransportOfferId(Long parameter) throws Exception;
}
