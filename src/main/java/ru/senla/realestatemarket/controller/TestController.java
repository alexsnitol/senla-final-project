package ru.senla.realestatemarket.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.senla.realestatemarket.model.house.House;
import ru.senla.realestatemarket.model.property.Property;
import ru.senla.realestatemarket.model.user.Message;
import ru.senla.realestatemarket.service.announcement.IAnnouncementService;
import ru.senla.realestatemarket.service.house.IHouseService;
import ru.senla.realestatemarket.service.property.IPropertyService;
import ru.senla.realestatemarket.service.user.IMessageService;

import java.util.List;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/api/test")
public class TestController {

    private final IPropertyService propertyService;
    private final IHouseService houseService;
    private final IAnnouncementService announcementService;
    private final IMessageService messageService;


    @GetMapping("/properties")
    public List<Property> getAllProperties() {
        List<Property> result = propertyService.getAll();
        return result;
    }

    @GetMapping("/houses")
    public List<House> getAllHouses() {
        List result = houseService.getAll();
        return result;
    }

    @GetMapping("/announcements")
    public List<House> getAllAnnouncements() {
        List result = announcementService.getAll();
        return result;
    }

    @GetMapping("/messages")
    public List<Message> getAllMessages() {
        List result = messageService.getAll();
        return result;
    }

}
