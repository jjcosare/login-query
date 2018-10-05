package com.jjcosare.isr.controller;

import com.jjcosare.isr.dto.LoginCountByDate;
import com.jjcosare.isr.dto.LoginCountByUser;
import com.jjcosare.isr.model.Login;
import com.jjcosare.isr.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.time.LocalDate;
import java.util.List;

/**
 * Created by jjcosare on 10/3/18.
 */
@RestController
@RequestMapping("/test")
public class LoginController extends BaseController<LoginService, Login, String> {

    @Autowired
    public LoginController(LoginService loginService){
        super(loginService);
    }

    /**
    * http://server/test/dates
    * Retrieves a JSON array of all the unique dates (ignoring time) in the table
    * The resulting JSON array needs to be sorted ascending.
    */
    @GetMapping("/dates")
    public Flux<LoginCountByDate> getAllUniqueLoginDates() {
        return this.getService().getAllUniqueLoginDates();
    }

    /**
     * http://server/test/users?start=YYYYMMDD&end=YYYYMMDD
     * Retrieves a JSON array of all the unique users for which there is a login record between the start and
     * end date.
     * Both parameters are optional, so there can be a start date, an end date, or both.
     * The resulting JSON array needs to be sorted ascending.
     */
    @GetMapping("/users")
    public Flux<LoginCountByUser> getUniqueLoginUsersByDateRange(
            @RequestParam(value = "start", required = false) @DateTimeFormat(pattern = "yyyyMMdd") LocalDate startDate,
            @RequestParam(value = "end", required = false) @DateTimeFormat(pattern = "yyyyMMdd") LocalDate endDate) {
        return this.getService().getUniqueLoginUsersByDateRange(startDate, endDate);
    }

    /**
     * http://server/test/logins?start=YYYYMMDD&end=YYYYMMDD&attribute1=AAA&attribute2=BBB&attribute3=CCC&attribute4=DDD
     * Retrieves a JSON object where the key is the user name and the value is the number of times a user
     * logged on between the start and the end date.
     * All parameters are optional.
     * The values used for the attributes are used as filters, i.e. only the records should be counted for which
     * the attribute values are equal to the ones specified in the parameters.
     * For one attribute, multiple values might be present, e.g.
     * http://server/test/logins?attribute1=AA1&attribute1=AA2&attribute1=AA3
     */
    @GetMapping("/logins")
    public Flux<LoginCountByUser> getUniqueLoginUsersByDateRangeAndAttributes(
            @RequestParam(value = "start", required = false) @DateTimeFormat(pattern = "yyyyMMdd") LocalDate startDate,
            @RequestParam(value = "end", required = false) @DateTimeFormat(pattern = "yyyyMMdd") LocalDate endDate,
            @RequestParam(value = "attribute1", required = false) List<String> attribute1List,
            @RequestParam(value = "attribute2", required = false) List<String> attribute2List,
            @RequestParam(value = "attribute3", required = false) List<String> attribute3List,
            @RequestParam(value = "attribute4", required = false) List<String> attribute4List) {
        return this.getService().getUniqueLoginUsersByDateRangeAndAttributes(startDate, endDate, attribute1List, attribute2List, attribute3List, attribute4List);
    }

}
