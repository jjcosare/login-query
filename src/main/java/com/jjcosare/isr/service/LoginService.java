package com.jjcosare.isr.service;

import com.jjcosare.isr.feature.LoginCount;
import com.jjcosare.isr.model.Login;
import com.jjcosare.isr.repository.LoginRepository;

/**
 * Created by jjcosare on 10/4/18.
 */
public interface LoginService extends BaseService<LoginRepository, Login, String>, LoginCount {

}
