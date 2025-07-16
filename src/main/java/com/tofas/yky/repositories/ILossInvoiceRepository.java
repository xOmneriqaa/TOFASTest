package com.tofas.yky.repositories;
/* T40127 @ 10.06.2016. */

import java.util.List;

public interface ILossInvoiceRepository<T> {

    List<T> findByLossIdIn(List<Long> lostIds);
}
