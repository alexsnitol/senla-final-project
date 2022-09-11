package ru.senla.realestatemarket.model.property;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.senla.realestatemarket.model.announcement.FamilyHouseAnnouncement;
import ru.senla.realestatemarket.model.house.FamilyHouse;

import javax.annotation.PostConstruct;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "family_house_properties")
public class FamilyHouseProperty extends HousingProperty
        implements IPropertyWithAnnouncementList<FamilyHouseAnnouncement> {

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "family_house_id")
    @JsonManagedReference
    private FamilyHouse familyHouse;

    @OneToMany(mappedBy = "property", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<FamilyHouseAnnouncement> announcementList;


    @PostConstruct
    public void initConstruct() {
        setPropertyType(PropertyTypeEnum.FAMILY_HOUSE);
    }

}

