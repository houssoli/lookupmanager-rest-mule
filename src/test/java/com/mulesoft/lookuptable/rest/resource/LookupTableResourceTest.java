package com.mulesoft.lookuptable.rest.resource;

import java.lang.reflect.Field;

import junit.framework.Assert;

import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;

import com.google.gson.Gson;
import com.mulesfot.lookuptable.persistence.dao.LookUpTableDao;
import com.mulesoft.lookuptable.rest.exceptions.CustomWebApplicationException;
import com.mulesoft.lookuptable.rest.response.LookupManagerResponse;

/**
 * Validates that the responses from {@link LookupTableResources} are correct by
 * mocking the responses the {@link LookUpTableDao} provides. In this way we
 * validate that in the event of an error we get a 500 http status code and if
 * everithing is OK we get a 200 http status code.
 * 
 * @author damiansima
 * 
 */
public class LookupTableResourceTest {
	private static final String FAKE_CUSTOMER = "fakecustomer";
	private static final String FAKE_TABLE_NAME = "faketable";
	private static final String SIMPLE_KEY = "fakesimplekey";
	private static final String COMPOSITE_KEY = "fakecompositekey0|key1|key2";

	private LookUpTableDao mockDao;

	@Before
	public void setUp() throws IllegalArgumentException, IllegalAccessException, SecurityException, NoSuchFieldException {
		this.mockDao = EasyMock.createMock(LookUpTableDao.class);

		Field servicesField = LookUpTableDao.class.getDeclaredField("INSTANCE");
		servicesField.setAccessible(true);
		servicesField.set(LookUpTableDao.getInstance(), mockDao);

	}

	@Test
	public void createDataOkResponseTest() {
		LookupTableResource resource = new LookupTableResource();

		EasyMock.expect(mockDao.createLookupTableRecords(FAKE_CUSTOMER, FAKE_TABLE_NAME, SIMPLE_KEY, "")).andReturn(true);

		EasyMock.replay(mockDao);

		String response = resource.createData(FAKE_CUSTOMER, FAKE_TABLE_NAME, SIMPLE_KEY, "");

		LookupManagerResponse actualResponse = new Gson().fromJson(response, LookupManagerResponse.class);
		Assert.assertEquals(200, actualResponse.getHttpStatus());

		EasyMock.verify(mockDao);
	}

	@Test(expected=CustomWebApplicationException.class)
	public void createDataErrorResponseTest() {
		LookupTableResource resource = new LookupTableResource();

		EasyMock.expect(mockDao.createLookupTableRecords(FAKE_CUSTOMER, FAKE_TABLE_NAME, SIMPLE_KEY, "")).andReturn(false);

		EasyMock.replay(mockDao);

		String response = resource.createData(FAKE_CUSTOMER, FAKE_TABLE_NAME, SIMPLE_KEY, "");

		LookupManagerResponse actualResponse = new Gson().fromJson(response, LookupManagerResponse.class);
		Assert.assertEquals(500, actualResponse.getHttpStatus());

		EasyMock.verify(mockDao);
	}

	@Test
	public void listDataNoKeyOkResponseTest() {
		LookupTableResource resource = new LookupTableResource();

		EasyMock.expect(mockDao.getLookupTableRecords(FAKE_CUSTOMER, FAKE_TABLE_NAME)).andReturn("");

		EasyMock.replay(mockDao);

		String response = resource.listData(FAKE_CUSTOMER, FAKE_TABLE_NAME, null);

		LookupManagerResponse actualResponse = new Gson().fromJson(response, LookupManagerResponse.class);
		Assert.assertEquals(200, actualResponse.getHttpStatus());

		EasyMock.verify(mockDao);
	}

	@Test
	public void listDataWithKeyOkResponseTest() {
		LookupTableResource resource = new LookupTableResource();

		EasyMock.expect(mockDao.getLookupTableRecords(FAKE_CUSTOMER, FAKE_TABLE_NAME, SIMPLE_KEY)).andReturn("");

		EasyMock.replay(mockDao);

		String response = resource.listData(FAKE_CUSTOMER, FAKE_TABLE_NAME, SIMPLE_KEY);

		LookupManagerResponse actualResponse = new Gson().fromJson(response, LookupManagerResponse.class);
		Assert.assertEquals(200, actualResponse.getHttpStatus());

		EasyMock.verify(mockDao);
	}

