package com.maan.common.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.maan.common.bean.CountryMaster;
import com.maan.common.bean.CountryMasterId;

@Repository
public interface CountryMasterRepository extends JpaRepository<CountryMaster, CountryMasterId>{

	List<CountryMaster> findByCountrypkCountryid(long parseLong);

	List<CountryMaster> findByNationalitynameIsNotNull();


	List<CountryMaster> findByCountrypkCountryidAndEffectivedateLessThanEqualOrderByEffectivedateDesc(long parseLong,
			Date date);

	List<CountryMaster> findByNationalitynameIsNotNullAndEffectivedateLessThanEqualOrderByEffectivedateDesc(Date date);

	
}
