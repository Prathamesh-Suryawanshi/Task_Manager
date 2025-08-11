	package com.prathameshToDoProject.taskmanager.Controller;
	
	import com.prathameshToDoProject.taskmanager.model.Task;
	import com.prathameshToDoProject.taskmanager.service.TaskService;
	import org.springframework.http.ResponseEntity;
	import org.springframework.web.bind.annotation.*;
	
	import java.util.List;
	
	
	@RestController
	@RequestMapping("/api/tasks")
	@CrossOrigin(origins = "http://localhost:3000")
	
	public class TaskController {
		
		
		private final TaskService service;
	
	    public TaskController(TaskService service) {
	        this.service = service;
	    }
	
	    @GetMapping
	    public List<Task> getAll() {
	        return service.findAll();
	    }
	
	    @GetMapping("/{id}")
	    public ResponseEntity<Task> getById(@PathVariable Long id) {
	        return service.findById(id)
	                .map(ResponseEntity::ok)
	                .orElse(ResponseEntity.notFound().build());
	    }
	
	    @PostMapping
	    public Task create(@RequestBody Task task) {
	        task.setId(null);
	        return service.save(task);
	    }
	
	    @PutMapping("/{id}")
	    public ResponseEntity<Task> update(@PathVariable Long id, @RequestBody Task task) {
	        return service.findById(id).map(existing -> {
	            existing.setTitle(task.getTitle());
	            existing.setDescription(task.getDescription());
	            existing.setCompleted(task.isCompleted());
	            service.save(existing);
	            return ResponseEntity.ok(existing);
	        }).orElse(ResponseEntity.notFound().build());
	    }
	
	    @DeleteMapping("/{id}")
	    public ResponseEntity<Void> delete(@PathVariable Long id) {
	        service.deleteById(id);
	        return ResponseEntity.noContent().build();
	    }
	
	    
	}
	

