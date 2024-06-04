package com.example.demo.ApiController;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.data.domain.Sort;

import com.example.demo.ApiDto.ChoosendataDto;
import com.example.demo.ApiDto.ForamdataResponse;
import com.example.demo.ApiDto.FormDataSaveResponse;
import com.example.demo.ApiDto.PaginatedResponse;
import com.example.demo.ApiDto.Response;
import com.example.demo.ApiDto.SearchRequest;
import com.example.demo.ApiDto.SearchResponse;
import com.example.demo.Model.Accessories;
import com.example.demo.Model.Brand;
import com.example.demo.Model.BrandModel;

import com.example.demo.Model.ConstructionStatus;
import com.example.demo.Model.EducationalType;
import com.example.demo.Model.Facing;
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
import com.fasterxml.jackson.annotation.JsonView;

import com.fasterxml.jackson.core.type.TypeReference;

import com.fasterxml.jackson.databind.ObjectMapper;



@RestController
@RequestMapping("/api/v2")
public class FormDataController {
	
	@Autowired
	private FormDataSaveRepository formdatasaverepo;

	
	  @Autowired
	    private AccessoriesRepository accessoriesRepository;
	  
	  @Autowired
	    private BrandRepository brandrepo;
	  		  
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
	    private ObjectMapper objectMapper;
	    
	    
	    @GetMapping("/active/data")
	    @JsonView(ChoosendataDto.View.Summary.class)
	    public ResponseEntity<?> getActiveFormData(
	            @RequestParam(value = "pagenumber", defaultValue = "0", required = false) int page,
	            @RequestParam(value = "pagesize", defaultValue = "1", required = false) int size,
	            @RequestParam(value = "sortBy", defaultValue = "title", required = false) String sortBy,
	            @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir) {

	        Sort.Direction direction = sortDir.equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;
	        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));
	        Page<FormDataSave> formdatasavePage = formdatasaverepo.findByActiveTrue(pageable);

	        if (formdatasavePage.isEmpty()) {
	            return ResponseEntity.notFound().build();
	        } else {
	            try {
	                List<FormDataSaveResponse> responseList = new ArrayList<>();

	                for (FormDataSave formData : formdatasavePage.getContent()) {
	                    String dataJson = formData.getDataJson();
	                    List<ChoosendataDto> data = objectMapper.readValue(dataJson, new TypeReference<List<ChoosendataDto>>() {});

	                    for (ChoosendataDto choosendata : data) {
	                        String type = choosendata.getType();
	                        String typechildIdStr = choosendata.getTypechildId();
	                        if (typechildIdStr != null && !typechildIdStr.trim().isEmpty()) {
	                            try {
	                                Long typechildId = Long.parseLong(typechildIdStr);
	                                String typeChildName = getTypeChildName(type, typechildId);
	                                choosendata.setTypeChildName(typeChildName);
	                            } catch (NumberFormatException e) {
	                                System.err.println("Error parsing typechildId: " + typechildIdStr + " - " + e.getMessage());
	                            }
	                        } else {
	                            System.err.println("Invalid or null typechildId: " + typechildIdStr);
	                        }
	                    }

	                    FormDataSaveResponse response = new FormDataSaveResponse(
	                            data,
	                            formData.getFormid(),
	                            formData.getAddress(),
	                            formData.getPathsListWithBaseUrl(),
	                            formData.getTitle(),
	                            formData.isActive()
	                    );

	                    responseList.add(response);
	                }

	                PaginatedResponse<FormDataSaveResponse> paginatedResponse = new PaginatedResponse<>(
	                        responseList,
	                        formdatasavePage.getNumber(),
	                        formdatasavePage.getSize(),
	                        formdatasavePage.getTotalElements(),
	                        formdatasavePage.getTotalPages()
	                );

	                return ResponseEntity.ok(paginatedResponse);

	            } catch (Exception e) {
	                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	            }
	        }
	    }
	    	    

	    @GetMapping("/data/{id}")
	    @JsonView(ChoosendataDto.View.Summary.class)
	    public ResponseEntity<?> getFormDataById(@PathVariable Long id) {
	        Optional<FormDataSave> formdatasaveOptional = formdatasaverepo.findById(id);

	        if (!formdatasaveOptional.isPresent()) {
	            ForamdataResponse response = new ForamdataResponse.Builder()
	                .setStatus(false)
	                .setMessage("Data not found")
	                .build();
	            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
	        }

	        try {
	            FormDataSave formData = formdatasaveOptional.get();
	            String dataJson = formData.getDataJson();
	            List<ChoosendataDto> data = objectMapper.readValue(dataJson, new TypeReference<List<ChoosendataDto>>() {});

	            for (ChoosendataDto choosendata : data) {
	                String type = choosendata.getType();
	                String typechildIdStr = choosendata.getTypechildId();
	                if (typechildIdStr != null && !typechildIdStr.trim().isEmpty()) {
	                    try {
	                        Long typechildId = Long.parseLong(typechildIdStr);
	                        String typeChildName = getTypeChildName(type, typechildId);
	                        choosendata.setTypeChildName(typeChildName);
	                    } catch (NumberFormatException e) {
	                        System.err.println("Error parsing typechildId: " + typechildIdStr + " - " + e.getMessage());
	                    }
	                } else {
	                    System.err.println("Invalid or null typechildId: " + typechildIdStr);
	                }
	            }

	            FormDataSaveResponse searchResponse = new FormDataSaveResponse(
	                data,
	                formData.getFormid(),
	                formData.getAddress(),
	                formData.getPathsListWithBaseUrl(),
	                formData.getTitle(),
	                formData.isActive()
	            );

	            ForamdataResponse response = new ForamdataResponse.Builder()
	                .setStatus(true)
	                .setMessage("Success")
	                .setresult(searchResponse)
	                .build();

	            return ResponseEntity.ok(response);

	        } catch (Exception e) {
	            ForamdataResponse response = new ForamdataResponse.Builder()
	                .setStatus(false)
	                .setMessage("An error occurred while processing the data")
	                .build();
	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
	        }
	    }

	    
	    private String getTypeChildName(String type, Long typechildId) {
        String typeChildName = null;

        switch (type) {
            case "brand":
                Optional<Brand> brandOptional = brandrepo.findById(typechildId);
                if (brandOptional.isPresent()) {
                    typeChildName = brandOptional.get().getName();
                } else {
                    typeChildName = "Brand data not found for the provided typeChildId";
                }
                break;
            case "brandmodel":
                Optional<BrandModel> brandmodelOptional = brandmodelrepo.findById(typechildId);
                if (brandmodelOptional.isPresent()) {
                    typeChildName = brandmodelOptional.get().getName();
                } else {
                    typeChildName = "BrandModel data not found for the provided typeChildId";
                }
                break;
            case "SalaryPeriod":
                Optional<SalaryPeriod> salaryPeriodOptional = salaryperiodrepo.findById(typechildId);
                if (salaryPeriodOptional.isPresent()) {
                    typeChildName = salaryPeriodOptional.get().getName();
                } else {
                    typeChildName = "SalaryPeriod data not found for the provided typeChildId";
                }
                break;
            case "Transmission":
                Optional<Transmission> transmissionOptional = transmissionrepo.findById(typechildId);
                if (transmissionOptional.isPresent()) {
                    typeChildName = transmissionOptional.get().getName();
                } else {
                    typeChildName = "Transmission data not found for the provided typeChildId";
                }
                break;
            case "ConstructionStatus":
                Optional<ConstructionStatus> constructionStatusOptional = constructionrepo.findById(typechildId);
                if (constructionStatusOptional.isPresent()) {
                    typeChildName = constructionStatusOptional.get().getName();
                } else {
                    typeChildName = "ConstructionStatus data not found for the provided typeChildId";
                }
                break;
            case "EducationalType":
                Optional<EducationalType> educationalTypeOptional = eduticationrepo.findById(typechildId);
                if (educationalTypeOptional.isPresent()) {
                    typeChildName = educationalTypeOptional.get().getName();
                } else {
                    typeChildName = "EducationalType data not found for the provided typeChildId";
                }
                break;
            case "Facing":
                Optional<Facing> facingOptional = facingrepo.findById(typechildId);
                if (facingOptional.isPresent()) {
                    typeChildName = facingOptional.get().getName();
                } else {
                    typeChildName = "Facing data not found for the provided typeChildId";
                }
                break;
            case "Fuel":
                Optional<Fuel> fuelOptional = fuelrepo.findById(typechildId);
                if (fuelOptional.isPresent()) {
                    typeChildName = fuelOptional.get().getName();
                } else {
                    typeChildName = "Fuel data not found for the provided typeChildId";
                }
                break;
            case "Furnshing":
                Optional<Furnshing> furnshingOptional = furnshingrepo.findById(typechildId);
                if (furnshingOptional.isPresent()) {
                    typeChildName = furnshingOptional.get().getName();
                } else {
                    typeChildName = "Furnshing data not found for the provided typeChildId";
                }
                break;
            case "ListedBy":
                Optional<ListedBy> listedByOptional = listedrepo.findById(typechildId);
                if (listedByOptional.isPresent()) {
                    typeChildName = listedByOptional.get().getName();
                } else {
                    typeChildName = "ListedBy data not found for the provided typeChildId";
                }
                break;
            case "Position":
                Optional<Position> positionOptional = positionrepo.findById(typechildId);
                if (positionOptional.isPresent()) {
                    typeChildName = positionOptional.get().getName();
                } else {
                    typeChildName = "Position data not found for the provided typeChildId";
                }
                break;
            case "Property":
                Optional<Property> propertyOptional = propertyrepo.findById(typechildId);
                if (propertyOptional.isPresent()) {
                    typeChildName = propertyOptional.get().getName();
                } else {
                    typeChildName = "Property data not found for the provided typeChildId";
                }
                break;
            case "Variant":
                Optional<Variant> variantOptional = variantrepo.findById(typechildId);
                if (variantOptional.isPresent()) {
                    typeChildName = variantOptional.get().getName();
                } else {
                    typeChildName = "Variant data not found for the provided typeChildId";
                }
                break;
            case "Vehicles":
                Optional<Vehicles> vehiclesOptional = vehiclesrepo.findById(typechildId);
                if (vehiclesOptional.isPresent()) {
                    typeChildName = vehiclesOptional.get().getName();
                } else {
                    typeChildName = "Vehicles data not found for the provided typeChildId";
                }
                break;
            case "Accessories":
                Optional<Accessories> accessoriesOptional = accessoriesRepository.findById(typechildId);
                if (accessoriesOptional.isPresent()) {
                    typeChildName = accessoriesOptional.get().getName();
                } else {
                    typeChildName = "Accessories data not found for the provided typeChildId";
                }
                break;
            default:
                typeChildName = "Unknown type: " + type;
                break;
        }

        return typeChildName;
    }    
	    	    

	    
	    @PostMapping("/Search")
	    @JsonView(ChoosendataDto.View.Summary.class)
	    public ResponseEntity<?> searchActiveFormData(@RequestBody SearchRequest searchRequest) {
	        System.out.println("Received title: " + searchRequest.getTitle());
	        System.out.println("Received page: " + searchRequest.getPage());

	        Pageable pageable = PageRequest.of(searchRequest.getPage(), 10); 
	        Page<FormDataSave> formdatasavePage = formdatasaverepo.findByTitleContainingIgnoreCaseAndActiveTrue(searchRequest.getTitle(), pageable);

	        SearchResponse searchResponse = new SearchResponse();
	        Response response;
	        
	        if (formdatasavePage.isEmpty()) {
	            searchResponse.setPage(searchRequest.getPage());
	            searchResponse.setTotalPages(0);
	            searchResponse.setTotalResults(0);
	            searchResponse.setData(new ArrayList<>());

	            response = new Response.Builder()
	                    .setStatus(false)
	                    .setMessage("Not found")
	                    .build();

	            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
	        } else {
	            try {
	                List<FormDataSaveResponse> responseList = new ArrayList<>();

	                for (FormDataSave formData : formdatasavePage.getContent()) {
	                    String dataJson = formData.getDataJson();
	                    List<ChoosendataDto> otherFields = objectMapper.readValue(dataJson, new TypeReference<List<ChoosendataDto>>() {});

	                    FormDataSaveResponse formDataSaveResponse = new FormDataSaveResponse(
	                    		otherFields,
	                            formData.getFormid(),
	                            formData.getAddress(),
	                            formData.getPathsListWithBaseUrl(),
	                            formData.getTitle(),
	                            formData.isActive()
	                    );

	                    responseList.add(formDataSaveResponse);
	                }

	                searchResponse.setPage(formdatasavePage.getNumber());
	                searchResponse.setTotalPages(formdatasavePage.getTotalPages());
	                searchResponse.setTotalResults((int) formdatasavePage.getTotalElements());
	                searchResponse.setData(responseList);

	                response = new Response.Builder()
	                        .setStatus(true)
	                        .setMessage("Success")
	                        .setSearchResponse(searchResponse)
	                        .build();

	                return ResponseEntity.ok(response);
	            } catch (Exception e) {
	                searchResponse.setPage(searchRequest.getPage());
	                searchResponse.setTotalPages(0);
	                searchResponse.setTotalResults(0);
	                searchResponse.setData(new ArrayList<>());

	                response = new Response.Builder()
	                        .setStatus(false)
	                        .setMessage("Error processing the request")
	                        .build();

	                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
	            }
	        }
	    }
	    

}