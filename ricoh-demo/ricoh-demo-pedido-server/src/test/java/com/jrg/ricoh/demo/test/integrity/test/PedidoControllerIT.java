package com.jrg.ricoh.demo.test.integrity.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.MimeTypeUtils;

import com.jrg.ricoh.demo.RicohDemoPedidoServer;
import com.jrg.ricoh.demo.controller.PedidoController;
import com.jrg.ricoh.demo.dto.PedidoDto;
import com.jrg.ricoh.demo.entity.Articulo;
import com.jrg.ricoh.demo.entity.Pedido;
import com.jrg.ricoh.demo.repository.PedidoCrudRepository;
import com.jrg.ricoh.demo.test.integrity.config.H2JpaConfig;
import com.jrg.ricoh.demo.test.integrity.config.WithMockOAuth2Authority;
import com.jrg.ricoh.demo.test.utils.IntegrationTestUtils;
import com.jrg.ricoh.demo.test.utils.OauthRoles;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { RicohDemoPedidoServer.class,
	H2JpaConfig.class }, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = {
		"spring.h2.console.enabled=true" })
public class PedidoControllerIT {

    @LocalServerPort
    private int port;

    @Autowired
    private PedidoController pedidoController;

    @Autowired
    private PedidoCrudRepository pedidoCrudRepository;

    private MockMvc mockMvc;
    private String pedidosJson;
    private String pedido1Json;
    private List<Integer> idsPedidosCrearOK = new ArrayList<>();
    private List<Integer> idsPedidosEditarOK = new ArrayList<>();

    @Before
    public void setup() throws IOException {
	mockMvc = MockMvcBuilders.standaloneSetup(pedidoController).build();
	pedidosJson = IOUtils.toString(this.getClass().getResourceAsStream("/files/pedidos.json"), "ISO-8859-1");
	pedido1Json = IOUtils.toString(this.getClass().getResourceAsStream("/files/pedido1.json"), "ISO-8859-1");

	idsPedidosCrearOK.add(1);
	idsPedidosCrearOK.add(6);
	idsPedidosEditarOK.add(5);
	idsPedidosEditarOK.add(9);
	idsPedidosEditarOK.add(10);
    }

    @Test
    @WithMockOAuth2Authority(authority = OauthRoles.ADMIN)
    public void testGetPedidosAdminOKTest() throws Exception {
	final ResultActions result = mockMvc.perform(get("/api/pedido/").accept(MimeTypeUtils.APPLICATION_JSON_VALUE));
	String obtained = result.andReturn().getResponse().getContentAsString();
	assertEquals(pedidosJson, obtained);
	assertEquals(HttpStatus.OK.value(), result.andReturn().getResponse().getStatus());
    }

    @Test
    @WithMockOAuth2Authority(authority = OauthRoles.USER)
    public void testGetPedidosUserOKTest() throws Exception {
	final ResultActions result = mockMvc.perform(get("/api/pedido/").accept(MimeTypeUtils.APPLICATION_JSON_VALUE));
	String obtained = result.andReturn().getResponse().getContentAsString();
	assertEquals(pedidosJson, obtained);
	assertEquals(HttpStatus.OK.value(), result.andReturn().getResponse().getStatus());
    }

    @Test
    @WithMockOAuth2Authority(authority = OauthRoles.NO_AUTH)
    public void testGetPedidosNoAuthUserKOTest() throws Exception {
	Boolean isAccessDeniedException = Boolean.FALSE;
	try {
	    mockMvc.perform(get("/api/pedido/").accept(MimeTypeUtils.APPLICATION_JSON_VALUE));
	} catch (Exception e) {
	    isAccessDeniedException = (e.getCause().getClass().equals(AccessDeniedException.class));
	}
	assertTrue(isAccessDeniedException);
    }

