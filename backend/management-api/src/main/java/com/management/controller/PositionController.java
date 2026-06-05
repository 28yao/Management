package com.management.controller;

import com.management.common.ApiResponse;
import com.management.dto.request.PositionRequest;
import com.management.entity.Position;
import com.management.service.PositionService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * 职位管理控制器
 *
 * @author management
 * @date 2024-01-01
 */
@RestController
@RequestMapping("/positions")
public class PositionController {

    private final PositionService positionService;

    public PositionController(PositionService positionService) {
        this.positionService = positionService;
    }

    /**
     * 获取职位列表
     */
    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public ApiResponse<List<Position>> list() {
        return ApiResponse.success(positionService.listAll());
    }

    /**
     * 新增职位
     */
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<Position> create(@Valid @RequestBody PositionRequest request) {
        Position position = new Position();
        position.setName(request.getName());
        return ApiResponse.success(positionService.create(position));
    }

    /**
     * 修改职位
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<Position> update(@PathVariable Long id, @Valid @RequestBody PositionRequest request) {
        Position position = new Position();
        position.setName(request.getName());
        return ApiResponse.success(positionService.update(id, position));
    }

    /**
     * 删除职位
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<String> delete(@PathVariable Long id) {
        positionService.delete(id);
        return ApiResponse.success("删除成功", null);
    }
}
