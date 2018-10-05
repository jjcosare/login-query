package com.jjcosare.isr.repository;

import com.jjcosare.isr.dto.LoginCountByDate;
import com.jjcosare.isr.dto.LoginCountByUser;
import com.jjcosare.isr.model.Login;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.aggregation.*;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.util.CollectionUtils;
import reactor.core.publisher.Flux;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;

/**
 * Created by jjcosare on 10/5/18.
 */
public class LoginRepositoryImpl implements LoginRepositoryCustom {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoginRepositoryImpl.class);

    @Autowired
    private ReactiveMongoTemplate reactiveMongoTemplate;

    @Override
    public Flux<LoginCountByDate> getAllUniqueLoginDates(){
        ProjectionOperation projectionOperation = project().and(
                DateOperators.dateFromParts()
                        .yearOf(DateOperators.Year.yearOf(Login.FIELD_LOGIN_TIME))
                        .monthOf(DateOperators.Month.monthOf(Login.FIELD_LOGIN_TIME))
                        .dayOf(DateOperators.DayOfMonth.dayOfMonth(Login.FIELD_LOGIN_TIME)))
                .as(LoginCountByDate.FIELD_LOGIN_DATE);
        GroupOperation groupOperation = group(LoginCountByDate.FIELD_LOGIN_DATE).first(LoginCountByDate.FIELD_LOGIN_DATE).as(LoginCountByDate.FIELD_LOGIN_DATE)
                .count().as(LoginCountByDate.FIELD_LOGIN_COUNT);
        SortOperation sortOperation = sort(Sort.Direction.ASC, LoginCountByDate.FIELD_LOGIN_DATE);
        return reactiveMongoTemplate.aggregate(Aggregation.newAggregation(projectionOperation, groupOperation,
                sortOperation), Login.class, LoginCountByDate.class);
    }

    @Override
    public Flux<LoginCountByUser> getUniqueLoginUsersByDateRange(LocalDate startDate, LocalDate endDate) {
        List<Criteria> optionalCriteriaList = new ArrayList<>();
        addCriteriaGTE(optionalCriteriaList, Login.FIELD_LOGIN_TIME, startDate);
        addCriteriaLTE(optionalCriteriaList, Login.FIELD_LOGIN_TIME, endDate);
        MatchOperation matchOperation = getMatchOperationOnAndOperator(optionalCriteriaList);
        GroupOperation groupOperation = group(Login.FIELD_USER).first(Login.FIELD_USER).as(LoginCountByUser.FIELD_LOGIN_USER)
                .count().as(LoginCountByUser.FIELD_LOGIN_COUNT);
        SortOperation sortOperation = sort(Sort.Direction.ASC, LoginCountByUser.FIELD_LOGIN_USER);
        return reactiveMongoTemplate.aggregate(getAggregation(matchOperation, groupOperation, sortOperation), Login.class, LoginCountByUser.class);
    }

    @Override
    public Flux<LoginCountByUser> getUniqueLoginUsersByDateRangeAndAttributes(LocalDate startDate, LocalDate endDate,
                List<String> attribute1List, List<String> attribute2List, List<String> attribute3List, List<String> attribute4List) {
        List<Criteria> optionalCriteriaList = new ArrayList<>();
        addCriteriaGTE(optionalCriteriaList, Login.FIELD_LOGIN_TIME, startDate);
        addCriteriaLTE(optionalCriteriaList, Login.FIELD_LOGIN_TIME, endDate);
        addCriteriaIN(optionalCriteriaList, Login.FIELD_ATTRIBUTE1, attribute1List);
        addCriteriaIN(optionalCriteriaList, Login.FIELD_ATTRIBUTE2, attribute2List);
        addCriteriaIN(optionalCriteriaList, Login.FIELD_ATTRIBUTE3, attribute3List);
        addCriteriaIN(optionalCriteriaList, Login.FIELD_ATTRIBUTE4, attribute4List);
        MatchOperation matchOperation = getMatchOperationOnAndOperator(optionalCriteriaList);
        GroupOperation groupOperation = group(Login.FIELD_USER).first(Login.FIELD_USER).as(LoginCountByUser.FIELD_LOGIN_USER)
                .count().as(LoginCountByUser.FIELD_LOGIN_COUNT);
        SortOperation sortOperation = sort(Sort.Direction.ASC, LoginCountByUser.FIELD_LOGIN_USER);
        return reactiveMongoTemplate.aggregate(getAggregation(matchOperation, groupOperation, sortOperation), Login.class, LoginCountByUser.class);
    }

    private Aggregation getAggregation(MatchOperation matchOperation, GroupOperation groupOperation, SortOperation sortOperation){
        return (matchOperation != null)
                ? Aggregation.newAggregation(matchOperation, groupOperation, sortOperation)
                : Aggregation.newAggregation(groupOperation, sortOperation);
    }

    private void addCriteriaGTE(List<Criteria> criteriaList, String field, Object object){
        if(object != null){
            criteriaList.add(Criteria.where(field).gte(object));
        }
    }

    private void addCriteriaLTE(List<Criteria> criteriaList, String field, Object object){
        if(object != null){
            criteriaList.add(Criteria.where(field).lte(object));
        }
    }

    private void addCriteriaIN(List<Criteria> criteriaList, String field, Collection collection){
        if(!CollectionUtils.isEmpty(collection)){
            criteriaList.add(Criteria.where(field).in(collection));
        }
    }

    private MatchOperation getMatchOperationOnAndOperator(List<Criteria> criteriaList){
        MatchOperation matchOperation = null;
        if(!CollectionUtils.isEmpty(criteriaList)) {
            Criteria criteria = new Criteria().andOperator(criteriaList.toArray(new Criteria[criteriaList.size()]));
            matchOperation = match(criteria);
        }
        return matchOperation;
    }

}
