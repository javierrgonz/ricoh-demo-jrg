package com.jrg.ricoh.demo.test.integrity.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import java.io.IOException;

import org.apache.commons.io.IOUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.MimeTypeUtils;

import com.jrg.ricoh.demo.RicohDemoArticuloServer;
import com.jrg.ricoh.demo.controller.ArticuloController;
import com.jrg.ricoh.demo.test.integrity.config.H2JpaConfig;
import com.jrg.ricoh.demo.test.integrity.config.WithMockOAuth2Authority;
import com.jrg.ricoh.demo.test.utils.OauthRoles;



@RunWith(SpringRunner.class)
@SpringBootTest(classes = { RicohDemoArticuloServer.class,
	H2JpaConfig.class }, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ArticuloControllerIT {

    @LocalServerPort
    private int port;

    @Autowired
    private ArticuloController articuloController;

    private MockMvc mockMvc;
    private String articulosJson;
    private String articulo1Json;

    @Before
    public void setup() throws IOException {
	mockMvc = MockMvcBuilders.standaloneSetup(articuloController).build();
	articulosJson = IOUtils.toString(this.getClass().getResourceAsStream("/files/articulos.json"), "ISO-8859-1");
	articulo1Json = IOUtils.toString(this.getClass().getResourceAsStream("/files/articulo1.json"), "ISO-8859-1");
    }

    @Test
    @WithMockOAuth2Authority(authority = OauthRoles.ADMIN)
    public void testGetArticuloAdminOKTest() throws Exception {
	final ResultActions result = mockMvc
		.perform(get("/api/articulo/").accept(MimeTypeUtils.APPLICATION_JSON_VALUE));
	String obtained = result.andReturn().getResponse().getContentAsString();
	assertEquals(articulosJson, obtained);
	assertEquals(HttpStatus.OK.value(), result.andReturn().getResponse().getStatus());
    }

    @Test
    @WithMockOAuth2Authority(authority = OauthRoles.USER)
    public void testGetArticuloUserOKTest() throws Exception {
	final ResultActions result = mockMvc
		.perform(get("/api/articulo/").accept(MimeTypeUtils.APPLICATION_JSON_VALUE));
	String obtained = result.andReturn().getResponse().getContentAsString();
	assertEquals(articulosJson, obtained);
	assertEquals(HttpStatus.OK.value(), result.andReturn().getResponse().getStatus());
    }

    @Test
    @WithMockOAuth2Authority(authority = OauthRoles.ADMIN)
    public void testGetArticulo1AdminOKTest() throws Exception {
	final ResultActions result = mockMvc
		.perform(get("/api/articulo/1").accept(MimeTypeUtils.APPLICATION_JSON_VALUE));
	String obtained = result.andReturn().getResponse().getContentAsString();
	assertEquals(articulo1Json, obtained);
	assertEquals(HttpStatus.OK.value(), result.andReturn().getResponse().getStatus());
    }

    @Test
    @WithMockOAuth2Authority(authority = OauthRoles.ADMIN)
    public void testGetArticulo1UserOKTest() throws Exception {
	final ResultActions result = mockMvc
		.perform(get("/api/articulo/1").accept(MimeTypeUtils.APPLICATION_JSON_VALUE));
	String obtained = result.andReturn().getResponse().getContentAsString();
	assertEquals(articulo1Json, obtained);
	assertEquals(HttpStatus.OK.value(), result.andReturn().getResponse().getStatus());
    }

    @Test
    @WithMockOAuth2Authority(authority = OauthRoles.NO_AUTH)
    public void testGetArticulosNoAuthUserKOTest() throws Exception {
	Boolean isAccessDeniedException = Boolean.FALSE;
	try {
	    mockMvc.perform(get("/api/articulo/").accept(MimeTypeUtils.APPLICATION_JSON_VALUE));
	} catch (Exception e) {
	    isAccessDeniedException = (e.getCause().getClass().equals(AccessDeniedException.class));
	}
	assertTrue(isAccessDeniedException);
    }

    @Test
    @WithMockOAuth2Authority(authority = OauthRoles.NO_AUTH)
    public void testGetArticulo1NoAuthUserKOTest() throws Exception {
	Boolean isAccessDeniedException = Boolean.FALSE;
	try {
	    mockMvc.perform(get("/api/articulo/1").accept(MimeTypeUtils.APPLICATION_JSON_VALUE));
	} catch (Exception e) {
	    isAccessDeniedException = (e.getCause().getClass().equals(AccessDeniedException.class));
	}
	assertTrue(isAccessDeniedException);
    }
}