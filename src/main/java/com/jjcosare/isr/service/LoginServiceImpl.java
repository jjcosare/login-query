package com.jjcosare.isr.service;

import com.jjcosare.isr.dto.LoginCountByDate;
import com.jjcosare.isr.dto.LoginCountByUser;
import com.jjcosare.isr.model.Login;
import com.jjcosare.isr.repository.LoginRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.time.LocalDate;
import java.util.List;

/**
 * Created by jjcosare on 10/3/18.
 */
@Service
public class LoginServiceImpl extends BaseServiceImpl<LoginRepository, Login, String> implements LoginService {

    @Autowired
    public LoginServiceImpl(LoginRepository loginRepository){
        super(loginRepository);
    }

    @Override
    public Flux<LoginCountByDate> getAllUniqueLoginDates(){
        return this.getRepository().getAllUniqueLoginDates();
    }

    @Override
    public Flux<LoginCountByUser> getUniqueLoginUsersByDateRange(LocalDate startDate, LocalDate endDate) {
        return this.getRepository().getUniqueLoginUsersByDateRange(startDate, endDate);
    }

    @Override
    public Flux<LoginCountByUser> getUniqueLoginUsersByDateRangeAndAttributes(LocalDate startDate, LocalDate endDate,
            List<String> attribute1List, List<String> attribute2List, List<String> attribute3List, List<String> attribute4List) {
        return this.getRepository().getUniqueLoginUsersByDateRangeAndAttributes(startDate, endDate, attribute1List, attribute2List, attribute3List, attribute4List);
    }

}
