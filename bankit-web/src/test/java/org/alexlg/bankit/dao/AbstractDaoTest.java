/*
 * Copyright (C) 2012 Alexandre Thomazo
 *
 * This file is part of BankIt.
 *
 * BankIt is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * BankIt is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with BankIt. If not, see <http://www.gnu.org/licenses/>.
 */
package org.alexlg.bankit.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.sql.DataSource;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

/**
 * Class use for DAO Testing.
 * Start Spring Context, get the entity manager
 * and load some sample data into the database.
 * 
 * @author Alexandre Thomazo
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"/appDb.xml"})
@Transactional
public abstract class AbstractDaoTest extends AbstractTransactionalJUnit4SpringContextTests {

	@Autowired
	DataSource dataSrc;
	
	@PersistenceContext
	private EntityManager em;
	
	private static boolean databaseLoaded = false;
	
	@Before
	public void beforeTest() {
		setDataSource(dataSrc);
		
		if (!databaseLoaded) {
			super.executeSqlScript("classpath:test-data.sql", false);
			databaseLoaded = true;
		}
	}
	
	// Utility method
	protected void flush() {
		em.flush();
	}

	public DataSource getDataSrc() {
		return dataSrc;
	}

	public void setDataSrc(DataSource dataSrc) {
		this.dataSrc = dataSrc;
	}

}
