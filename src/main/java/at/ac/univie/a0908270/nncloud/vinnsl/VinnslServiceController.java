package at.ac.univie.a0908270.nncloud.vinnsl;

import at.ac.univie.a00908270.vinnsl.schema.*;
import at.ac.univie.a0908270.nncloud.db.NeuronalNetworkRepository;
import at.ac.univie.a0908270.nncloud.db.Vinnsl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api(description = "ViNNSL Neural Network Service")
@RestController
public class VinnslServiceController {
	
	@Autowired
	NeuronalNetworkRepository nnRepository;

	@GetMapping(value = "/vinnsl", produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "List all Neural Networks")
	public List<Vinnsl> getAllVinnslNetworks() {
		return nnRepository.findAll();
	}
	
	@PostMapping(value = "/vinnsl", consumes = {MediaType.APPLICATION_XML_VALUE})
	@ApiOperation(value = "Import a new ViNNSL XML Defintion")
	ResponseEntity<?> addXml(@RequestBody Vinnsl vinnsl) {
		nnRepository.insert(vinnsl);
		
		URI location = ServletUriComponentsBuilder
				.fromCurrentRequest().path("/{id}")
				.buildAndExpand(vinnsl.identifier).toUri();
		
		return ResponseEntity.created(location).build();
	}
	
	@GetMapping(value = "/vinnsl/{id}", produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
	@ApiOperation(value = "Get Neural Network Object")
	public ResponseEntity<?> getVinnslNetwork(@PathVariable("id") String id) {
		Vinnsl result = nnRepository.findOne(id);
		if (result != null) {
			return ResponseEntity.ok().body(result);
		} else {
			return ResponseEntity.notFound().build();
		}
	}
	
	@PutMapping(value = "/vinnsl/{id}/definition", consumes = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
	@ApiOperation(value = "Add/Replace ViNNSL Definition of Neural Network")
	ResponseEntity<?> addDefinitionToVinnsl(@PathVariable("id") String id, @RequestBody Definition def) {
		Vinnsl result = nnRepository.findOne(id);
		
		if (result != null) {
			result.definition = def;
			nnRepository.save(result);
			return ResponseEntity.ok().body(result);
		} else {
			return ResponseEntity.notFound().build();
		}
	}
	
	@PutMapping(value = "/vinnsl/{id}/instanceschema", consumes = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
	@ApiOperation(value = "Add/Replace ViNNSL Instanceschema of Neural Network")
	ResponseEntity<?> addInstanceToVinnsl(@PathVariable("id") String id, @RequestBody Instanceschema instance) {
		Vinnsl result = nnRepository.findOne(id);
		
		if (result != null) {
			result.instance = instance;
			nnRepository.save(result);
			return ResponseEntity.ok().body(result);
		} else {
			return ResponseEntity.notFound().build();
		}
	}
	
	@PutMapping(value = "/vinnsl/{id}/trainingresult", consumes = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
	@ApiOperation(value = "Add/Replace ViNNSL Trainingresult of Neural Network")
	ResponseEntity<?> addResultToVinnsl(@PathVariable("id") String id, @RequestBody Trainingresultschema trainingresult) {
		Vinnsl result = nnRepository.findOne(id);
		
		if (result != null) {
			result.trainingresult = trainingresult;
			nnRepository.save(result);
			return ResponseEntity.ok().body(result);
		} else {
			return ResponseEntity.notFound().build();
		}
	}
	
	@PutMapping(value = "/vinnsl/{id}/resultschema", consumes = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
	@ApiOperation(value = "Add/Replace ViNNSL Resultschema of Neural Network")
	ResponseEntity<?> addResultToVinnsl(@PathVariable("id") String id, @RequestBody Resultschema resultSchema) {
		Vinnsl result = nnRepository.findOne(id);
		
		if (result != null) {
			result.result = resultSchema;
			nnRepository.save(result);
			return ResponseEntity.ok().body(result);
		} else {
			return ResponseEntity.notFound().build();
		}
	}
	
	@PutMapping(value = "/vinnsl/{id}/addfile", produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
	@ApiOperation(value = "Add/Replace File of Neural Network")
	public ResponseEntity<Vinnsl> addFileToVinnslNetwork(@PathVariable("id") String id, @RequestParam("fileId") String fileId) {
		Vinnsl result = nnRepository.findOne(id);
		
		if (result != null) {
			Dataschema dataschema = new Dataschema();
			dataschema.setIdentifier(fileId);
			Dataschema.Data data = new Dataschema.Data();
			data.setFile(fileId);
			dataschema.setData(data);
			result.data = dataschema;
			
			Definition.Data defData = new Definition.Data();
			defData.setDataSchemaID(fileId);
			result.definition.setData(defData);
			
			nnRepository.save(result);
			
			return ResponseEntity.ok().body(result);
		} else {
			return ResponseEntity.notFound().build();
		}
	}
	
	@DeleteMapping(value = "/vinnsl/{id}")
	@ApiOperation(value = "Remove Neural Network Object")
	public ResponseEntity removeNn(@PathVariable("id") String id) {
		nnRepository.delete(id);
		
		return ResponseEntity.ok().build();
		
	}
	
	@DeleteMapping(value = "/vinnsl/deleteall", produces = {MediaType.APPLICATION_JSON_VALUE})
	@ApiOperation(value = "Delete all Neural Networks")
	public ResponseEntity<?> removeAllNn() {
		nnRepository.deleteAll();
		
		Map<String, String> message = new HashMap<>();
		message.put("message", "all vinnsl deleted");
		
		return ResponseEntity.ok(message);
		
	}
	
}
