package ru.senla.realestatemarket.service.helper;

import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.Nullable;

import javax.persistence.EntityNotFoundException;

@Slf4j
public class EntityHelper {

    private EntityHelper() {}


    /**
     * If the model is null, then print a message on log and trow exception
     * @throws EntityNotFoundException if the model is null
     */
    public static  <M, I> void checkEntityOnNullAfterFoundById(M model, Class<M> modelClass, @Nullable I id) {
        if (model == null) {
            String message = "";
            if (id == null) {
                message = String.format("Specified %s not found", modelClass.getSimpleName());
            } else {
                message = String.format("%s with id %s not found", modelClass.getSimpleName(), id);
            }

            log.error(message);
            throw new EntityNotFoundException(message);
        }
    }

}
