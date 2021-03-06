package com.pdsu.banmeng.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pdsu.banmeng.entity.WebLabelControl;
import com.pdsu.banmeng.ibo.BlobSearchIbo;
import com.pdsu.banmeng.ibo.LabelDeleteIbo;
import com.pdsu.banmeng.ibo.LabelSearchIbo;
import com.pdsu.banmeng.mapper.WebLabelControlMapper;
import com.pdsu.banmeng.service.IWebLabelControlService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 半梦
 * @since 2021-11-20
 */
@Service
public class WebLabelControlServiceImpl extends ServiceImpl<WebLabelControlMapper, WebLabelControl> implements IWebLabelControlService {

    @Autowired
    private ModelMapper modelMapper;

    @Override
    @Cacheable(value = "Code_Sharing_Community_WebLabelControlService_getWebIdsById", key = "#ibo")
    public List<Integer> getWebIdsById(BlobSearchIbo ibo) {
        return page(new Page<>(ibo.getP(), 10), new QueryWrapper<WebLabelControl>()
                .setEntity(WebLabelControl.builder().lid(ibo.getLid()).build()).orderByDesc("update_time"))
                .getRecords().stream().map(WebLabelControl :: getWid).collect(Collectors.toList());
    }

    @Override
    @Cacheable(value = "Code_Sharing_Community_WebLabelControlService_getLabelIds", key = "#ibo")
    public List<Integer> getLabelIds(LabelSearchIbo ibo) {
        return list(new QueryWrapper<WebLabelControl>()
                .setEntity(modelMapper.map(ibo, WebLabelControl.class))).stream()
                .map(WebLabelControl :: getLid).collect(Collectors.toList());
    }

    @Override
    @CacheEvict(value = "Code_Sharing_Community_WebLabelControlService_getLabelIds", allEntries = true)
    public boolean remove(LabelDeleteIbo ibo) {
        return remove(new QueryWrapper<WebLabelControl>()
                .setEntity(modelMapper.map(ibo, WebLabelControl.class)));
    }
}
