package com.ms.post.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ms.post.dto.PostageDto;
import com.ms.post.mapper.PostageDtoToPostageModel;
import com.ms.post.mapper.PostageModelToEmailPostageMapper;
import com.ms.post.mapper.PostageModelToPostageDto;
import com.ms.post.model.PostageModel;
import com.ms.post.service.PostageService;
import com.ms.post.service.RabbitMQService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping(value = "/postage",produces = { "application/json" })
@AllArgsConstructor
public class PostageController {

    @Autowired
    PostageService postageService;

    @Autowired
    private RabbitMQService rabbitMQService;

    private final PostageDtoToPostageModel postageDtoToPostageModel;

    private final PostageModelToPostageDto postageModelToPostageDto;

    private final PostageModelToEmailPostageMapper postageModelToEmailPostageMapper;

    @PostMapping
    public ResponseEntity<PostageModel> insertPostageModel(@RequestBody PostageDto postageDto) throws JsonProcessingException {

        var postageModel = postageDtoToPostageModel.mapper(postageDto);
        postageService.insert(postageModel);

        var postageQueue = postageModelToPostageDto.mapper(postageModel);
        rabbitMQService.sendMessageToQueueEventTrack(postageQueue);

        var email = postageModelToEmailPostageMapper.mapper(postageModel);
        rabbitMQService.sendMessageToQueueEmail(email);

        return new ResponseEntity<>(postageModel, HttpStatus.CREATED);
    }

    @GetMapping()
    public ResponseEntity<List<PostageModel>> findAllPostageModel() {

        List<PostageModel> postageList = postageService.findAll();

        if (postageList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            for (PostageModel postageModel : postageList) {
                long id = postageModel.getId();
                postageModel.add(linkTo(methodOn(PostageController.class).findByIdPostageModel(id)).withSelfRel());
            }
            return new ResponseEntity<List<PostageModel>>(postageList, HttpStatus.OK);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostageModel> findByIdPostageModel(@PathVariable(value = "id") long id) {
        Optional<PostageModel> postageModel = postageService.findById(id);
        if (!postageModel.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            postageModel.get().add(linkTo(methodOn(PostageController.class).findAllPostageModel()).withRel("Lista de Postagens"));
            return new ResponseEntity<PostageModel>(postageModel.get(), HttpStatus.OK);
        }
    }


}
