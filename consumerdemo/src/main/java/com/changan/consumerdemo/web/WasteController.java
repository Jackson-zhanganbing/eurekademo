package com.changan.consumerdemo.web;

import com.changan.consumerdemo.service.IWasteService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
/**
* @author zab
* @date 2019-08-07 08:39
*/
@RestController
@RequestMapping("/waste")
public class WasteController {

    @Autowired
    private IWasteService wasteService;

    @GetMapping("/getType")
    public String getWasteType(String wasteName){
        if(StringUtils.isEmpty(wasteName)){
            return "";
        }
        return wasteService.getWasteType(wasteName);
    }
}
