package com.onlinebanking.request;

import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/requests")
public class RequestController {
	private RequestRepository requestRepository;

	public RequestController(RequestRepository requestRepository) {
		this.requestRepository = requestRepository;
	}

	@PostMapping
	public void addRequest(@RequestBody Request request) {
		requestRepository.save(request);
	}

	@GetMapping
	public List<Request> getRequests() {
		return requestRepository.findAll();
	}

	@DeleteMapping("/{id}")
	public void deleteRequest(@PathVariable long id) {
		requestRepository.delete(id);
	}
}
