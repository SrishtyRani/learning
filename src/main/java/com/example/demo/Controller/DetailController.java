package com.example.demo.Controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.Model.Accessories;
import com.example.demo.Model.Brand;
import com.example.demo.Model.BrandModel;
import com.example.demo.Model.Categories;
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
import com.example.demo.Model.SubCatrgories;
import com.example.demo.Model.Transmission;
import com.example.demo.Model.Variant;
import com.example.demo.Model.Vehicles;
import com.example.demo.Repository.AccessoriesRepository;
import com.example.demo.Repository.BrandModelRepository;
import com.example.demo.Repository.BrandRepository;
import com.example.demo.Repository.CategoriesRepository;
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
import com.example.demo.Repository.SubCategoriesRepository;
import com.example.demo.Repository.TransmissionRepository;
import com.example.demo.Repository.VariantRepository;
import com.example.demo.Repository.VehiclesRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
@RequestMapping("/admin/Formsavedata")
public class DetailController {

	@Autowired
	private FormDataSaveRepository  formDataSaveRepository ;
	
    @Autowired
    private FormDataRepository formDataRepository; 
    
    @Autowired
    private SubCategoriesRepository subcategoryRepository; 
    
    @Autowired
    private CategoriesRepository categoryRepository; 
    
    
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
	
    
   
