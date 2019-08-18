package com.changan.consumerdemo.dao;

import com.changan.consumerdemo.dao.mapper.WasteTypeTableMapper;
import com.changan.consumerdemo.entity.WasteTypeTable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class WasteDao {
    @Autowired
    private WasteTypeTableMapper wasteTypeTableMapper;

    public String getWasteType(String wasteName) {
        WasteTypeTable wasteTypeTable = wasteTypeTableMapper.selectByWasteName(wasteName);
        if (wasteTypeTable == null) {
            return "";
        }
        return wasteTypeTable.getWasteType();
    }
}
