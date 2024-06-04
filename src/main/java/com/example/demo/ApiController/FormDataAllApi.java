package com.example.demo.ApiController;

import java.io.IOException;
import java.util.ArrayList;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.example.demo.ApiDto.ApiResponse;
import com.example.demo.ApiDto.FormDataSaveRequest;
import com.example.demo.ApiDto.Response;
import com.example.demo.ApiService.FileService;
import com.example.demo.Model.Accessories;
import com.example.demo.Model.Brand;
import com.example.demo.Model.BrandModel;
import com.example.demo.Model.ConstructionStatus;
import com.example.demo.Model.EducationalType;
import com.example.demo.Model.Facing;
import com.example.demo.Model.FormData;
import com.example.demo.Model.FormDataSave;
import com.example.demo.Model.Fuel;
import com.example.demo.Model.Furnshing;
import com.example.demo.Model.ListedBy;
import com.example.demo.Model.Position;
import com.example.demo.Model.Property;
import com.example.demo.Model.SalaryPeriod;
import com.example.demo.Model.Transmission;
import com.example.demo.Model.Variant;
import com.example.demo.Model.Vehicles;
import com.example.demo.Repository.AccessoriesRepository;
import com.example.demo.Repository.BrandModelRepository;
import com.example.demo.Repository.BrandRepository;
import com.example.demo.Repository.ConstructionStatusRepository;
import com.example.demo.Repository.EducationalTypeRepository;
import com.example.demo.Repository.FacingRepository;
import com.example.demo.Repository.FormDataRepository;
import com.example.demo.Repository.FormDataSaveRepository;
import com.example.demo.Repository.FuelRepository;
import com.example.demo.Repository.FurnshingRepository;
import com.example.demo.Repository.ListedByRepository;
import com.example.demo.Repository.PositionRepository;
import com.example.demo.Repository.PropertyRepository;
import com.example.demo.Repository.SalaryPeriodRepository;
import com.example.demo.Repository.TransmissionRepository;
import com.example.demo.Repository.VariantRepository;
import com.example.demo.Repository.VehiclesRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/api/v2")
public class FormDataAllApi {
	
	  @Autowired
	    private AccessoriesRepository accessoriesRepository;
	  
	  @Autowired
	    private BrandRepository brandrepo;
	  
		@Autowired
		  private  FormDataRepository formDataRepository;
			 
		@Autowired
		  private  	FormDataSaveRepository  formDataSaveRepository ;
		
			  
		@Autowired
		  private  BrandModelRepository brandmodelrepo;
		
		@Autowired
		  private  ConstructionStatusRepository constructionrepo;
		
		@Autowired
		  private  EducationalTypeRepository eduticationrepo;
		
		@Autowired
		  private  	FacingRepository facingrepo;
		
		@Autowired
		  private  FuelRepository  fuelrepo;
		
		@Autowired
		  private  	FurnshingRepository furnshingrepo;
		
		@Autowired
		  private  	ListedByRepository listedrepo;
		
		@Autowired
		  private  	PositionRepository positionrepo;
		
		@Autowired
		  private  	PropertyRepository propertyrepo;
		
		@Autowired
		  private  	SalaryPeriodRepository salaryperiodrepo;
		
		@Autowired
		  private  	TransmissionRepository  transmissionrepo;
		
		@Autowired
		  private  VariantRepository  variantrepo;
		
		@Autowired
		  private  	VehiclesRepository  vehiclesrepo;
		
