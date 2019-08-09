package com.changan.consumerdemo.dao.mapper;

import com.changan.consumerdemo.entity.WasteTypeTable;
import com.changan.consumerdemo.entity.WasteTypeTableExample;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
@Mapper
public interface WasteTypeTableMapper {
    int countByExample(WasteTypeTableExample example);

    int deleteByExample(WasteTypeTableExample example);

    int insert(WasteTypeTable record);

    int insertSelective(WasteTypeTable record);

    List<WasteTypeTable> selectByExample(WasteTypeTableExample example);

    WasteTypeTable selectByWasteName(String wasteName);

    int updateByExampleSelective(@Param("record") WasteTypeTable record, @Param("example") WasteTypeTableExample example);

    int updateByExample(@Param("record") WasteTypeTable record, @Param("example") WasteTypeTableExample example);
}