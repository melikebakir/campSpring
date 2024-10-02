package kodlama.io.rentACar.business.concretes;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import kodlama.io.rentACar.business.abstracts.BrandService;
import kodlama.io.rentACar.business.requests.CreateBrandRequest;
import kodlama.io.rentACar.business.requests.UpdateBrandRequest;
import kodlama.io.rentACar.business.responses.GetAllBrandsResponse;
import kodlama.io.rentACar.business.responses.GetByIdBrandResponse;
import kodlama.io.rentACar.core.utilities.mappers.ModelMapperService;
import kodlama.io.rentACar.dataAccess.abstracts.BrandRepository;
import kodlama.io.rentACar.entities.concretes.Brand;
import lombok.AllArgsConstructor;

@Service //bu sınıf bir business nesnesidir
@AllArgsConstructor
public class BrandManager implements BrandService {
	private BrandRepository brandRepostory;
	private ModelMapperService modelMapperService;
	
	/*@Autowired --> bunun yerine @AllArgsConst kullandık
	public BrandManager(BrandRepository brandRepostory) {
		
		this.brandRepostory = brandRepostory;
	}*/
	@Override
	public List<GetAllBrandsResponse> getAll(){ 
		
		List<Brand> brands =brandRepostory.findAll();
		//List<GetAllBrandsResponse> brandRespose =new ArrayList<GetAllBrandsResponse>();
		
		/*for (Brand brand : brands) {
			GetAllBrandsResponse resposeItem =new GetAllBrandsResponse();
			resposeItem.setId(brand.getId());
			resposeItem.setName(brand.getName());
			brandRespose.add(resposeItem);
		}*/
		
		List<GetAllBrandsResponse> brandsResponse = brands.stream().map(brand->this.modelMapperService
				.forResponse().map(brand, GetAllBrandsResponse.class)).collect(Collectors.toList());
				
		return brandsResponse;
	}
	@Override
	public void add(CreateBrandRequest createBrandRequest) {
		
		/*Brand brand = new Brand();
		brand.setName(createBrandRequest.getName());
		//burda çok fazla alan da olabilirdi o yüzden bu kullanımı değiştiriyoruz*/
		
		Brand brand= this.modelMapperService.forRequest().map(createBrandRequest, Brand.class);
		this.brandRepostory.save(brand);
		
	}
	@Override
	public GetByIdBrandResponse getById(int id) {
		// Belirli bir ID'ye sahip markayı veritabanından alır. Eğer marka bulunamazsa, bir istisna fırlatılır.
		Brand brand=this.brandRepostory.findById(id).orElseThrow();
		 // Marka nesnesini GetByIdBrandResponse nesnesine dönüştürür.
		GetByIdBrandResponse response =this.modelMapperService.forResponse().map(brand, GetByIdBrandResponse.class);
		
		return response;
	}
	@Override
	public void update(UpdateBrandRequest updateBrandRequest) {
		// Güncelleme isteği nesnesini Brand nesnesine dönüştürür.
		Brand brand= this.modelMapperService.forRequest().map(updateBrandRequest, Brand.class);
		 // Dönüştürülmüş markayı veritabanında günceller.
		this.brandRepostory.save(brand);	
	}
	@Override
	public void delete(int id) {
		// Belirli bir ID'ye sahip markayı veritabanından siler.
		this.brandRepostory.deleteById(id);
		
	}
	

}