	    @Autowired
	    private FileService fileService;
		

	    
	    @GetMapping("/formData/{id}")
	    public ResponseEntity<List<Object>> getFormData(@PathVariable Long id) {
	        FormData formData = formDataRepository.findById(id).orElse(null);
	        if (formData == null) {
	            return ResponseEntity.notFound().build();
	        }

	        String[] checkedBoxes = formData.getCheckedBoxes().split(",");

	        List<Object> responseData = new ArrayList<>();

	        for (String checkBox : checkedBoxes) {
	            switch (checkBox.trim()) {
	                case "Accessories":
	                    List<Accessories> accessories = accessoriesRepository.findByActiveTrue();
	                    responseData.add(new ApiResponse("accessories", "checkedbox" , accessories ));
	                    break;
	                case "Brand":
	                    List<Brand> brands = brandrepo.findByActiveTrue();
	                    responseData.add(new ApiResponse("brand",  "dropdown" , brands));
	                    break;
	                case "Model":
	                    List<BrandModel> brandmodel = brandmodelrepo.findByActiveTrue();
	                    responseData.add(new ApiResponse("brandmodel", "dropdown" , brandmodel));
	                    break;
	                case "ConstructionStatus":
	                    List<ConstructionStatus> constructionstatus = constructionrepo.findByActiveTrue();
	                    responseData.add(new ApiResponse(" constructionstatus", "checkedbox" , constructionstatus));
	                    break;
	                case "EducationalType":
	                    List<EducationalType> educational = eduticationrepo.findByActiveTrue();
	                    responseData.add(new ApiResponse(" educational" ,"checkedbox" ,  educational));
	                    break;
	                case "Facing":
	                    List<Facing> facing = facingrepo.findByActiveTrue();
	                    responseData.add(new ApiResponse("facing" , "dropdown" ,  facing));
	                    break;
	                case "Furnshing":
	                    List<Furnshing> furnshing = furnshingrepo.findByActiveTrue();
	                    responseData.add(new ApiResponse("Furnshing" , "checkedbox" ,  furnshing));
	                    break;
	                case "Fuel":
	                    List<Fuel> fuel = fuelrepo.findByActiveTrue();
	                    responseData.add(new ApiResponse("fuel" ,"checkedbox" ,  fuel));
	                    break;
	                case "ListedBy":
	                    List<ListedBy> listedBy = listedrepo.findByActiveTrue();
	                    responseData.add(new ApiResponse("listedBy" ,"checkedbox" , listedBy));
	                    break;
	                case "Position":
	                    List<Position> position = positionrepo.findByActiveTrue();
	                    responseData.add(new ApiResponse("position" ,"checkedbox" ,  position));
	                    break;
	                case "Property":
	                    List<Property> property = propertyrepo.findByActiveTrue();
	                    responseData.add(new ApiResponse("property" ,"dropdown" ,  property));
	                    break;
	                case "SalaryPeriod":
	                    List<SalaryPeriod> salaryperiod = salaryperiodrepo.findByActiveTrue();
	                    responseData.add(new ApiResponse("salaryperiod","checkedbox" , salaryperiod ));
	                    break;
	                case "Transmission":
	                    List<Transmission> transmission =  transmissionrepo.findByActiveTrue();
	                    responseData.add(new ApiResponse("transmission" , "checkedbox" ,  transmission));
	                    break;
	                case "Variant":
	                    List<Variant> variant = variantrepo.findByActiveTrue();
	                    responseData.add(new ApiResponse("varient" ,"dropdown" ,  variant));
	                    break;
	                case "Vehicles":
	                    List<Vehicles> vehicle = vehiclesrepo.findByActiveTrue();
	                    responseData.add(new ApiResponse("vehicle","dropdown" ,  vehicle ));
	                    break;
	                default:
	                 
	                    break;
	            }
	        }

	        return ResponseEntity.ok(responseData);
	    }


	    private final ObjectMapper objectMapper = new ObjectMapper();

	    @PostMapping("/formData")
	    public ResponseEntity<Response> createFormData(@RequestBody FormDataSaveRequest formDataRequest) throws JsonProcessingException {
	        FormDataSave formData = new FormDataSave();
	        formData.setFormid(formDataRequest.getFormid());
	        formData.setAddress(formDataRequest.getAddress());
//            formData.setPathsList(formDataRequest.getPath());
	        formData.setTitle(formDataRequest.getTitle());
	        formData.setActive(formDataRequest.isActive());
	        formData.setDataJson(objectMapper.writeValueAsString(formDataRequest.getData()));

	        FormDataSave savedFormData = formDataSaveRepository.save(formData);
	        
	        Response response = new Response.Builder()
	                .setStatus(true)
	                .setMessage("data save succesfully.")
	                .build();
	        return ResponseEntity.status(HttpStatus.OK).body(response);
	     
	    }

	    
	    @PostMapping("/{formdatasaveId}/uploadpic")
	    public ResponseEntity<Response> uploadImage(
	            @PathVariable Long formdatasaveId, 
	            @RequestParam("files") List<MultipartFile> files) {
	        try {
	            fileService.saveformfile(files, formdatasaveId); 
	            
	            Response response = new Response.Builder()
	                    .setStatus(true)
	                    .setMessage("Files uploaded successfully!")
	                    .build();

	            return ResponseEntity.status(HttpStatus.OK).body(response);

	        } catch (IOException e) {
	            Response response = new Response.Builder()
	                    .setStatus(false)
	                    .setMessage("Error uploading files: " + e.getMessage())
	                    .build();
	            
	            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
	        }
	    }
	}