    @Test
    @WithMockOAuth2Authority(authority = OauthRoles.ADMIN)
    public void testGetPedido1AdminOKTest() throws Exception {
	final ResultActions result = mockMvc.perform(get("/api/pedido/1").accept(MimeTypeUtils.APPLICATION_JSON_VALUE));
	String obtained = result.andReturn().getResponse().getContentAsString();
	assertEquals(pedido1Json, obtained);
	assertEquals(HttpStatus.OK.value(), result.andReturn().getResponse().getStatus());
    }

    @Test
    @WithMockOAuth2Authority(authority = OauthRoles.USER)
    public void testGetPedido1UserOKTest() throws Exception {
	final ResultActions result = mockMvc.perform(get("/api/pedido/1").accept(MimeTypeUtils.APPLICATION_JSON_VALUE));
	String obtained = result.andReturn().getResponse().getContentAsString();
	assertEquals(pedido1Json, obtained);
	assertEquals(HttpStatus.OK.value(), result.andReturn().getResponse().getStatus());
    }

    @Test
    @WithMockOAuth2Authority(authority = OauthRoles.NO_AUTH)
    public void testGetPedido1NoAuthUserKOTest() throws Exception {
	Boolean isAccessDeniedException = Boolean.FALSE;
	try {
	    mockMvc.perform(get("/api/pedido/1").accept(MimeTypeUtils.APPLICATION_JSON_VALUE));
	} catch (Exception e) {
	    isAccessDeniedException = (e.getCause().getClass().equals(AccessDeniedException.class));
	}
	assertTrue(isAccessDeniedException);
    }

    @Test
    @WithMockOAuth2Authority(authority = OauthRoles.ADMIN)
    public void testGetNoFoundPedidoAdminKOTest() throws Exception {
	final ResultActions result = mockMvc
		.perform(get("/api/pedido/100").accept(MimeTypeUtils.APPLICATION_JSON_VALUE));
	assertEquals(HttpStatus.NOT_FOUND.value(), result.andReturn().getResponse().getStatus());
    }

    @Test
    @WithMockOAuth2Authority(authority = OauthRoles.USER)
    public void testGetNoFoundPedidoUserKOTest() throws Exception {
	final ResultActions result = mockMvc
		.perform(get("/api/pedido/100").accept(MimeTypeUtils.APPLICATION_JSON_VALUE));
	assertEquals(HttpStatus.NOT_FOUND.value(), result.andReturn().getResponse().getStatus());
    }

    @Test
    @WithMockOAuth2Authority(authority = OauthRoles.NO_AUTH)
    public void testGetNoFoundPedidoNoAuthUserKOTest() throws Exception {
	Boolean isAccessDeniedException = Boolean.FALSE;
	try {
	    mockMvc.perform(get("/api/pedido/100").accept(MimeTypeUtils.APPLICATION_JSON_VALUE));
	} catch (Exception e) {
	    isAccessDeniedException = (e.getCause().getClass().equals(AccessDeniedException.class));
	}
	assertTrue(isAccessDeniedException);
    }

    @Test
    @DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
    @WithMockOAuth2Authority(authority = OauthRoles.ADMIN)
    public void testCreatePedidoAdminOKTest() throws Exception {
	String creationCliente = "testCreatePedidoAdminOKTest";
	Pedido pedidoCreado = new Pedido();
	List<Integer> idsArticulosPedidoCreado = new ArrayList<>();
	List<Pedido> pedidos = new ArrayList<>();

	PedidoDto pedidoDto = new PedidoDto();
	pedidoDto.setCliente(creationCliente);
	pedidoDto.setArticulos(idsPedidosCrearOK);

	final ResultActions result = mockMvc
		.perform(post("/api/pedido/").contentType(IntegrationTestUtils.APPLICATION_JSON_UTF8)
			.content(IntegrationTestUtils.convertObjectToJsonBytes(pedidoDto))
			.accept(MimeTypeUtils.APPLICATION_JSON_VALUE));

	pedidos = this.pedidoCrudRepository.findAll();
	for (Pedido pedido : pedidos) {
	    if (pedido.getCliente().equals(creationCliente)) {
		pedidoCreado = pedido;
	    }
	}
	for (Articulo articulo : pedidoCreado.getArticulos()) {
	    idsArticulosPedidoCreado.add(articulo.getId());
	}

	assertEquals(HttpStatus.CREATED.value(), result.andReturn().getResponse().getStatus());
	assertEquals(creationCliente, pedidoCreado.getCliente());
	assertTrue(idsPedidosCrearOK.containsAll(idsArticulosPedidoCreado));
    }

