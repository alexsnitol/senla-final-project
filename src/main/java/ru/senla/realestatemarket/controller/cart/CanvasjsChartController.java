package ru.senla.realestatemarket.controller.cart;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.senla.realestatemarket.model.announcement.ApartmentAnnouncement;
import ru.senla.realestatemarket.model.announcement.FamilyHouseAnnouncement;
import ru.senla.realestatemarket.model.announcement.HousingAnnouncementTypeEnum;
import ru.senla.realestatemarket.model.announcement.LandAnnouncement;
import ru.senla.realestatemarket.model.user.Message;
import ru.senla.realestatemarket.service.announcement.IApartmentAnnouncementService;
import ru.senla.realestatemarket.service.announcement.IFamilyHouseAnnouncementService;
import ru.senla.realestatemarket.service.announcement.ILandAnnouncementService;
import ru.senla.realestatemarket.service.user.IMessageService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.SplittableRandom;

/**
 * @author Alexander Slotin (<a href="https://github.com/alexsnitol">@alexsnitol</a>) <p>
 * 2022 Nov
 */
@Controller
@RequestMapping("/canvas")
public class CanvasjsChartController {

    private final IApartmentAnnouncementService apartmentAnnouncementService;
    private final IFamilyHouseAnnouncementService familyHouseAnnouncementService;
    private final ILandAnnouncementService landAnnouncementService;

    private final IMessageService messageService;


    public CanvasjsChartController(
            IApartmentAnnouncementService apartmentAnnouncementService,
            IFamilyHouseAnnouncementService familyHouseAnnouncementService,
            ILandAnnouncementService landAnnouncementService,
            IMessageService messageService
    ) {
        this.apartmentAnnouncementService = apartmentAnnouncementService;
        this.familyHouseAnnouncementService = familyHouseAnnouncementService;
        this.landAnnouncementService = landAnnouncementService;
        this.messageService = messageService;
    }


    @GetMapping("/property-types-of-announcements")
    public String typeOfAnnouncements(ModelMap modelMap) {
        List<Map<Object,Object>> dataPoints = new ArrayList<>();
        Map<Object, Object> map;

        int numberOfApartmentAnnouncement = apartmentAnnouncementService.getAll().size();
        map = new HashMap<>(); map.put("y", numberOfApartmentAnnouncement); map.put("label", "Apartment");
        dataPoints.add(map);

        int numberOfFamilyHouseAnnouncement = familyHouseAnnouncementService.getAll().size();
        map = new HashMap<>(); map.put("y", numberOfFamilyHouseAnnouncement); map.put("label", "Family house");
        dataPoints.add(map);

        int numberOfLandAnnouncement = landAnnouncementService.getAll().size();
        map = new HashMap<>(); map.put("y", numberOfLandAnnouncement); map.put("label", "Land");
        dataPoints.add(map);

        modelMap.addAttribute("dataPointsList", dataPoints);

        return "property-types-of-announcements";
    }

    @GetMapping("/types-of-announcements")
    public String statusesOfAnnouncements(ModelMap modelMap) {
        List<Map<Object,Object>> dataPoints = new ArrayList<>();
        Map<Object, Object> map;

        List<ApartmentAnnouncement> apartmentAnnouncements = apartmentAnnouncementService.getAll();
        List<FamilyHouseAnnouncement> familyHouseAnnouncements = familyHouseAnnouncementService.getAll();
        List<LandAnnouncement> landAnnouncements = landAnnouncementService.getAll();

        long numberOfRentType =
                apartmentAnnouncements.stream().filter(a -> {
                    return a.getType() == HousingAnnouncementTypeEnum.MONTHLY_RENT
                            || a.getType() == HousingAnnouncementTypeEnum.DAILY_RENT;
                }).count() +
                familyHouseAnnouncements.stream().filter(a -> {
                    return a.getType() == HousingAnnouncementTypeEnum.MONTHLY_RENT
                            || a.getType() == HousingAnnouncementTypeEnum.DAILY_RENT;
                }).count();

        long numberOfSellType =
                apartmentAnnouncements.stream()
                        .filter(a -> a.getType() == HousingAnnouncementTypeEnum.SELL).count()
                        + familyHouseAnnouncements.stream()
                        .filter(a -> a.getType() == HousingAnnouncementTypeEnum.SELL).count()
                        + landAnnouncements.size();

        float percentOfNumberOfRentType = ((float) numberOfRentType / (float) (numberOfRentType + numberOfSellType)) * 100F;
        float percentOfNumberOfSellType = ((float) numberOfSellType / (float) (numberOfRentType + numberOfSellType)) * 100F;

        map = new HashMap<>(); map.put("y", percentOfNumberOfRentType); map.put("label", "Rent");
        dataPoints.add(map);

        map = new HashMap<>(); map.put("y", percentOfNumberOfSellType); map.put("label", "Sell");
        dataPoints.add(map);

        modelMap.addAttribute("dataPointsList", dataPoints);

        return "types-of-announcements";
    }

    @GetMapping("/messages-time")
    public String messagesTime(ModelMap modelMap) {
        List<Map<Object,Object>> dataPoints = new ArrayList<>();
        Map<Object, Object> map;

        List<Message> messages = getRandomMessages(100000L);

        for (int hour = 0; hour <= 23; hour++) {
            for (int minute = 0; minute <= 59; minute++) {
                int finalHours = hour;
                int finalMinutes = minute;

                long count = messages.stream().filter(m -> {
                    return m.getCreatedDt().getHour() == finalHours && m.getCreatedDt().getMinute() == finalMinutes;
                }).count();

                if (hour >= 10 && hour <= 15)
                    count *= getRandomNumber(2, 4);

                if (hour > 15 && hour <= 20)
                    count *= getRandomNumber(2, 6);

                if (hour > 20 && hour <= 22)
                    count *= getRandomNumber(1, 5);

                map = new HashMap<>();
                map.put("hour", hour);
                map.put("minute", minute);
                map.put("y", count);
                dataPoints.add(map);
            }
        }

        modelMap.addAttribute("dataPointsList", dataPoints);

        return "messages-time";
    }

    private List<Message> getRandomMessages(Long number) {
        List<Message> messageList = new LinkedList<>();

        for (long i = 1; i <= number; i++) {
            Message randomMessage = new Message();

            randomMessage.setCreatedDt(LocalDateTime.of(
                    LocalDate.now(),
                    LocalTime.of(
                            getRandomNumber(0, 24),
                            getRandomNumber(0, 60)
                    )
            ));

            messageList.add(randomMessage);
        }

        return messageList;
    }

    private int getRandomNumber(int from, int to) {
        if (to >= from)
            try {
                return new SplittableRandom().nextInt(from, to);
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException(e.getMessage());
            }
        else
            return -1;
    }

}
