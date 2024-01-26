package com.example.books.service.category;

import com.example.books.dto.category.CategoryDto;
import com.example.books.dto.category.CreateCategoryRequestDto;
import com.example.books.exception.EntityNotFoundException;
import com.example.books.mapper.CategoryMapper;
import com.example.books.model.Category;
import com.example.books.repository.category.CategoryRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Override
    public List<CategoryDto> findAll(Pageable pageable) {
        return categoryRepository.findAll(pageable).stream()
                .map(categoryMapper::toDto)
                .toList();
    }

    @Override
    public CategoryDto getById(Long id) {
        return categoryMapper.toDto(categoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "There is no category with id " + id)));
    }

    @Override
    public CategoryDto save(CreateCategoryRequestDto createCategoryRequestDto) {
        return categoryMapper.toDto(
                categoryRepository.save(categoryMapper.toEntity(createCategoryRequestDto)));
    }

    @Override
    public CategoryDto update(Long id, CreateCategoryRequestDto createCategoryRequestDto) {
        Category category = categoryMapper.toEntity(createCategoryRequestDto);
        category.setId(id);
        return categoryMapper.toDto(categoryRepository.save(category));
    }

    @Override
    public void deleteById(Long id) {
        categoryRepository.deleteById(id);
    }
}
