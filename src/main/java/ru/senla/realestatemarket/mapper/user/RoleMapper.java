package ru.senla.realestatemarket.mapper.user;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import ru.senla.realestatemarket.dto.user.RoleDto;
import ru.senla.realestatemarket.model.user.Role;

import java.util.Collection;
import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public abstract class RoleMapper {

    public abstract RoleDto toRoleDto(Role role);

    public abstract List<RoleDto> toRoleDto(Collection<Role> roles);

}
