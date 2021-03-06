package com.nuist.hospitalcare.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.nuist.hospitalcare.bean.ResultBean;
import com.nuist.hospitalcare.entity.Dependent;
import com.nuist.hospitalcare.service.DependentService;

/**
 * 家属信息控制器
 * @author 97784
 *
 */
@RestController
@RequestMapping(value = "dependent")// 所有具有前缀dependent均路由到此控制器
public class DependentController {
	//private static final int PAGE_SIZE=10;//分页查询每页大小
	@Autowired
	private DependentService dependentService;
	
	/**
	 * 添加家属信息
	 * @param dependent 家属信息
	 * @param bindingResult 
	 * @return
	 */
	@PostMapping(value = "insert")
	public ResultBean insert(@Validated Dependent dependent,BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			StringBuffer msgBuffer= new StringBuffer();
			for (FieldError iterable_element : bindingResult.getFieldErrors()) {
				msgBuffer.append(iterable_element.getField()+":"+iterable_element.getDefaultMessage()+"\n");
			}	
			return new ResultBean(5006, false,msgBuffer.toString(), null);
		}
		boolean flag=dependentService.insert(dependent);
		if(flag) {
			return new ResultBean(200,true,"添加成功","");
		}else {
			return new ResultBean(5004,false,"添加失败","");
		}
	}
	
	/**
	 * 修改家属信息
	 * @param dependent 家属信息
	 * @param bindingResult 
	 * @return
	 */
	@PutMapping(value = "update")
	public ResultBean update(String update) {
		JSONObject t = JSON.parseObject(update);
		System.out.println(t);
		Dependent oldDependent = JSON.parseObject(t.getString("old"), Dependent.class);
		Dependent newDependent = JSON.parseObject(t.getString("new"), Dependent.class);
		System.out.println(oldDependent);
		System.out.println(newDependent);
		boolean flag=dependentService.update(oldDependent,newDependent);
		if(flag) {
			return new ResultBean(200,true,"修改成功","");
		}else {
			return new ResultBean(5004,false,"修改失败","");
		}
	}
	
	/**
	 * 根据客户编号删除该客户的全部家属信息
	 * @param cid
	 * @return
	 */
	@DeleteMapping(value = "deleteallbycid/{cid}")
	public ResultBean deleteAllByCid(@PathVariable("cid")Integer cid) {
		boolean flag=dependentService.deleteAllByCid(cid);
		if(flag) {
			return new ResultBean(200,true,"删除成功","");
		}else {
			return new ResultBean(5004,false,"删除失败","");
		}
	}
	
	
	/**
	 * 根据主键删除指定家属信息
	 * @return
	 */
	@DeleteMapping(value = "delete")
	public ResultBean delete(Dependent dependent) {
		boolean flag=dependentService.delete(dependent);
		if(flag) {
			return new ResultBean(200,true,"删除成功","");
		}else {
			return new ResultBean(5004,false,"删除失败","");
		}
	}
	
	/**
	 * 根据客户编号cid，分页查询该客户的全部家属信息
	 * @param page 页号
	 * @return
	 */
	@GetMapping(value = "findbycid")
	public ResultBean findByCid(Integer cid,Integer page,Integer limit) {
		page=page-1;
		Page<Dependent> dependentPage=dependentService.findByCid(cid, PageRequest.of(page, limit));
		return new ResultBean(200,true,"查询成功",dependentPage);
	}
	
	/**
	 * 分页查询所有家属信息
	 * @param page 页号
	 * @return
	 */
	@GetMapping(value = "findall")
	public ResultBean findAll(Integer page,Integer limit) {
		page=page-1;
		Page<Dependent> customerHealthPage=dependentService.findAll(PageRequest.of(page, limit));
		return new ResultBean(200,true,"分页查询成功",customerHealthPage);
	}
}
