package tumblr.api.tumblr_api.controllers;

import org.apache.coyote.BadRequestException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import tumblr.api.tumblr_api.exceptions.ElementNotFoundException;

import java.util.List;
import java.util.UUID;

public interface IController<T, D> {
    public T findById(@PathVariable UUID id) throws ElementNotFoundException;

    public List<T> find(@RequestParam(required = false) String name) throws Exception;

    public void findByIdAndDelete(@PathVariable UUID id) throws ElementNotFoundException;

    public T findByIdAndUpdate(@PathVariable UUID id, @RequestBody @Validated D body, BindingResult validation) throws Exception;

    public T create(@RequestBody @Validated D body, BindingResult validation)  throws BadRequestException;
}
