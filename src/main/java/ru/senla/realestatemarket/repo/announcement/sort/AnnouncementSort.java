package ru.senla.realestatemarket.repo.announcement.sort;

import org.springframework.data.domain.Sort;

import java.util.List;

/**
 * @author Alexander Slotin (@alexsnitol)
 */

public class AnnouncementSort {

    private AnnouncementSort() {}

    /**
     * Default sort for announcements
     */
    public static Sort byTopDescAndPropertyOwnerRatingAscAndCreatedDtAsc() {
        return Sort.by(List.of(
                new Sort.Order(Sort.Direction.DESC, "top"),
                new Sort.Order(Sort.Direction.DESC, "property.owner.rating"),
                new Sort.Order(Sort.Direction.DESC, "createdDt")
        ));
    }

    public static Sort byCreatedDtAsc() {
        return Sort.by(List.of(
                new Sort.Order(Sort.Direction.ASC, "createdDt")
        ));
    }

}
