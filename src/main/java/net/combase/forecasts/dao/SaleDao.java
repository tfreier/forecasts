/**
 * Copyright 2013 COMBASE AG
 */
package net.combase.forecasts.dao;

import java.math.BigDecimal;
import java.util.Date;

import net.combase.forecasts.domain.Sale;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * @author Till Freier
 * 
 */
public interface SaleDao extends JpaRepository<Sale, Long>
{
	@Query("select count(*) from Sale s where s.dateTime between :dateFrom and :dateTo and s.temp between :tempFrom and :tempTo")
	long getSalesForTemp(@Param("dateFrom") Date from, @Param("dateTo") Date to,
		@Param("tempFrom") BigDecimal tempFrom, @Param("tempTo") BigDecimal tempTo);

	// Checking number of sales in each temp range
	@Query("select count(*) from Sale s where s.temp >= :tempFrom and s.temp <= :tempTo")
	long getSalesFromTemp15To20(@Param("tempFrom") BigDecimal tempFrom,@Param("tempTo") BigDecimal tempTo);
	
	@Query("select count(*) from Sale s where s.temp > :tempFrom and s.temp <= :tempTo")
	long getSalesFromTemp20To25(@Param("tempFrom") BigDecimal tempFrom,@Param("tempTo") BigDecimal tempTo);
	
	@Query("select count(*) from Sale s where s.temp > :tempFrom and s.temp <= :tempTo")
	long getSalesFromTemp25To30(@Param("tempFrom") BigDecimal tempFrom,@Param("tempTo") BigDecimal tempTo);
	
	@Query("select count(*) from Sale s where s.temp > :temp30")
	long getSalesWhenTempOver30(@Param("temp30") BigDecimal temp30);
	
	@Query("select count(*) from Sale")
	long getTotalSaleNo();
	
	//count number of days in total
	@Query("select count(distinct dateOnly) from Sale ")
	long getNoOfDayInTotal();
	
	
	//*** Checking the effect of ratio: Temperature
	//Count number of day in a temp range
	@Query("select count(distinct s.dateOnly) from Sale s where s.temp > :tempFrom and s.temp <= :tempTo")
	long getNoOfDayForTempRange(@Param("tempFrom") BigDecimal tempFrom,@Param("tempTo") BigDecimal tempTo);
	
	@Query("select count(distinct s.dateOnly) from Sale s where s.temp > :temp30")
	long getNoOfDayWhenTempOver30(@Param("temp30") BigDecimal temp30);
	
	
	//*** Checking the effect of ratio: Day of week
	// Checking the total sales for Mon/Tue/Wed....
	@Query("select count(*) from Sale s where s.dayOfWeek = :day")
	long getSaleEachDay(@Param("day") int day);
	
	//Count number of Mon/Tue/Wed... in the whole database
	@Query("select count(distinct s.dateOnly) from Sale s where s.dayOfWeek = :day")
	long getDayOfWeek(@Param("day") int day);
	
	
	//*** Checking the effect of ratio: Price of the product 
	//Get the average price of 1 product 
	@Query("select AVG(price) from Sale s where s.product.name = :productName")
	long getAvgPrice(@Param("productName") String productName);
	
	//Get total sales number for each product
	@Query("select count(*) from Sale s where s.product.name = :productName")
	long getTotalSaleNoEachProd(@Param("productName") String productName);
	
	//Get the number of price variants of 1 product 
	@Query("select count(distinct price) from Sale s where s.product.name = :productName")
	long getNoPriceVar(@Param("productName") String productName);
	
	
}