package ru.senla.realestatemarket.service.property;

import ru.senla.realestatemarket.dto.property.RequestRenovationTypeDto;
import ru.senla.realestatemarket.model.property.RenovationType;
import ru.senla.realestatemarket.service.IAbstractService;

public interface IRenovationTypeService extends IAbstractService<RenovationType, Long> {

    void add(RequestRenovationTypeDto requestRenovationTypeDto);

}