    private final ObjectMapper objectMapper = new ObjectMapper();


    
    @GetMapping("/{id}/details")
    public String getFormDataDetails(@PathVariable("id") Long id, Model model) throws JsonProcessingException {
        Optional<FormDataSave> formDataSaveOptional = formDataSaveRepository.findById(id);

        if (formDataSaveOptional.isPresent()) {
            FormDataSave formDataSave = formDataSaveOptional.get();

            Optional<FormData> formDataOptional = formDataRepository.findById(formDataSave.getFormid());

            if (formDataOptional.isPresent()) {
                FormData formData = formDataOptional.get();

                Optional<SubCatrgories> subcategoryOptional = subcategoryRepository.findById(formData.getSubcategory());

                if (subcategoryOptional.isPresent()) {
                    SubCatrgories subcategory = subcategoryOptional.get();

                    Optional<Categories> categoryOptional = categoryRepository.findById(formData.getCategory());
                    

              

                    List<String> pathse = formDataSave.getPathsListWithBaseUrl(); 
                    for (String path : pathse) {
                        System.out.println("Path: " + path);
                    }

                    
                    if (categoryOptional.isPresent()) {
                        Categories category = categoryOptional.get();

                        model.addAttribute("formDataSave", formDataSave);
                        model.addAttribute("formData", formData);
                        model.addAttribute("subcategory", subcategory);
                        model.addAttribute("category", category);

                        List<Map<String, Object>> dataList = objectMapper.readValue(formDataSave.getDataJson(), new TypeReference<List<Map<String, Object>>>() {});
                        model.addAttribute("typeDataList", dataList);
                        model.addAttribute("pathse", pathse);

                        List<String> typeChildNames = new ArrayList<>();
                        System.out.println(dataList);
                        for (Map<String, Object> data : dataList) {
                            String type = (String) data.get("type");
                            Object typechildIdObj = data.get("typechildId");

                            System.out.println("Data map: " + data);
                            System.out.println("TypechildIdObj: " + typechildIdObj);
                            Long typechildId = null;

                            if (typechildIdObj != null) {
                                if (typechildIdObj instanceof Number) {
                                    typechildId = ((Number) typechildIdObj).longValue();
                                } else if (typechildIdObj instanceof String) {
                                    try {
                                        typechildId = Long.parseLong((String) typechildIdObj);
                                    } catch (NumberFormatException e) {
                                        System.err.println("Error parsing typechildId: " + e.getMessage());
                                    }
                                } else {
                                    System.err.println("Unexpected type for typechildId: " + typechildIdObj.getClass().getName());
                                }
                                
                                String typeChildName = null;
                                
                                switch (type) {
                               
                                    case "brand":
                                        Optional<Brand> brandOptional = brandrepo.findById(typechildId);
                                        if (brandOptional.isPresent()) {
                                        	System.out.println(type);
                                            Brand brand = brandOptional.get();
                                            if (brand.getId().equals(typechildId)) {
                                                String name = brand.getName();
                                                typeChildName = name;
                                            } else {
                                                typeChildName = "Type Child ID does not match Brand ID";
                                            }
                                        } else {
                                            typeChildName = "Brand data not found for the provided typeChildId";
                                        }
                                        break;
                                        
                                    case "ConstructionStatus":
                                        Optional<ConstructionStatus> constructionStatusOptional = constructionrepo.findById(typechildId);
                                        if (constructionStatusOptional.isPresent()) {
                                        	System.out.println(type);
                                        	ConstructionStatus constructionStatus = constructionStatusOptional.get();
                                            if (constructionStatus.getId().equals(typechildId)) {
                                                String name = constructionStatus.getName();
                                                typeChildName = name;
                                            } else {
                                                typeChildName = "Type Child ID does not match Brand ID";
                                            }
                                        } else {
                                            typeChildName = "Brand data not found for the provided typeChildId";
                                        }
                                        break;
                                        
                                    case "EducationalType":
                                        Optional<EducationalType> educationalTypeOptional = eduticationrepo.findById(typechildId);
                                        if (educationalTypeOptional.isPresent()) {
                                        	System.out.println(type);
                                        	EducationalType educationalType = educationalTypeOptional.get();
                                            if (educationalType.getId().equals(typechildId)) {
                                                String name = educationalType.getName();									
                                                typeChildName = name;
                                            } else {
                                                typeChildName = "Type Child ID does not match Brand ID";
                                            }
                                        } else {
                                            typeChildName = "Brand data not found for the provided typeChildId";
                                        }
                                        break;
                                        
                                    case "Fuel":
                                        Optional<Fuel> fuelOptional = fuelrepo.findById(typechildId);
                                        if (fuelOptional.isPresent()) {
                                        	System.out.println(type);
                                        	Fuel fuel = fuelOptional.get();
                                            if (  fuel.getId().equals(typechildId)) {
                                                String name =   fuel.getName();
                                                typeChildName = name;
                                            } else {
                                                typeChildName = "Type Child ID does not match Brand ID";
                                            }
                                        } else {
                                            typeChildName = "Brand data not found for the provided typeChildId";
                                        }
                                        break;
                                        
                                        
                                    case "Facing":
                                        Optional<Facing> facingOptional = facingrepo.findById(typechildId);
                                        if (facingOptional.isPresent()) {
                                        	System.out.println(type);
                                        	Facing facing = facingOptional.get();
                                            if ( facing.getId().equals(typechildId)) {
                                                String name =  facing.getName();
                                                typeChildName = name;
                                            } else {
                                                typeChildName = "Type Child ID does not match Brand ID";
                                            }
                                        } else {
                                            typeChildName = "Brand data not found for the provided typeChildId";
                                        }
                                        break;
                                        
                                    case "Furnshing":
                                        Optional<Furnshing> furnshingOptional = furnshingrepo.findById(typechildId);
                                        if ( furnshingOptional.isPresent()) {
                                        	System.out.println(type);
                                        	Furnshing furnshing =  furnshingOptional.get();
                                            if ( furnshing.getId().equals(typechildId)) {
                                                String name =  furnshing.getName();
                                                typeChildName = name;
                                            } else {
                                                typeChildName = "Type Child ID does not match Brand ID";
                                            }
                                        } else {
                                            typeChildName = "Brand data not found for the provided typeChildId";
                                        }
                                        break;
                                        
                                    case "ListedBy":
                                        Optional<ListedBy> listedByOptional = listedrepo.findById(typechildId);
                                        if ( listedByOptional .isPresent()) {
                                        	System.out.println(type);
                                        	ListedBy listedBy =  listedByOptional .get();
                                            if ( listedBy.getId().equals(typechildId)) {
                                                String name =  listedBy.getName();
                                                typeChildName = name;
                                            } else {
                                                typeChildName = "Type Child ID does not match Brand ID";
                                            }
                                        } else {
                                            typeChildName = "Brand data not found for the provided typeChildId";
                                        }
                                        break;
                                        
                                    case "Position":
                                        Optional<Position> positionOptional = positionrepo.findById(typechildId);
                                        if ( positionOptional .isPresent()) {
                                        	System.out.println(type);
                                        	Position Position =  positionOptional .get();
                                            if ( Position.getId().equals(typechildId)) {
                                                String name =  Position.getName();
                                                typeChildName = name;
                                            } else {
                                                typeChildName = "Type Child ID does not match Brand ID";
                                            }
                                        } else {
                                            typeChildName = "Brand data not found for the provided typeChildId";
                                        }
                                        break;
                                        
                                    case "brandmodel":
                                        Optional<BrandModel> brandmodelOptional =brandmodelrepo.findById(typechildId);
                                        if (brandmodelOptional.isPresent()) {
                                        	System.out.println(type);
                                        	BrandModel  brandmodel = brandmodelOptional.get();
                                            if (brandmodel.getId().equals(typechildId)) {
                                                String name = brandmodel.getName();
                                                typeChildName = name;
                                            } else {
                                                typeChildName = "Type Child ID does not match Brand ID";
                                            }
                                        } else {
                                            typeChildName = "Brand data not found for the provided typeChildId";
                                        }
                                        break;     
                                        
                                    case "Property":
                                        Optional<Property> propertyOptional =propertyrepo.findById(typechildId);
                                        if (propertyOptional.isPresent()) {
                                        	System.out.println(type);
                                        	Property property = propertyOptional.get();
                                            if (property .getId().equals(typechildId)) {
                                                String name = property .getName();
                                                typeChildName = name;
                                            } else {
                                                typeChildName = "Type Child ID does not match Brand ID";
                                            }
                                        } else {
                                            typeChildName = "Brand data not found for the provided typeChildId";
                                        }
                                        break;
                                        
                                    case "SalaryPeriod":
                                        Optional<SalaryPeriod> salaryPeriodOptional =salaryperiodrepo.findById(typechildId);
                                        if (salaryPeriodOptional.isPresent()) {
                                        	System.out.println(type);
                                        	SalaryPeriod SalaryPeriod = salaryPeriodOptional.get();
                                            if (SalaryPeriod .getId().equals(typechildId)) {
                                                String name = SalaryPeriod .getName();
                                                typeChildName = name;
                                            } else {
                                                typeChildName = "Type Child ID does not match Brand ID";
                                            }
                                        } else {
                                            typeChildName = "Brand data not found for the provided typeChildId";
                                        }
                                        break;
                                        
                                        
                                    case "Transmission":
                                        Optional<Transmission> transmissionOptional =transmissionrepo.findById(typechildId);
                                        if (transmissionOptional.isPresent()) {
                                        	System.out.println(type);
                                        	Transmission SalaryPeriod = transmissionOptional.get();
                                            if (SalaryPeriod .getId().equals(typechildId)) {
                                                String name = SalaryPeriod .getName();
                                                typeChildName = name;
                                            } else {
                                                typeChildName = "Type Child ID does not match Brand ID";
                                            }
                                        } else {
                                            typeChildName = "Brand data not found for the provided typeChildId";
                                        }
                                        break;
                                        
                                        
                                    case "Variant":
                                        Optional<Variant> variantOptional =variantrepo.findById(typechildId);
                                        if (variantOptional.isPresent()) {
                                        	System.out.println(type);
                                        	Variant variant = variantOptional.get();
                                            if (variant .getId().equals(typechildId)) {
                                                String name = variant .getName();
                                                typeChildName = name;
                                            } else {
                                                typeChildName = "Type Child ID does not match Brand ID";
                                            }
                                        } else {
                                            typeChildName = "Brand data not found for the provided typeChildId";
                                        }
                                        break;
                                        
                                        
                                        
                                    case "Vehicles":
                                        Optional<Vehicles> vehiclesOptional =vehiclesrepo.findById(typechildId);
                                        if (vehiclesOptional.isPresent()) {
                                        	System.out.println(type);
                                        	Vehicles variant = vehiclesOptional.get();
                                            if (variant .getId().equals(typechildId)) {
                                                String name = variant .getName();
                                                typeChildName = name;
                                            } else {
                                                typeChildName = "Type Child ID does not match Brand ID";
                                            }
                                        } else {
                                            typeChildName = "Brand data not found for the provided typeChildId";
                                        }
                                        break;
                                        
                                        
                                    case "Accessories":
                                        Optional<Accessories> accessoriesOptional = accessoriesRepository.findById(typechildId);
                                        if (accessoriesOptional.isPresent()) {
                                            Accessories accessories = accessoriesOptional.get();
                                            if (accessories.getId().equals(typechildId)) {
                                                String name = accessories.getName();
                                                System.out.println("Accessories Name: " + name);
                                                typeChildName = name;
                                            } else {
                                                typeChildName = "Type Child ID does not match Accessories ID";
                                            }
                                        } else {
                                            typeChildName = "Accessories data not found for the provided typeChildId";
                                        }
                                        break;
                                    default:
                                        typeChildName = "Unknown type";
                                        break;
                                }

                                data.put("typeChildName", typeChildName);

                            }
                        }

                        model.addAttribute("typeDataList", dataList);

                        return "FormDataSave_List";
                    } else {
                        model.addAttribute("error", "Category not found");
                    }
                } else {
                    model.addAttribute("error", "Subcategory not found");
                }
            } else {
                model.addAttribute("error", "Form data not found");
            }
        } else {
            model.addAttribute("error", "Form data save not found");
        }

        return "error";
    }


}
