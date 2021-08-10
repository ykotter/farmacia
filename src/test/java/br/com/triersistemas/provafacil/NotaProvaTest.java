package br.com.triersistemas.provafacil;

import br.com.triersistemas.provafacil.model.*;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class NotaProvaTest {

    private final static List<String> notas = new ArrayList<>();
    private static BigDecimal nota = BigDecimal.ZERO;

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate rest;

    @BeforeAll
    public static void antesTest() {
        nota = BigDecimal.ZERO;
    }

    @AfterAll
    public static void DepoisTest() {
        notas.add("Nota.: " + nota);

        System.out.println("");
        System.out.println("");
        System.out.println("+-----------------------------------");
        for (String s : notas) {
            System.out.println("| " + s);
        }
        System.out.println("+-----------------------------------");
        System.out.println("");
        System.out.println("");
    }

    @Test
    @Order(0)
    public void questaoUmTest() {

        var nq = BigDecimal.ZERO;

        try {
            final var p1 = new ProdutoModel("P1", BigDecimal.ONE, EnumProdutoModel.MEDICAMENTO);
            final var p2 = new ProdutoModel("P2", BigDecimal.TEN, EnumProdutoModel.NAO_MEDICAMENTO);

            assertEquals(1L, p1.getId());
            assertEquals("P1", p1.getNome());
            assertEquals(BigDecimal.ONE, p1.getValor());
            assertEquals(EnumProdutoModel.MEDICAMENTO, p1.getTipo());

            assertEquals(2L, p2.getId());
            assertEquals("P2", p2.getNome());
            assertEquals(BigDecimal.TEN, p2.getValor());
            assertEquals(EnumProdutoModel.NAO_MEDICAMENTO, p2.getTipo());

            nq = nq.add(BigDecimal.valueOf(0.5));

        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }

        try {

            final PessoaModel cliente = new ClienteModel("c", "0", "1");
            final PessoaModel farma = new FarmaceuticoModel("f", "2", "3");

            assertEquals(1L, cliente.getId());
            assertEquals("c", cliente.getNome());
            assertEquals("0", cliente.getDocumento());
            assertEquals("1", ((ClienteModel) cliente).getSintoma());

            assertEquals(2L, farma.getId());
            assertEquals("f", farma.getNome());
            assertEquals("2", farma.getDocumento());
            assertEquals("3", ((FarmaceuticoModel) farma).getPis());
            nq = nq.add(BigDecimal.valueOf(1));
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }

        try {

            final var p1 = new ProdutoModel("P1", BigDecimal.ONE, EnumProdutoModel.MEDICAMENTO);
            final var p2 = new ProdutoModel("P2", BigDecimal.TEN, EnumProdutoModel.NAO_MEDICAMENTO);

            final var pedido = new PedidoModel();
            pedido.adicionaProduto(p1, 5);
            pedido.adicionaProduto(p2, 3);

            assertEquals(2, pedido.getItens().size());
            assertEquals(BigDecimal.valueOf(5), pedido.getItens().get(0).getValorTotal());
            assertEquals(BigDecimal.valueOf(30), pedido.getItens().get(1).getValorTotal());
            assertEquals(BigDecimal.valueOf(35), pedido.getValorTotal());
            assertNotNull(pedido.getDataPedido());
            assertTrue(pedido.getDataPedido() instanceof LocalDateTime);
            assertNull(pedido.getDataPagamento());
            assertEquals(EnumPedidoModel.AGUARDANDO_PAGAMENTO, pedido.getStatus());

            pedido.pagar();
            assertNotNull(pedido.getDataPagamento());
            assertTrue(pedido.getDataPagamento() instanceof LocalDateTime);
            assertEquals(EnumPedidoModel.PAGO, pedido.getStatus());

            nq = nq.add(BigDecimal.valueOf(0.5));
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }

        nota = nota.add(nq);
        notas.add("Q1...: " + nq);
    }

    @Test
    @Order(1)
    public void questaoDoisTest() {

        var nq = BigDecimal.ZERO;
        long id = 0;

        try {
            final String param = "?nome=carlos&documento=00000000191&pis=123";
            final FarmaceuticoModel f = this.rest.getForObject(getURL("/pessoa/cadastrar-farmaceutico" + param), FarmaceuticoModel.class);
            assertEquals("00000000191", f.getDocumento());
            assertEquals("123", f.getPis());
            assertEquals("carlos", f.getNome());
            assertNotNull(f.getId());
            id = f.getId();
            nq = nq.add(BigDecimal.valueOf(0.5));
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }

        try {
            final String param = "?nome=Jota&documento=00000000604&sintoma=dor";
            final ClienteModel c = this.rest.getForObject(getURL("/pessoa/cadastrar-cliente" + param), ClienteModel.class);
            assertEquals("00000000604", c.getDocumento());
            assertEquals("dor", c.getSintoma());
            assertEquals("Jota", c.getNome());
            assertNotNull(c.getId());
            nq = nq.add(BigDecimal.valueOf(0.5));
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }

        try {
            final String param = "?id=" + id;
            final FarmaceuticoModel f = this.rest.getForObject(getURL("/pessoa/excluir" + param), FarmaceuticoModel.class);
            assertEquals("00000000191", f.getDocumento());
            assertEquals("123", f.getPis());
            assertEquals("carlos", f.getNome());
            assertEquals(id, f.getId());
            nq = nq.add(BigDecimal.valueOf(0.5));
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }

        try {
            final ClienteModel[] pessoas = this.rest.getForObject(getURL("/pessoa/listar"), ClienteModel[].class);
            assertEquals(1, pessoas.length);
            assertEquals("00000000604", pessoas[0].getDocumento());
            assertEquals("dor", pessoas[0].getSintoma());
            assertEquals("Jota", pessoas[0].getNome());
            nq = nq.add(BigDecimal.valueOf(0.5));
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }

        nota = nota.add(nq);
        notas.add("Q2...: " + nq);
    }

    @Test
    @Order(2)
    public void questaoTresTest() {

        var nq = BigDecimal.ZERO;
        long id = 0;

        try {
            String param = "?nome=Aspirina&valor=99.95&tipo=MEDICAMENTO";
            ProdutoModel p = this.rest.getForObject(getURL("/produto/cadastrar" + param), ProdutoModel.class);
            assertNotNull(p.getId());
            assertEquals("Aspirina", p.getNome());
            assertEquals(BigDecimal.valueOf(99.95), p.getValor());
            assertEquals(EnumProdutoModel.MEDICAMENTO, p.getTipo());
            id = p.getId();

            param = "?nome=Leite&valor=1.25&tipo=NAO_MEDICAMENTO";
            p = this.rest.getForObject(getURL("/produto/cadastrar" + param), ProdutoModel.class);
            assertNotNull(p.getId());
            assertEquals("Leite", p.getNome());
            assertEquals(BigDecimal.valueOf(1.25), p.getValor());
            assertEquals(EnumProdutoModel.NAO_MEDICAMENTO, p.getTipo());

            nq = nq.add(BigDecimal.valueOf(0.25));
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }

        try {
            final String param = "?id=" + id + "&nome=Aspirina&valor=9.95&tipo=MEDICAMENTO";
            final ProdutoModel p = this.rest.getForObject(getURL("/produto/alterar" + param), ProdutoModel.class);
            assertEquals(id, p.getId());
            assertEquals("Aspirina", p.getNome());
            assertEquals(BigDecimal.valueOf(9.95), p.getValor());
            assertEquals(EnumProdutoModel.MEDICAMENTO, p.getTipo());
            nq = nq.add(BigDecimal.valueOf(0.25));
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }

        try {
            final String param = "?id=" + id;
            final ProdutoModel p = this.rest.getForObject(getURL("/produto/excluir" + param), ProdutoModel.class);
            assertEquals(id, p.getId());
            assertEquals("Aspirina", p.getNome());
            assertEquals(BigDecimal.valueOf(9.95), p.getValor());
            assertEquals(EnumProdutoModel.MEDICAMENTO, p.getTipo());
            nq = nq.add(BigDecimal.valueOf(0.25));
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }

        try {
            final ProdutoModel[] p = this.rest.getForObject(getURL("/produto/listar"), ProdutoModel[].class);
            assertEquals(1, p.length);
            assertNotNull(p[0].getId());
            assertEquals("Leite", p[0].getNome());
            assertEquals(BigDecimal.valueOf(1.25), p[0].getValor());
            assertEquals(EnumProdutoModel.NAO_MEDICAMENTO, p[0].getTipo());
            nq = nq.add(BigDecimal.valueOf(0.25));
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }

        nota = nota.add(nq);
        notas.add("Q3...: " + nq);
    }

    @Test
    @Order(3)
    public void questaoQuatroTest() {

        var nq = BigDecimal.ZERO;
        Long id1 = 0L;
        Long id2 = 0L;
        Long idi = 0L;

        try {
            // CadastrarPedido
            PedidoModel p = this.rest.getForObject(getURL("/pedido/cadastrar"), PedidoModel.class);
            assertNotNull(p.getId());
            assertEquals(0, p.getItens().size());
            assertEquals(BigDecimal.ZERO, p.getValorTotal());
            assertNotNull(p.getDataPedido());
            assertTrue(p.getDataPedido() instanceof LocalDateTime);
            assertNull(p.getDataPagamento());
            assertEquals(EnumPedidoModel.AGUARDANDO_PAGAMENTO, p.getStatus());
            id1 = p.getId();

            p = this.rest.getForObject(getURL("/pedido/cadastrar"), PedidoModel.class);
            assertNotNull(p.getId());
            assertEquals(0, p.getItens().size());
            assertEquals(BigDecimal.ZERO, p.getValorTotal());
            assertNotNull(p.getDataPedido());
            assertTrue(p.getDataPedido() instanceof LocalDateTime);
            assertNull(p.getDataPagamento());
            assertEquals(EnumPedidoModel.AGUARDANDO_PAGAMENTO, p.getStatus());
            id2 = p.getId();
            nq = nq.add(BigDecimal.valueOf(0.3));
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }

        try {
            // AdicionarProduto
            var p1 = cadastrarProduto();
            var p2 = cadastrarProduto();

            String param = "?id-pedido=" + id2 + "&id-produto=" + p1.getId() + "&qtd=2";
            PedidoModel pe1 = this.rest.getForObject(getURL("/pedido/adicionar" + param), PedidoModel.class);
            assertEquals(id2, pe1.getId());
            assertEquals(1, pe1.getItens().size());
            assertEquals(0, BigDecimal.valueOf(3).compareTo(pe1.getValorTotal()));
            assertNotNull(pe1.getDataPedido());
            assertNull(pe1.getDataPagamento());
            assertEquals(EnumPedidoModel.AGUARDANDO_PAGAMENTO, pe1.getStatus());
            idi = pe1.getItens().get(0).getId();

            param = "?id-pedido=" + id2 + "&id-produto=" + p2.getId() + "&qtd=4";
            PedidoModel pe2 = this.rest.getForObject(getURL("/pedido/adicionar" + param), PedidoModel.class);
            assertEquals(pe1.getId(), pe2.getId());
            assertEquals(2, pe2.getItens().size());
            assertEquals(0, BigDecimal.valueOf(9).compareTo(pe2.getValorTotal()));
            assertNotNull(pe2.getDataPedido());
            assertNull(pe2.getDataPagamento());
            assertEquals(EnumPedidoModel.AGUARDANDO_PAGAMENTO, pe2.getStatus());

            nq = nq.add(BigDecimal.valueOf(0.5));
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }

        try {
            // ListarPedidos
            PedidoModel[] pedidos = this.rest.getForObject(getURL("/pedido/listar"), PedidoModel[].class);
            assertEquals(2, pedidos.length);
            for (PedidoModel p : pedidos) {
                if (id1.equals(p.getId())) {
                    assertEquals(0, p.getItens().size());
                    assertEquals(BigDecimal.ZERO, p.getValorTotal());
                    assertNotNull(p.getDataPedido());
                    assertNull(p.getDataPagamento());
                    assertEquals(EnumPedidoModel.AGUARDANDO_PAGAMENTO, p.getStatus());
                } else if (id2.equals(p.getId())) {
                    assertEquals(2, p.getItens().size());
                    assertEquals(0, BigDecimal.valueOf(9).compareTo(p.getValorTotal()));
                    assertNotNull(p.getDataPedido());
                    assertNull(p.getDataPagamento());
                    assertEquals(EnumPedidoModel.AGUARDANDO_PAGAMENTO, p.getStatus());
                } else {
                    fail();
                }
            }

            nq = nq.add(BigDecimal.valueOf(0.2));
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }

        try {
            // RemoverItemProduto
            String param = "?id=" + idi;
            PedidoModel p = this.rest.getForObject(getURL("/pedido/retirar" + param), PedidoModel.class);
            assertEquals(id2, p.getId());
            assertEquals(1, p.getItens().size());
            assertEquals(0, BigDecimal.valueOf(6).compareTo(p.getValorTotal()));
            assertNotNull(p.getDataPedido());
            assertNull(p.getDataPagamento());
            assertEquals(EnumPedidoModel.AGUARDANDO_PAGAMENTO, p.getStatus());

            nq = nq.add(BigDecimal.valueOf(1.5));
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }

        try {
            // ConfirmarPagamento
            String param = "?id=" + id2;
            PedidoModel p = this.rest.getForObject(getURL("/pedido/pagar" + param), PedidoModel.class);
            assertEquals(id2, p.getId());
            assertEquals(1, p.getItens().size());
            assertEquals(0, BigDecimal.valueOf(6).compareTo(p.getValorTotal()));
            assertNotNull(p.getDataPedido());
            assertNotNull(p.getDataPagamento());
            assertTrue(p.getDataPagamento() instanceof LocalDateTime);
            assertEquals(EnumPedidoModel.PAGO, p.getStatus());

            nq = nq.add(BigDecimal.valueOf(0.5));
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }

        try {
            // ExcluirPedido
            String param = "?id=" + id1;
            PedidoModel p = this.rest.getForObject(getURL("/pedido/excluir" + param), PedidoModel.class);
            assertNotNull(p.getId());
            assertEquals(0, p.getItens().size());
            assertEquals(BigDecimal.ZERO, p.getValorTotal());
            assertNotNull(p.getDataPedido());
            assertNull(p.getDataPagamento());
            assertEquals(EnumPedidoModel.AGUARDANDO_PAGAMENTO, p.getStatus());

            try {
                this.rest.getForObject(getURL("/pedido/excluir" + "?id=" + id2), PedidoModel.class);
            } catch (Exception e) {
                e.printStackTrace();
            }

            PedidoModel[] pedidos = this.rest.getForObject(getURL("/pedido/listar"), PedidoModel[].class);
            assertEquals(1, pedidos.length);
            assertEquals(id2, pedidos[0].getId());
            assertEquals(1, pedidos[0].getItens().size());
            assertEquals(0, BigDecimal.valueOf(6).compareTo(pedidos[0].getValorTotal()));
            assertNotNull(pedidos[0].getDataPedido());
            assertNotNull(pedidos[0].getDataPagamento());
            assertEquals(EnumPedidoModel.PAGO, pedidos[0].getStatus());

            nq = nq.add(BigDecimal.valueOf(1));
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }

        nota = nota.add(nq);
        notas.add("Q4...: " + nq);
    }

    private ProdutoModel cadastrarProduto() {
        String param = "?nome=Aspirina&valor=1.50&tipo=MEDICAMENTO";
        return this.rest.getForObject(getURL("/produto/cadastrar" + param), ProdutoModel.class);
    }

    @Test
    @Order(4)
    public void questaoCincoTest() {

        var nq = BigDecimal.ZERO;
        long id1 = 0;
        long id2 = 0;

        try {
            final String param = "?nome=carlos&documento=00000000604&pis=123";
            final FarmaceuticoModel f = this.rest.getForObject(getURL("/pessoa/cadastrar-farmaceutico" + param), FarmaceuticoModel.class);
            assertEquals("00000000604", f.getDocumento());
            assertEquals("123", f.getPis());
            assertEquals("carlos", f.getNome());
            assertNotNull(f.getId());
            id1 = f.getId();
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }

        try {
            final String param = "?nome=Jota&documento=00000000192&sintoma=dor";
            final ClienteModel c = this.rest.getForObject(getURL("/pessoa/cadastrar-cliente" + param), ClienteModel.class);
            assertEquals("00000000192", c.getDocumento());
            assertEquals("dor", c.getSintoma());
            assertEquals("Jota", c.getNome());
            assertNotNull(c.getId());
            id2 = c.getId();
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }

        try {
            final String param = "?id=" + id1;
            final String validar = this.rest.getForObject(getURL("/pessoa/validar-documento" + param), String.class);
            assertEquals("000.000.006-04", validar);
            nq = nq.add(BigDecimal.valueOf(0.5));
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }

        try {
            final String param = "?id=" + id2;
            final String validar = this.rest.getForObject(getURL("/pessoa/validar-documento" + param), String.class);
            assertEquals("Documento inválido", validar);
            nq = nq.add(BigDecimal.valueOf(0.5));
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }

        nota = nota.add(nq);
        notas.add("Q5...: " + nq);
    }

    private String getURL(final String url) {
        return "http://localhost:" + port + url;
    }
}