	@Test
	public void updateDataOkResponseTest() {
		LookupTableResource resource = new LookupTableResource();

		EasyMock.expect(mockDao.updateLookupTableRecords(FAKE_CUSTOMER, FAKE_TABLE_NAME, SIMPLE_KEY, "")).andReturn(true);

		EasyMock.replay(mockDao);

		String response = resource.updateData(FAKE_CUSTOMER, FAKE_TABLE_NAME, SIMPLE_KEY, "");

		LookupManagerResponse actualResponse = new Gson().fromJson(response, LookupManagerResponse.class);
		Assert.assertEquals(200, actualResponse.getHttpStatus());

		EasyMock.verify(mockDao);
	}

	@Test(expected=CustomWebApplicationException.class)
	public void updateDataErrorResponseTest() {
		LookupTableResource resource = new LookupTableResource();

		EasyMock.expect(mockDao.updateLookupTableRecords(FAKE_CUSTOMER, FAKE_TABLE_NAME, SIMPLE_KEY, "")).andReturn(false);

		EasyMock.replay(mockDao);

		resource.updateData(FAKE_CUSTOMER, FAKE_TABLE_NAME, SIMPLE_KEY, "");

		EasyMock.verify(mockDao);
	}

	@Test
	public void deleteDataNoKeyOkResponseTest() {
		LookupTableResource resource = new LookupTableResource();

		EasyMock.expect(mockDao.deleteLookupTableRecords(FAKE_CUSTOMER, FAKE_TABLE_NAME)).andReturn(true);

		EasyMock.replay(mockDao);

		String response = resource.deleteData(FAKE_CUSTOMER, FAKE_TABLE_NAME, null);

		LookupManagerResponse actualResponse = new Gson().fromJson(response, LookupManagerResponse.class);
		Assert.assertEquals(200, actualResponse.getHttpStatus());

		EasyMock.verify(mockDao);
	}

	@Test
	public void deleteDataWhitKeyOkResponseTest() {
		LookupTableResource resource = new LookupTableResource();

		EasyMock.expect(mockDao.deleteLookupTableRecords(FAKE_CUSTOMER, FAKE_TABLE_NAME, SIMPLE_KEY)).andReturn(true);

		EasyMock.replay(mockDao);

		String response = resource.deleteData(FAKE_CUSTOMER, FAKE_TABLE_NAME, SIMPLE_KEY);

		LookupManagerResponse actualResponse = new Gson().fromJson(response, LookupManagerResponse.class);
		Assert.assertEquals(200, actualResponse.getHttpStatus());

		EasyMock.verify(mockDao);
	}

	@Test(expected=CustomWebApplicationException.class)
	public void deleteDataNoKeyErrorResponseTest() {
		LookupTableResource resource = new LookupTableResource();

		EasyMock.expect(mockDao.deleteLookupTableRecords(FAKE_CUSTOMER, FAKE_TABLE_NAME)).andReturn(false);

		EasyMock.replay(mockDao);

		resource.deleteData(FAKE_CUSTOMER, FAKE_TABLE_NAME, null);

		EasyMock.verify(mockDao);
	}

	@Test(expected=CustomWebApplicationException.class)
	public void deleteDataWhitKeyErrorResponseTest() {
		LookupTableResource resource = new LookupTableResource();

		EasyMock.expect(mockDao.deleteLookupTableRecords(FAKE_CUSTOMER, FAKE_TABLE_NAME, SIMPLE_KEY)).andReturn(false);

		EasyMock.replay(mockDao);

		resource.deleteData(FAKE_CUSTOMER, FAKE_TABLE_NAME, SIMPLE_KEY);

		EasyMock.verify(mockDao);
	}
}