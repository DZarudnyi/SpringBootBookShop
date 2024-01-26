package com.example.books.mapper;

import com.example.books.config.MapperConfig;
import com.example.books.dto.category.CategoryDto;
import com.example.books.dto.category.CreateCategoryRequestDto;
import com.example.books.model.Category;
import org.mapstruct.Mapper;

@Mapper(config = MapperConfig.class, componentModel = "spring")
public interface CategoryMapper {
    CategoryDto toDto(Category category);

    Category toEntity(CreateCategoryRequestDto createCategoryRequestDto);
}
