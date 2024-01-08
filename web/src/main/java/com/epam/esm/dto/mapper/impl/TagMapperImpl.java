package com.epam.esm.dto.mapper.impl;

import com.epam.esm.dto.TagDto;
import com.epam.esm.dto.mapper.Mapper;
import com.epam.esm.entity.Tag;
import org.springframework.stereotype.Component;

@Component
public class TagMapperImpl implements Mapper<Tag, TagDto> {
    @Override
    public Tag mapToEntity(TagDto dto) {
        Tag tag = new Tag();
        tag.setId(dto.getId());
        tag.setName(dto.getName());
        return tag;
    }

    @Override
    public TagDto mapToDto(Tag entity) {
        TagDto dto = new TagDto();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        return dto;
    }
}
