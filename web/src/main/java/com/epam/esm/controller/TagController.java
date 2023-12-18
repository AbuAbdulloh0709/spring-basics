package com.epam.esm.controller;

import com.epam.esm.entity.Tag;
import com.epam.esm.exception.DaoException;
import com.epam.esm.exceptions.IncorrectParameterException;
import com.epam.esm.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tags")
public class TagController {
    private final TagService tagService;

    @Autowired
    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    @RequestMapping
    public List<Tag> getAllTags() throws IncorrectParameterException, DaoException {
        return tagService.getAll();
    }

    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
    public Tag tagById(@PathVariable("id") long id) throws IncorrectParameterException, DaoException {
        return tagService.getById(id);
    }

    @RequestMapping(value = "/{id}",method = RequestMethod.DELETE)
    public ResponseEntity<Object> deleteTag(@PathVariable("id") long id) throws IncorrectParameterException, DaoException {
        tagService.removeById(id);
        return ResponseEntity.status(HttpStatus.OK).body("success");
    }
    @PostMapping
    public ResponseEntity<Object> createTag(@RequestBody Tag tag) throws IncorrectParameterException, DaoException {
        tagService.insert(tag);
        return ResponseEntity.status(HttpStatus.CREATED).body("success");
    }
}
