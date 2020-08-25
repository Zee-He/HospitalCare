package com.nuist.hospitalcare.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import com.nuist.hospitalcare.entity.ServiceRelationship;
import com.nuist.hospitalcare.entity.ServiceRelationshipKey;
/**
 * 服务对象关系数据访问层接口ServiceRelationshipRepository
 * @author 97784
 *
 */
public interface ServiceRelationshipRepository extends JpaRepository<ServiceRelationship, ServiceRelationshipKey> {

	/**
	 * 删除该客户所有服务关系
	 * 
	 * @param cid
	 */
	@Modifying
	@Query(nativeQuery = true, value = "delete from service_relationship where cid=?1")
	void deleteByCid(Integer cid);

	/**
	 * 删除该员工所有服务关系
	 * 
	 * @param eid
	 */
	@Modifying
	@Query(nativeQuery = true, value = "delete from service_relationship where eid=?1")
	void deleteByServiceId(Integer serviceId);

	/**
	 * 根据客户id、员工id组合分页查询
	 * 
	 * @param cid
	 * @param eid
	 * @param pageable
	 * @return
	 */
	@Query(nativeQuery = true, value = "select * from service_relationship where if(IFNULL(?1,'') !='',cid=?1,1=1) and if(IFNULL(?2,'') !='',eid=?2,1=1)")
	Page<ServiceRelationship> findByCidEid(@Param("cid") Integer cid, @Param("eid") Integer eid,
			Pageable pageable);
}