    @Test
    @WithMockOAuth2Authority(authority = OauthRoles.ADMIN)
    public void testCreatePedidoAdminNoParamsKOTest() throws Exception {
	Boolean isInvalidDataAccessApiUsageException = Boolean.FALSE;
	try {
	    mockMvc.perform(post("/api/pedido/").contentType(IntegrationTestUtils.APPLICATION_JSON_UTF8)
		    .content(IntegrationTestUtils.convertObjectToJsonBytes(new PedidoDto()))
		    .accept(MimeTypeUtils.APPLICATION_JSON_VALUE));
	} catch (Exception e) {
	    isInvalidDataAccessApiUsageException = (e.getCause().getClass()
		    .equals(InvalidDataAccessApiUsageException.class));
	}
	assertTrue(isInvalidDataAccessApiUsageException);
    }

    @Test
    @WithMockOAuth2Authority(authority = OauthRoles.USER)
    public void testCreatePedidoUseKOTest() throws Exception {
	Boolean isAccessDeniedException = Boolean.FALSE;
	try {
	    mockMvc.perform(post("/api/pedido/").contentType(IntegrationTestUtils.APPLICATION_JSON_UTF8)
		    .content(IntegrationTestUtils.convertObjectToJsonBytes(new PedidoDto()))
		    .accept(MimeTypeUtils.APPLICATION_JSON_VALUE));
	} catch (Exception e) {
	    isAccessDeniedException = (e.getCause().getClass().equals(AccessDeniedException.class));
	}
	assertTrue(isAccessDeniedException);
    }

    @Test
    @WithMockOAuth2Authority(authority = OauthRoles.NO_AUTH)
    public void testCreatePedidoNoAuthKOTest() throws Exception {
	Boolean isAccessDeniedException = Boolean.FALSE;
	try {
	    mockMvc.perform(post("/api/pedido/").contentType(IntegrationTestUtils.APPLICATION_JSON_UTF8)
		    .content(IntegrationTestUtils.convertObjectToJsonBytes(new PedidoDto()))
		    .accept(MimeTypeUtils.APPLICATION_JSON_VALUE));
	} catch (Exception e) {
	    isAccessDeniedException = (e.getCause().getClass().equals(AccessDeniedException.class));
	}
	assertTrue(isAccessDeniedException);
    }

    @Test
    @DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
    @WithMockOAuth2Authority(authority = OauthRoles.ADMIN)
    public void testUpdatePedidoAdminOKTest() throws Exception {
	String updateCliente = "testUpdatePedidoAdminOKTest";
	Pedido pedidoEditado = new Pedido();
	List<Integer> idsArticulosPedidoEditado = new ArrayList<>();

	Pedido pedidoToUpdate = this.pedidoCrudRepository.findById(1).get();
	PedidoDto pedidoDtoToUpdate = fromPedido(pedidoToUpdate);
	pedidoDtoToUpdate.setCliente(updateCliente);
	pedidoDtoToUpdate.setArticulos(idsPedidosEditarOK);

	final ResultActions result = mockMvc
		.perform(put("/api/pedido/").contentType(IntegrationTestUtils.APPLICATION_JSON_UTF8)
			.content(IntegrationTestUtils.convertObjectToJsonBytes(pedidoDtoToUpdate))
			.accept(MimeTypeUtils.APPLICATION_JSON_VALUE));

	pedidoEditado = this.pedidoCrudRepository.findById(1).get();
	for (Articulo articulo : pedidoEditado.getArticulos()) {
	    idsArticulosPedidoEditado.add(articulo.getId());
	}

	assertEquals(HttpStatus.NO_CONTENT.value(), result.andReturn().getResponse().getStatus());
	assertEquals(pedidoEditado.getId(), pedidoToUpdate.getId());
	assertFalse(pedidoEditado.getCliente().contentEquals(pedidoToUpdate.getCliente()));
	assertTrue(pedidoEditado.getCliente().equals(updateCliente));
	assertTrue(idsPedidosEditarOK.containsAll(idsArticulosPedidoEditado));
    }

