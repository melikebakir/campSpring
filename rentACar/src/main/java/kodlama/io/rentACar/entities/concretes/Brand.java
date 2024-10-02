package kodlama.io.rentACar.entities.concretes;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter

@NoArgsConstructor
@Entity
@Table (name="brands") 
@Data
@AllArgsConstructor
public class Brand {
	
	@Id //veritabanında primary key alanısın ve tekrar edevezsiz
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	//id yi otomatik 1 1 artır
	@Column(name="id")
	private int id; 
	
	@Column(name="name")
	private String name;
	
	@OneToMany(mappedBy ="brand")
	private List<Model> models;
	
	//1 numaralı markanın modellerine erişmek istiyorsan ordaki modelleri baz alacaksın
	

}
