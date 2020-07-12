package com.ng.emts.morecreditreceiver.repo;

import com.ng.emts.morecreditreceiver.model.request.MoreCreditRequest;
import org.springframework.data.repository.CrudRepository;

public interface MoreCreditRequestRepo extends CrudRepository<MoreCreditRequest, Long> {
}
