package com.epam.esm.controller;

import com.epam.esm.dto.TagDto;
import com.epam.esm.dto.mapper.impl.TagMapperImpl;
import com.epam.esm.entity.Tag;
import com.epam.esm.hateoas.impl.TagHateoasAdder;
import com.epam.esm.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/tags")
public class TagController {
    private final TagService tagService;
    private final TagMapperImpl mapper;
    private final TagHateoasAdder hateoasAdder;

    @Autowired
    public TagController(TagService tagService, TagMapperImpl mapper, TagHateoasAdder hateoasAdder) {
        this.tagService = tagService;
        this.mapper = mapper;
        this.hateoasAdder = hateoasAdder;
    }


    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<TagDto> getAll(@RequestParam(value = "page", defaultValue = "0", required = false) int page,
                               @RequestParam(value = "size", defaultValue = "5", required = false) int size) {

        List<Tag> tags = tagService.getAll(page, size);

        return tags.stream()
                .map(mapper::mapToDto)
                .peek(hateoasAdder::addLinks)
                .collect(Collectors.toList());
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public TagDto tagById(@PathVariable("id") long id) {
        return mapper.mapToDto(tagService.getById(id));
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TagDto createTag(@RequestBody TagDto tag) {
        Tag addedTag = tagService.insert(mapper.mapToEntity(tag));

        TagDto tagDto = mapper.mapToDto(addedTag);
        hateoasAdder.addLinks(tagDto);
        return tagDto;
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> delete(@PathVariable("id") long id) {
        tagService.removeById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/popular")
    @ResponseStatus(HttpStatus.OK)
    public List<TagDto> mostPopularTagOfUserWithHighestCostOfAllOrders(@Param("user_id") long user_id) {
        List<Tag> tags = tagService.getMostPopularTagsOfUserWithHighestCostOfAllOrders(user_id);

        return tags.stream().map(mapper::mapToDto).peek(hateoasAdder::addLinks).collect(Collectors.toList());
    }
}
