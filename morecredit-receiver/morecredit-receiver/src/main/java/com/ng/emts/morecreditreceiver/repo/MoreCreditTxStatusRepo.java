package com.ng.emts.morecreditreceiver.repo;

import com.ng.emts.morecreditreceiver.model.request.MoreCreditTxStatus;
import org.springframework.data.repository.CrudRepository;

public interface MoreCreditTxStatusRepo extends CrudRepository<MoreCreditTxStatus, Long> {
    MoreCreditTxStatus findByMsisdn(Long msisdn);
}