    @Test
    @WithMockOAuth2Authority(authority = OauthRoles.ADMIN)
    public void testUpdatePedidoNotFoundAdminKOTest() throws Exception {
	PedidoDto pedidoDtoToUpdate = new PedidoDto();
	pedidoDtoToUpdate.setId(100);
	pedidoDtoToUpdate.setCliente("updateCliente");
	pedidoDtoToUpdate.setArticulos(idsPedidosEditarOK);

	final ResultActions result = mockMvc
		.perform(put("/api/pedido/").contentType(IntegrationTestUtils.APPLICATION_JSON_UTF8)
			.content(IntegrationTestUtils.convertObjectToJsonBytes(pedidoDtoToUpdate))
			.accept(MimeTypeUtils.APPLICATION_JSON_VALUE));

	assertEquals(HttpStatus.NOT_FOUND.value(), result.andReturn().getResponse().getStatus());

    }

    @Test
    @WithMockOAuth2Authority(authority = OauthRoles.ADMIN)
    public void testUpdatePedidoNoParamsAdminKOTest() throws Exception {
	final ResultActions result = mockMvc
		.perform(put("/api/pedido/").contentType(IntegrationTestUtils.APPLICATION_JSON_UTF8)
			.content(IntegrationTestUtils.convertObjectToJsonBytes(new PedidoDto()))
			.accept(MimeTypeUtils.APPLICATION_JSON_VALUE));
	assertEquals(HttpStatus.NOT_FOUND.value(), result.andReturn().getResponse().getStatus());
    }

    @Test
    @WithMockOAuth2Authority(authority = OauthRoles.USER)
    public void testUpdatePedidoUserKOTest() throws Exception {
	Boolean isAccessDeniedException = Boolean.FALSE;
	try {
	    PedidoDto pedidoDtoToUpdate = new PedidoDto();
	    pedidoDtoToUpdate.setId(1);
	    pedidoDtoToUpdate.setCliente("updateCliente");
	    pedidoDtoToUpdate.setArticulos(idsPedidosEditarOK);

	    mockMvc.perform(put("/api/pedido/").contentType(IntegrationTestUtils.APPLICATION_JSON_UTF8)
		    .content(IntegrationTestUtils.convertObjectToJsonBytes(pedidoDtoToUpdate))
		    .accept(MimeTypeUtils.APPLICATION_JSON_VALUE));
	} catch (Exception e) {
	    isAccessDeniedException = (e.getCause().getClass().equals(AccessDeniedException.class));
	}
	assertTrue(isAccessDeniedException);
    }

    @Test
    @WithMockOAuth2Authority(authority = OauthRoles.NO_AUTH)
    public void testUpdatePedidoNoAuthKOTest() throws Exception {
	Boolean isAccessDeniedException = Boolean.FALSE;
	try {
	    PedidoDto pedidoDtoToUpdate = new PedidoDto();
	    pedidoDtoToUpdate.setId(1);
	    pedidoDtoToUpdate.setCliente("updateCliente");
	    pedidoDtoToUpdate.setArticulos(idsPedidosEditarOK);

	    mockMvc.perform(put("/api/pedido/").contentType(IntegrationTestUtils.APPLICATION_JSON_UTF8)
		    .content(IntegrationTestUtils.convertObjectToJsonBytes(pedidoDtoToUpdate))
		    .accept(MimeTypeUtils.APPLICATION_JSON_VALUE));
	} catch (Exception e) {
	    isAccessDeniedException = (e.getCause().getClass().equals(AccessDeniedException.class));
	}
	assertTrue(isAccessDeniedException);
    }

