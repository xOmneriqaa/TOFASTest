package com.tofas.yky.components;
/* T40127 @ 16.10.2015. */

import com.tofas.yky.model.decoratorbases.impl.VSupplier;
import com.tofas.yky.repositories.SupplierUserRepositoryImpl;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

@Component
@Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class SupplierUserInfo {

    private @Resource
    SupplierUserRepositoryImpl supplierRepository;

    private VSupplier supplier;

    @PostConstruct
    public void postConstruct() {
        supplier = null;
    }

    public VSupplier getSupplier() {
        if(supplier == null) {
            supplier = supplierRepository.getSupplierFromUser();

        }
        return supplier;
    }
}
