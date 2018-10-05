package com.jjcosare.isr.feature;

import com.jjcosare.isr.dto.LoginCountByDate;
import com.jjcosare.isr.dto.LoginCountByUser;
import reactor.core.publisher.Flux;

import java.time.LocalDate;
import java.util.List;

/**
 * Created by jjcosare on 10/5/18.
 */
public interface LoginCount {

    Flux<LoginCountByDate> getAllUniqueLoginDates();

    Flux<LoginCountByUser> getUniqueLoginUsersByDateRange(LocalDate startDate, LocalDate endDate);

    Flux<LoginCountByUser> getUniqueLoginUsersByDateRangeAndAttributes(LocalDate startDate, LocalDate endDate,
        List<String> attribute1List, List<String> attribute2List, List<String> attribute3List, List<String> attribute4List);

}
