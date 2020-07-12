package com.ng.emts.morecreditreceiver.repo;

import com.ng.emts.morecreditreceiver.model.request.MoreCreditDebtorsLog;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface MoreCreditDebtorsLogRepo extends CrudRepository<MoreCreditDebtorsLog,Long> {
    List<MoreCreditDebtorsLog> findByMsisdn(String msisdn);
}
