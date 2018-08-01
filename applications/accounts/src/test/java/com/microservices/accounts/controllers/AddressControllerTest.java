package com.microservices.accounts.controllers;

import com.microservices.accounts.SecurityConfig;
import com.microservices.accounts.services.AddressService;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(AddressController.class)
@ContextConfiguration(classes = SecurityConfig.class)
public class AddressControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private AddressService addressService;

    @InjectMocks
    private AddressController addressController;

    private JSONObject testJsonObject = new JSONObject();

    @Before
    public void setup() throws JSONException {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(addressController).build();

        testJsonObject.put("id", 1);
        testJsonObject.put("addressOne", "811 e stone ct");
        testJsonObject.put("addressTwo", "---");
        testJsonObject.put("city", "Addison");
        testJsonObject.put("state", "IL");
        testJsonObject.put("zipCode", "60101");
        testJsonObject.put("country", "USA");
    }

    @Test
    public void testGetAllAddresses() throws Exception {
        mockMvc.perform(get("/addresses")).andExpect(status().isOk());
    }

    @Test
    public void testGetAllAddressesForGivenId() throws Exception {
        Long accountId = 1L;
        mockMvc.perform(get("/accounts/{id}/address", accountId))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    public void testGetSingleAddressWithAddressId() throws Exception {
        Long addressId = 2L;
        Long accountId = 1L;

        mockMvc.perform(get("/accounts/{id}/address/{addressId}", accountId, addressId))
                .andExpect(status().isOk());
    }

    @Test
    public void testCreateNewAddress() throws Exception {
        Long accountId = 1L;

        mockMvc.perform(post("/accounts/{id}/address", accountId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(String.valueOf(testJsonObject)))
                .andExpect(status().isCreated()).andDo(print());
    }

    @Test
    public void testUpdateAddress() throws Exception {
        Long accountId = 1L;
        Long addressId = 2L;

        mockMvc.perform(put("/accounts/{id}/address/{addressId}", accountId, addressId)
                .contentType(MediaType.APPLICATION_JSON).content(String.valueOf(testJsonObject)))
                .andExpect(status().isCreated());
    }

    @Test
    public void testDeletingAddress() throws Exception {
        Long accountId = 1L;
        Long addressId = 2L;

        mockMvc.perform(delete("/accounts/{id}/address/{addressId}", accountId, addressId)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }
}