package com.microservices.accounts.controllers;

import com.microservices.accounts.SecurityConfig;
import com.microservices.accounts.services.AccountService;
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
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(AccountController.class)
@ContextConfiguration(classes = SecurityConfig.class)
public class AccountControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Mock
    private AccountService accountService;
    @InjectMocks
    private AccountController accountController;

    private JSONObject testJsonObject = new JSONObject();

    @Before
    public void setup() throws JSONException {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(this.accountController).build();

        testJsonObject.put("id", 1);
        testJsonObject.put("firstName", "Joe");
        testJsonObject.put("lastName", "Yarbrough");
        testJsonObject.put("emailAddress", "testEmail@email.com");
    }

    @Test
    public void testGetAllAccounts() throws Exception {
        mockMvc.perform(get("/accounts"))
                .andExpect(status().isOk())
                .andDo(print());
    }

//    @Test
//    public void testFindAccountById() throws Exception {
//        mockMvc.perform(get("/accounts/{id}", 1)
//                .contentType(MediaType.APPLICATION_JSON)
//                .accept(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk());
//    }

//    @Test
//    public void testCreateNewAccount() throws Exception {
//        mockMvc.perform(post("/accounts")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(String.valueOf(testJsonObject.toString())))
//                .andExpect(status().isCreated())
//                .andDo(print());
//    }
//
//    @Test
//    public void testUpdatingExistingAccount() throws Exception {
//        mockMvc.perform(put("/accounts/{id}", 1)
//                .contentType(MediaType.APPLICATION_JSON).content(String.valueOf(testJsonObject)))
//                .andExpect(status().isCreated())
//                .andDo(print());
//    }

//    @Test
//    public void testDeletingAnAccount() throws Exception {
//        mockMvc.perform(delete("/accounts/{id}", 1)
//                .contentType(MediaType.APPLICATION_JSON)
//                .accept(MediaType.APPLICATION_JSON))
//                .andExpect(status().isNoContent());
//    }
}