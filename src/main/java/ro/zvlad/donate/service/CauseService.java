package ro.zvlad.donate.service;

import org.springframework.stereotype.Service;
import ro.zvlad.donate.dto.cause.CauseDto;
import ro.zvlad.donate.dto.cause.CauseIdDto;
import ro.zvlad.donate.exceptions.GeneralException;
import ro.zvlad.donate.model.Cause;
import ro.zvlad.donate.repo.CauseRepo;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.ValidatorFactory;
import javax.validation.constraints.NotNull;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

@Service
public class CauseService {
    private final CauseRepo causeRepo;

    public CauseService(CauseRepo causeRepo) {
        this.causeRepo = causeRepo;
    }

    public Cause getCause(int id){
        try {
            return causeRepo.getById(id);
        }catch (IndexOutOfBoundsException ex){
            throw new GeneralException("Cause not found",404);
        }
    }

    public List<CauseIdDto> getCauses(int page){
        return causeRepo.getCauses(page).stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    public CauseIdDto mapToDto(Cause cause){
        return new CauseIdDto(cause.getId(),cause.getName(),cause.getDescription(),cause.getTarget_amount(),cause.getCurrency(),cause.getCreated_on(),cause.getEnding_on());
    }

    public int getCausesCount(){
        return causeRepo.getCausesCount();
    }

    public int getCausesTotalAmount(int id){return causeRepo.getCausesTotalAmount(id);}

    public int addCause(CauseDto cause){
        return causeRepo.insert(new Cause(cause)).getId();
    }

    public boolean removeCause(int id){
        return causeRepo.delete(id);
    }

    public Cause updatePartial(@NotNull int id, @NotNull Map<String, Object> data){
        Cause cause;
        try {
            cause = causeRepo.getById(id);
        }catch(IndexOutOfBoundsException ex){
            throw new GeneralException("Cause not found",404);
        }

        Field[] causeFields = CauseDto.class.getDeclaredFields();
        AtomicBoolean changedFields= new AtomicBoolean(false);
        for (Field causeField : causeFields) {
            data.forEach((key, value) -> {
                if (key.equalsIgnoreCase(causeField.getName()) ) {
                    try {
                        final Field declaredField = Cause.class.getDeclaredField(key);
                        declaredField.setAccessible(true);

                        if(!value.equals(declaredField.get(cause))) {
                            declaredField.set(cause, value);
                            changedFields.set(true);
                        }
                    } catch (NoSuchFieldException | IllegalAccessException e) {
                        throw new GeneralException("Something went wrong at server while partial updation",400);
                    }
                }
            });
        }
        if(!changedFields.get()){
            throw new GeneralException("Nothing to update",400);
        }

        CauseDto causeDto = new CauseDto(cause.getName(),cause.getDescription(),cause.getTarget_amount(),cause.getCurrency(),cause.getCreated_on(),cause.getEnding_on());
        if(validatorDto(causeDto))
            causeRepo.save(cause);

        return cause;
    }

    public boolean validatorDto(CauseDto causeDto){
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        var validator = factory.getValidator();
        Set<ConstraintViolation<CauseDto>> violations = validator.validate(causeDto);

        if(violations.isEmpty())
            return true;
        throw new GeneralException("Invalid value on field "+violations.stream().findFirst().get().getPropertyPath().toString(),400);
    }

}
