package com.ruoyi.manage.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.manage.domain.Departments;
import com.ruoyi.manage.service.IDepartmentsService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 学院信息Controller
 * 
 * @author ruoyi
 * @date 2024-08-13
 */
@RestController
@RequestMapping("/manage/departments")
public class DepartmentsController extends BaseController
{
    @Autowired
    private IDepartmentsService departmentsService;

    /**
     * 查询学院信息列表
     */
    @PreAuthorize("@ss.hasPermi('manage:departments:list')")
    @GetMapping("/list")
    public TableDataInfo list(Departments departments)
    {
        startPage();
        List<Departments> list = departmentsService.selectDepartmentsList(departments);
        return getDataTable(list);
    }

    /**
     * 导出学院信息列表
     */
    @PreAuthorize("@ss.hasPermi('manage:departments:export')")
    @Log(title = "学院信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Departments departments)
    {
        List<Departments> list = departmentsService.selectDepartmentsList(departments);
        ExcelUtil<Departments> util = new ExcelUtil<Departments>(Departments.class);
        util.exportExcel(response, list, "学院信息数据");
    }

    /**
     * 获取学院信息详细信息
     */
    @PreAuthorize("@ss.hasPermi('manage:departments:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(departmentsService.selectDepartmentsById(id));
    }

    /**
     * 新增学院信息
     */
    @PreAuthorize("@ss.hasPermi('manage:departments:add')")
    @Log(title = "学院信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Departments departments)
    {
        return toAjax(departmentsService.insertDepartments(departments));
    }

    /**
     * 修改学院信息
     */
    @PreAuthorize("@ss.hasPermi('manage:departments:edit')")
    @Log(title = "学院信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Departments departments)
    {
        return toAjax(departmentsService.updateDepartments(departments));
    }

    /**
     * 删除学院信息
     */
    @PreAuthorize("@ss.hasPermi('manage:departments:remove')")
    @Log(title = "学院信息", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(departmentsService.deleteDepartmentsByIds(ids));
    }
}
