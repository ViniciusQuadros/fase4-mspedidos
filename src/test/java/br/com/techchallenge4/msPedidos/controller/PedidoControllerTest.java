package br.com.techchallenge4.msPedidos.controller;

import br.com.techchallenge4.msPedidos.handler.GlobalExceptionHandler;
import br.com.techchallenge4.msPedidos.model.Pedido;
import br.com.techchallenge4.msPedidos.service.PedidoService;
import br.com.techchallenge4.msPedidos.utils.PedidoHelper;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class PedidoControllerTest {

    private MockMvc mockMvc;

//    @RegisterExtension
//    LogTrackerStub logTracker = LogTrackerStub.create().recordForLevel(LogTracker.LogLevel.INFO)
//            .recordForType(ProdutoController.class);

    @Mock
    private PedidoService pedidoService;

    AutoCloseable openMocks;

    @BeforeEach
    void setUp() {
        openMocks = MockitoAnnotations.openMocks(this);
        PedidoController pedidoController = new PedidoController(pedidoService);
        mockMvc = MockMvcBuilders.standaloneSetup(pedidoController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .addFilter((request, response, chain) -> {
                    response.setCharacterEncoding("UTF-8");
                    chain.doFilter(request, response);
                }, "/*")
                .build();
    }

    @AfterEach
    void tearDown() throws Exception {
        openMocks.close();
    }

    @Test
    void devePermitirCriarUmPedido() throws Exception {
        var id = 1L;
        var pedidoRequest = PedidoHelper.criarPedidoRequest();
        var pedido = PedidoHelper.criarPedido();

        Mockito.when(pedidoService.criarPedido(pedidoRequest)).thenReturn(pedido);

        mockMvc.perform(post("/api/v1/pedidos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(pedidoRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nomeCliente").value(pedido.getNomeCliente()));
        verify(pedidoService, times(1)).criarPedido(pedidoRequest);
    }

    @Test
    void devePermitirBuscarUmPedido() throws Exception {
        var pedido = PedidoHelper.criarPedido();

        //Mockito.when(pedidoService.getPedido(pedidoRequest)).thenReturn(pedido);
        Mockito.when(pedidoService.getPedido(any(Long.class))).thenReturn(pedido);

        mockMvc.perform(get("/api/v1/pedidos/{id}", pedido.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nomeCliente").value(pedido.getNomeCliente()));
        verify(pedidoService, times(1)).getPedido(pedido.getId());
    }

//    @Test
//    void devePermitirBuscarPedidos() throws Exception {
//
//        var pedido = PedidoHelper.criarPedido();
//        List<Pedido> listPedidos = List.of(pedido);
//
//        Page<Pedido> pagePedidos = new PageImpl<>(listPedidos, PageRequest.of(0, 10), listPedidos.size());
//
//        Mockito.when(pedidoService.getPedidos(any())).thenReturn(pagePedidos);
//
//        mockMvc.perform(get("/api/v1/pedidos")
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.content[0].nomeCliente").value(pedido.getNomeCliente()));
//        verify(pedidoService, times(1)).getPedidos(any(Pageable.class));
//    }
//
//    @Test
//    void devePermitirBuscarPedidosDeUmCliente() throws Exception {
//
//        var pedido = PedidoHelper.criarPedido();
//        List<Pedido> listPedidos = List.of(pedido);
//
//        Pageable pageable = PageRequest.of(0, 10);
//        Page<Pedido> pagePedidos = new PageImpl<>(listPedidos, pageable, 1);
//
//        Mockito.when(pedidoService.getPedidos(any(Pageable.class))).thenReturn(pagePedidos);
//
//        mockMvc.perform(get("/api/v1/pedidos/pedidosCliente/{id}", pedido.getClienteId())
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.content[0].nomeCliente").value(pedido.getNomeCliente()));
//        verify(pedidoService, times(1)).getPedidosByClienteId(any(Pageable.class), pedido.getClienteId());
//    }

    public static String asJsonString(final Object obj) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());

        try {
            return mapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}