    @Test
    @DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
    @WithMockOAuth2Authority(authority = OauthRoles.ADMIN)
    public void testDeletePedidoAdminOKTest() throws Exception {
	final ResultActions result = mockMvc.perform(delete("/api/pedido/1")
		.contentType(IntegrationTestUtils.APPLICATION_JSON_UTF8).accept(MimeTypeUtils.APPLICATION_JSON_VALUE));

	assertEquals(HttpStatus.ACCEPTED.value(), result.andReturn().getResponse().getStatus());
	assertFalse(this.pedidoCrudRepository.findById(1).isPresent());
    }

    @Test
    @WithMockOAuth2Authority(authority = OauthRoles.ADMIN)
    public void testDeletePedidoNotFoundAdminKOTest() throws Exception {
	final ResultActions result = mockMvc.perform(delete("/api/pedido/100")
		.contentType(IntegrationTestUtils.APPLICATION_JSON_UTF8).accept(MimeTypeUtils.APPLICATION_JSON_VALUE));

	assertEquals(HttpStatus.BAD_REQUEST.value(), result.andReturn().getResponse().getStatus());
	assertFalse(this.pedidoCrudRepository.findById(100).isPresent());
    }

    @Test
    @WithMockOAuth2Authority(authority = OauthRoles.ADMIN)
    public void testDeletePedidoNoParamsAdminKOTest() throws Exception {
	final ResultActions result = mockMvc.perform(delete("/api/pedido/")
		.contentType(IntegrationTestUtils.APPLICATION_JSON_UTF8).accept(MimeTypeUtils.APPLICATION_JSON_VALUE));

	assertEquals(HttpStatus.METHOD_NOT_ALLOWED.value(), result.andReturn().getResponse().getStatus());
    }

    @Test
    @WithMockOAuth2Authority(authority = OauthRoles.USER)
    public void testDeletePedidoUserKOTest() throws Exception {
	Boolean isAccessDeniedException = Boolean.FALSE;
	try {
	    mockMvc.perform(delete("/api/pedido/1").contentType(IntegrationTestUtils.APPLICATION_JSON_UTF8)
		    .accept(MimeTypeUtils.APPLICATION_JSON_VALUE));
	} catch (Exception e) {
	    isAccessDeniedException = (e.getCause().getClass().equals(AccessDeniedException.class));
	}
	assertTrue(isAccessDeniedException);
    }

    @Test
    @WithMockOAuth2Authority(authority = OauthRoles.NO_AUTH)
    public void testDeletePedidoNoAuthKOTest() throws Exception {
	Boolean isAccessDeniedException = Boolean.FALSE;
	try {
	    mockMvc.perform(delete("/api/pedido/1").contentType(IntegrationTestUtils.APPLICATION_JSON_UTF8)
		    .accept(MimeTypeUtils.APPLICATION_JSON_VALUE));
	} catch (Exception e) {
	    isAccessDeniedException = (e.getCause().getClass().equals(AccessDeniedException.class));
	}
	assertTrue(isAccessDeniedException);
    }

    private PedidoDto fromPedido(Pedido pedido) {
	PedidoDto updatePedidoDto = new PedidoDto();
	updatePedidoDto.setId(pedido.getId());
	updatePedidoDto.setCliente(pedido.getCliente());
	List<Integer> articulos = new ArrayList<>();
	for (Articulo articulo : pedido.getArticulos()) {
	    articulos.add(articulo.getId());
	}
	updatePedidoDto.setArticulos(articulos);
	return updatePedidoDto;
    }

}
