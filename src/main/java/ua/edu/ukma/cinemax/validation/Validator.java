package ua.edu.ukma.cinemax.validation;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import ua.edu.ukma.cinemax.dto.AbstractDto;
import ua.edu.ukma.cinemax.persistance.entity.AbstractEntity;

import java.lang.reflect.Method;
import java.util.Objects;

public interface Validator <D extends AbstractDto, E extends AbstractEntity> {

    void validateFieldConstraints(D object, BindingResult result);

    default void checkUniqueFieldConstraint(D object, BindingResult result,
                                                JpaRepository<E, Long> repo,
                                                String fieldName,
                                                String failureMessage) {
        try {
            String getMethodName = "get" + StringUtils.capitalize(fieldName);
            Method getMethod = object.getClass().getMethod(getMethodName);
            Object field = getMethod.invoke(object);
            String methodName = "findBy" + StringUtils.capitalize(fieldName);
            Method method = repo.getClass().getMethod(methodName, field.getClass());
            AbstractEntity existing = (AbstractEntity) method.invoke(repo, field);
            if (existing != null && !Objects.equals(object.getId(), existing.getId())) {
                result.rejectValue(fieldName, null, failureMessage);
            }
        } catch(Exception e) {
            throw new ValidationException(e);
        }
    }
}
