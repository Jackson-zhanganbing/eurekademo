package com.changan.consumerdemo.service.impl;

import com.changan.consumerdemo.dao.WasteDao;
import com.changan.consumerdemo.service.IWasteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IWasteServiceImpl implements IWasteService {

    @Autowired
    private WasteDao wasteDao;

    @Override
    public String getWasteType(String wasteName) {
        return wasteDao.getWasteType(wasteName);

    }


}
